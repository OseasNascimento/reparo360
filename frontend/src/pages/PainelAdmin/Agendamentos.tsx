// src/pages/PainelAdmin/Agendamentos.tsx
import React, { useEffect, useMemo, useState } from 'react';
import api from '@/services/api';
import type { AgendamentoDTO, ClienteDTO, ServicoDTO, TecnicoDTO } from '@/api';

export type Status =
  | 'AGENDADO'
  | 'TECNICO_A_CAMINHO'
  | 'TECNICO_CHEGOU'
  | 'EM_ANDAMENTO'
  | 'CONCLUIDO'
  | 'CANCELADO';

const TODOS_STATUS: Status[] = [
  'AGENDADO',
  'TECNICO_A_CAMINHO',
  'TECNICO_CHEGOU',
  'EM_ANDAMENTO',
  'CONCLUIDO',
  'CANCELADO',
];

const statusLabels: Record<Status, string> = {
  AGENDADO: 'Agendado',
  TECNICO_A_CAMINHO: 'Téc. a Caminho',
  TECNICO_CHEGOU: 'Téc. Chegou',
  EM_ANDAMENTO: 'Em Andamento',
  CONCLUIDO: 'Concluído',
  CANCELADO: 'Cancelado',
};

const statusClasses: Record<Status, string> = {
  AGENDADO: 'bg-yellow-100 text-yellow-800',
  TECNICO_A_CAMINHO: 'bg-blue-100 text-blue-800',
  TECNICO_CHEGOU: 'bg-green-100 text-green-800',
  EM_ANDAMENTO: 'bg-indigo-100 text-indigo-800',
  CONCLUIDO: 'bg-green-200 text-green-800',
  CANCELADO: 'bg-red-100 text-red-800',
};

type StatusMap = Record<number, Status>;

type Periodo = 'MANHA' | 'TARDE';

const Agendamentos: React.FC = () => {
  // lista + status inline
  const [agendamentos, setAgendamentos] = useState<AgendamentoDTO[]>([]);
  const [statusMap, setStatusMap] = useState<StatusMap>({});
  const [updatingId, setUpdatingId] = useState<number | null>(null);

  // dados para criação manual
  const [openCreate, setOpenCreate] = useState(false);
  const [tecnicos, setTecnicos] = useState<TecnicoDTO[]>([]);
  const [servicos, setServicos] = useState<ServicoDTO[]>([]);
  const [loadingForm, setLoadingForm] = useState(false);
  const [erroForm, setErroForm] = useState<string | null>(null);

  // cliente/endereço
  const [nome, setNome] = useState('');
  const [telefone, setTelefone] = useState('');
  const [email, setEmail] = useState('');
  const [cep, setCep] = useState('');
  const [logradouro, setLogradouro] = useState('');
  const [numero, setNumero] = useState('');
  const [complemento, setComplemento] = useState('');
  const [bairro, setBairro] = useState('');
  const [cidade, setCidade] = useState('');
  const [uf, setUf] = useState('');

  // agendamento
  const [dataInput, setDataInput] = useState('');
  const [periodo, setPeriodo] = useState<Periodo>('MANHA');
  const [idTecnico, setIdTecnico] = useState<string>('');
  const [idsServicos, setIdsServicos] = useState<number[]>([]);
  const [observacoes, setObservacoes] = useState('');

  // disponibilidade
  const [ocupado, setOcupado] = useState(false);
  const [msgOcupado, setMsgOcupado] = useState('');

  const horarioSelecionado = useMemo(
    () => (periodo === 'MANHA' ? '08:00:00' : '13:00:00'),
    [periodo]
  );

  // ---- fetch base ----
  const fetchAgendamentos = async () => {
    const resp = await api.get<AgendamentoDTO[]>('/agendamentos');
    setAgendamentos(resp.data);
    const map: StatusMap = {};
    resp.data.forEach((a) => (map[a.id as number] = a.status as Status));
    setStatusMap(map);
  };

  useEffect(() => {
    (async () => {
      try {
        await fetchAgendamentos();
        const [t, s] = await Promise.all([
          api.get<TecnicoDTO[]>('/tecnicos'),
          api.get<ServicoDTO[]>('/servicos'),
        ]);
        setTecnicos(t.data);
        setServicos(s.data);
      } catch (e) {
        // silencia erros de carga inicial
      }
    })();
  }, []);

  // disponibilidade do técnico/slot
  useEffect(() => {
    (async () => {
      if (!idTecnico || !dataInput) {
        setOcupado(false);
        setMsgOcupado('');
        return;
      }
      try {
        const r = await api.get<string[]>(
          `/agendamentos/disponibilidade?tecnicoId=${idTecnico}&dia=${dataInput}`
        );
        const thisSlot = `${dataInput}T${horarioSelecionado}`;
        if (r.data.includes(thisSlot)) {
          setOcupado(true);
          const dt = new Date(thisSlot);
          const f = dt.toLocaleString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
          });
          setMsgOcupado(`Este técnico já possui agendamento em ${f}`);
        } else {
          setOcupado(false);
          setMsgOcupado('');
        }
      } catch {
        setOcupado(false);
        setMsgOcupado('');
      }
    })();
  }, [idTecnico, dataInput, horarioSelecionado]);

  // ---- ações da tabela ----
  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir este agendamento?')) return;
    try {
      await api.delete(`/agendamentos/${id}`);
      await fetchAgendamentos();
    } catch (err) {
      console.error(err);
    }
  };

  const handleStatusChange = async (id: number, novoStatus: Status) => {
    setUpdatingId(id);
    try {
      await api.patch(`/agendamentos/${id}/status`, null, {
        params: { status: novoStatus },
      });
      setAgendamentos((prev) =>
        prev.map((a) => (a.id === id ? { ...a, status: novoStatus } : a))
      );
      setStatusMap((prev) => ({ ...prev, [id]: novoStatus }));
    } catch (err) {
      console.error(err);
    } finally {
      setUpdatingId(null);
    }
  };

  // ---- criação manual ----
  const limparFormulario = () => {
    setNome('');
    setTelefone('');
    setEmail('');
    setCep('');
    setLogradouro('');
    setNumero('');
    setComplemento('');
    setBairro('');
    setCidade('');
    setUf('');
    setDataInput('');
    setPeriodo('MANHA');
    setIdTecnico('');
    setIdsServicos([]);
    setObservacoes('');
    setOcupado(false);
    setMsgOcupado('');
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    setErroForm(null);

    if (
      !nome ||
      !telefone ||
      !email ||
      !cep ||
      !logradouro ||
      !bairro ||
      !cidade ||
      !uf ||
      !dataInput ||
      !idTecnico ||
      idsServicos.length === 0
    ) {
      setErroForm('Preencha todos os campos obrigatórios.');
      return;
    }
    if (ocupado) {
      setErroForm(msgOcupado || 'Horário indisponível para este técnico.');
      return;
    }

    setLoadingForm(true);
    try {
      // 1) busca ou cria cliente por e-mail
      let idCliente: number;
      try {
        const r = await api.get<ClienteDTO>(
          `/clientes/email/${encodeURIComponent(email)}`
        );
        idCliente = r.data.idCliente!;
      } catch (err: any) {
        if (err?.response?.status === 404) {
          const novo = await api.post<ClienteDTO>('/clientes', {
            nome,
            email,
            telefone,
            logradouro,
            numero,
            complemento,
            bairro,
            cidade,
            uf,
            cep,
          });
          idCliente = novo.data.idCliente!;
        } else {
          throw err;
        }
      }

      // 2) cria agendamento
      const payload: Omit<AgendamentoDTO, 'id'> = {
        nomeCliente: nome,
        email,
        telefone,
        clienteId: idCliente,
        tecnicoId: Number(idTecnico),
        dataAgendamento: `${dataInput}T${horarioSelecionado}`,
        servicosId: idsServicos,
        observacoes,
        status: 'AGENDADO',
        logradouro,
        numero,
        complemento,
        bairro,
        cidade,
        uf,
        cep,
      } as any;

      await api.post<AgendamentoDTO>('/agendamentos', payload);
      await fetchAgendamentos();
      limparFormulario();
      setOpenCreate(false);
    } catch (err: any) {
      console.error(err);
      setErroForm(
        err?.response?.data?.message || 'Erro ao criar agendamento.'
      );
    } finally {
      setLoadingForm(false);
    }
  };

  return (
    <div className="p-6 bg-white rounded shadow">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold">Agendamentos</h2>
        <button
          onClick={() => setOpenCreate((v) => !v)}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
        >
          {openCreate ? 'Fechar' : 'Novo Agendamento'}
        </button>
      </div>

      {openCreate && (
        <form onSubmit={handleCreate} className="mb-8 border rounded-lg p-4 space-y-4">
          {erroForm && <p className="text-red-600">{erroForm}</p>}

          {/* Data/Período/Técnico */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className="block mb-1 font-medium">Data *</label>
              <input
                type="date"
                className="w-full border rounded px-3 py-2"
                value={dataInput}
                onChange={(e) => setDataInput(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">Período *</label>
              <select
                className="w-full border rounded px-3 py-2"
                value={periodo}
                onChange={(e) => setPeriodo(e.target.value as Periodo)}
              >
                <option value="MANHA">Manhã</option>
                <option value="TARDE">Tarde</option>
              </select>
            </div>
            <div>
              <label className="block mb-1 font-medium">Técnico *</label>
              <select
                className="w-full border rounded px-3 py-2"
                value={idTecnico}
                onChange={(e) => setIdTecnico(e.target.value)}
                required
              >
                <option value="">Selecione…</option>
                {tecnicos.map((t) => (
                  <option key={t.id} value={t.id!.toString()}>
                    {t.nome}
                  </option>
                ))}
              </select>
              {ocupado && (
                <p className="text-sm text-red-600 mt-1">{msgOcupado}</p>
              )}
            </div>
          </div>

          {/* Cliente */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className="block mb-1 font-medium">Nome *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">Telefone *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={telefone}
                onChange={(e) => setTelefone(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">E-mail *</label>
              <input
                type="email"
                className="w-full border rounded px-3 py-2"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
          </div>

          {/* Endereço */}
          <div className="grid grid-cols-1 md:grid-cols-6 gap-4">
            <div className="md:col-span-2">
              <label className="block mb-1 font-medium">CEP *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={cep}
                onChange={(e) => setCep(e.target.value)}
                required
              />
            </div>
            <div className="md:col-span-4">
              <label className="block mb-1 font-medium">Logradouro *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={logradouro}
                onChange={(e) => setLogradouro(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">Número</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={numero}
                onChange={(e) => setNumero(e.target.value)}
              />
            </div>
            <div className="md:col-span-2">
              <label className="block mb-1 font-medium">Complemento</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={complemento}
                onChange={(e) => setComplemento(e.target.value)}
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">Bairro *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={bairro}
                onChange={(e) => setBairro(e.target.value)}
                required
              />
            </div>
            <div className="md:col-span-2">
              <label className="block mb-1 font-medium">Cidade *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={cidade}
                onChange={(e) => setCidade(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-1 font-medium">UF *</label>
              <input
                className="w-full border rounded px-3 py-2"
                value={uf}
                onChange={(e) => setUf(e.target.value)}
                maxLength={2}
                required
              />
            </div>
          </div>

          {/* Serviços + Observações */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block mb-1 font-medium">Serviços *</label>
              <div className="space-y-2 max-h-40 overflow-auto border rounded p-2">
                {servicos.map((s) => (
                  <label key={s.idServico} className="flex items-center gap-2">
                    <input
                      type="checkbox"
                      checked={idsServicos.includes(s.idServico!)}
                      onChange={(e) => {
                        const v = s.idServico!;
                        setIdsServicos((prev) =>
                          e.target.checked
                            ? [...prev, v]
                            : prev.filter((x) => x !== v)
                        );
                      }}
                    />
                    <span>{s.descricao}</span>
                  </label>
                ))}
              </div>
            </div>
            <div>
              <label className="block mb-1 font-medium">Observações</label>
              <textarea
                rows={5}
                className="w-full border rounded px-3 py-2"
                value={observacoes}
                onChange={(e) => setObservacoes(e.target.value)}
                placeholder="Descreva o defeito, instruções, etc."
              />
            </div>
          </div>

          <div className="pt-2">
            <button
              type="submit"
              disabled={loadingForm || ocupado}
              className={`px-5 py-2 rounded text-white transition ${
                loadingForm || ocupado
                  ? 'bg-gray-400 cursor-not-allowed'
                  : 'bg-green-600 hover:bg-green-700'
              }`}
            >
              {loadingForm ? 'Salvando…' : 'Criar Agendamento'}
            </button>
          </div>
        </form>
      )}

      {/* Tabela existente */}
      <table className="min-w-full bg-white">
        <thead>
          <tr className="bg-gray-100">
            <th className="px-4 py-2 text-left">Cliente</th>
            <th className="px-4 py-2 text-left">Técnico</th>
            <th className="px-4 py-2 text-left">Data / Hora</th>
            <th className="px-4 py-2 text-left">Detalhes</th>
            <th className="px-4 py-2 text-left">Status Atual</th>
            <th className="px-4 py-2 text-left">Mudar Status</th>
            <th className="px-4 py-2 text-left">Ações</th>
          </tr>
        </thead>
        <tbody>
          {agendamentos.map((a) => {
            const currentStatus: Status =
              statusMap[a.id as number] ?? (a.status as Status);

            return (
              <tr key={a.id} className="border-t">
                <td className="px-4 py-2">{a.nomeCliente}</td>
                <td className="px-4 py-2">{a.tecnicoId}</td>
                <td className="px-4 py-2">
                  {new Date(a.dataAgendamento).toLocaleString('pt-BR')}
                </td>
                <td className="px-4 py-2 whitespace-pre-line">
                  {a.observacoes}
                </td>
                <td className="px-4 py-2">
                  <span
                    className={`px-2 py-1 rounded-full text-xs font-semibold ${statusClasses[currentStatus]}`}
                  >
                    {statusLabels[currentStatus]}
                  </span>
                </td>
                <td className="px-4 py-2">
                  <div className="flex items-center space-x-2">
                    <select
                      value={currentStatus}
                      onChange={(e) => {
                        const novo = e.target.value as Status;
                        setStatusMap((prev) => ({
                          ...prev,
                          [a.id as number]: novo,
                        }));
                      }}
                      disabled={updatingId === a.id}
                      className="border rounded px-2 py-1 text-sm"
                    >
                      {TODOS_STATUS.map((s) => (
                        <option key={s} value={s}>
                          {statusLabels[s]}
                        </option>
                      ))}
                    </select>
                    <button
                      onClick={() =>
                        handleStatusChange(a.id as number, currentStatus)
                      }
                      disabled={updatingId === a.id}
                      className="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 transition disabled:opacity-50 text-sm"
                    >
                      {updatingId === a.id ? 'Salvando...' : 'Salvar'}
                    </button>
                  </div>
                </td>
                <td className="px-4 py-2">
                  <button
                    onClick={() => handleDelete(a.id as number)}
                    className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition text-sm"
                  >
                    Excluir
                  </button>
                </td>
              </tr>
            );
          })}
          {agendamentos.length === 0 && (
            <tr>
              <td colSpan={7} className="p-4 text-center text-gray-500">
                Nenhum agendamento encontrado.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Agendamentos;
