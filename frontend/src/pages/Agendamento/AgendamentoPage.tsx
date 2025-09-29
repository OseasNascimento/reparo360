// src/pages/AgendamentoPage.tsx
import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '@/services/api';
import type {
  ClienteDTO,
  TecnicoDTO,
  ServicoDTO,
  AgendamentoDTO
} from '@/api';

const UF_LIST = [
  'AC','AL','AP','AM','BA','CE','DF','ES','GO','MA',
  'MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN',
  'RS','RO','RR','SC','SP','SE','TO',
];

const maskCep = (v: string) =>
  v.replace(/\D/g, '').slice(0, 8).replace(/^(\d{5})(\d{0,3})$/, (_, a, b) => (b ? `${a}-${b}` : a));

const maskPhone = (v: string) => {
  const d = v.replace(/\D/g, '').slice(0, 11);
  if (d.length <= 10) {
    return d
      .replace(/^(\d{2})(\d)/, '($1) $2')
      .replace(/(\d{4})(\d{1,4})$/, '$1-$2');
  }
  return d
    .replace(/^(\d{2})(\d)/, '($1) $2')
    .replace(/(\d{5})(\d{1,4})$/, '$1-$2');
};

export default function AgendamentoPage() {
  const navigate = useNavigate();

  // listas da API
  const [tecnicos, setTecnicos] = useState<TecnicoDTO[]>([]);
  const [servicos, setServicos] = useState<ServicoDTO[]>([]);

  // dados do cliente
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

  // dados do agendamento
  const [dataInput, setDataInput] = useState<string>('');
  const [periodo, setPeriodo] = useState<'MANHA' | 'TARDE'>('MANHA');
  const [idTecnico, setIdTecnico] = useState<string>('');
  const [idsServicos, setIdsServicos] = useState<number[]>([]);

  // defeito
  const [descricaoProblema, setDescricaoProblema] = useState('');

  // disponibilidade
  const [slotBusy, setSlotBusy] = useState(false);
  const [slotBusyMessage, setSlotBusyMessage] = useState('');

  // estado CEP
  const [buscandoCep, setBuscandoCep] = useState(false);
  const [erro, setErro] = useState<string | null>(null);

  // carrega técnicos e serviços
  useEffect(() => {
    api.get<TecnicoDTO[]>('/tecnicos')
      .then(r => setTecnicos(r.data))
      .catch(() => setTecnicos([]));
    api.get<ServicoDTO[]>('/servicos')
      .then(r => setServicos(r.data))
      .catch(() => setServicos([]));
  }, []);

  // verifica disponibilidade
  useEffect(() => {
    if (!idTecnico || !dataInput) {
      setSlotBusy(false);
      setSlotBusyMessage('');
      return;
    }
    api.get<string[]>(
      `/agendamentos/disponibilidade?tecnicoId=${idTecnico}&dia=${dataInput}`
    )
    .then(resp => {
      const slots = resp.data;
      const time = periodo === 'MANHA' ? '08:00:00' : '13:00:00';
      const thisSlot = `${dataInput}T${time}`;
      if (slots.includes(thisSlot)) {
        setSlotBusy(true);
        const date = new Date(thisSlot);
        const formatted = date.toLocaleString('pt-BR', {
          day: '2-digit', month: '2-digit', year: 'numeric',
          hour: '2-digit', minute: '2-digit'
        });
        setSlotBusyMessage(`Este técnico já possui agendamento em ${formatted}`);
      } else {
        setSlotBusy(false);
        setSlotBusyMessage('');
      }
    })
    .catch(() => {
      setSlotBusy(false);
      setSlotBusyMessage('');
    });
  }, [idTecnico, dataInput, periodo]);

  const podeEnviar = useMemo(() => {
    return Boolean(
      dataInput &&
      idTecnico &&
      idsServicos.length > 0 &&
      nome && telefone && email &&
      cep && logradouro && bairro && cidade && uf &&
      descricaoProblema
    );
  }, [
    dataInput, idTecnico, idsServicos,
    nome, telefone, email, cep, logradouro, bairro, cidade, uf, descricaoProblema
  ]);

  const onBlurCep = async () => {
    const clean = cep.replace(/\D/g, '');
    if (clean.length !== 8) return;
    setBuscandoCep(true);
    setErro(null);
    try {
      const res = await fetch(`https://viacep.com.br/ws/${clean}/json/`);
      if (!res.ok) throw new Error('Falha ao consultar CEP');
      const data = await res.json();
      if (data.erro) {
        setErro('CEP não encontrado.');
        return;
      }
      setLogradouro(prev => data.logradouro ?? prev ?? '');
      setBairro(prev => data.bairro ?? prev ?? '');
      setCidade(prev => data.localidade ?? prev ?? '');
      setUf(prev => data.uf ?? prev ?? '');
    } catch (e) {
      setErro('Não foi possível buscar o CEP.');
    } finally {
      setBuscandoCep(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!podeEnviar) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }
    if (slotBusy) {
      alert(slotBusyMessage);
      return;
    }

    try {
      // 1) tenta buscar cliente por e-mail
      let idCliente: number;
      try {
        const resp = await api.get<ClienteDTO>(
          `/clientes/email/${encodeURIComponent(email)}`
        );
        idCliente = resp.data.idCliente!;
      } catch (err: any) {
        if (err.response?.status === 404) {
          // cria novo cliente com os campos separados
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

      // 2) monta payload completo do agendamento
      const hora = periodo === 'MANHA' ? '08:00:00' : '13:00:00';
      const createDto: Omit<AgendamentoDTO, 'id'> = {
        nomeCliente: nome,
        email,
        telefone,
        clienteId: idCliente,
        tecnicoId: Number(idTecnico),
        dataAgendamento: `${dataInput}T${hora}`,
        servicosId: idsServicos,
        observacoes: `Período: ${periodo}\nDefeito: ${descricaoProblema}`,
        status: 'AGENDADO',
        logradouro,
        numero,
        complemento,
        bairro,
        cidade,
        uf,
        cep,
      };

      // 3) envia para o backend
      const res = await api.post<AgendamentoDTO>('/agendamentos', createDto);
      const newId = (res.data as any).id ?? (res.data as any).idAgendamento;
      navigate(`/Agendamentos/${newId}/status`);
    } catch (err: any) {
      if (err.response?.status === 409) {
        alert(err.response.data.error);
        return;
      }
      console.error(err);
      alert('Erro interno. Tente novamente mais tarde.');
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 pt-[104px] px-4 lg:px-0">
      <div className="max-w-[900px] mx-auto">
        <h1 className="text-3xl font-heading text-primary mb-6">Agendar Serviço</h1>

        {erro && (
          <div className="mb-4 p-3 rounded bg-red-50 text-red-700 border border-red-200">
            {erro}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-lg">

          {/* Data / Período */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label className="block mb-2 font-semibold">Data *</label>
              <input
                type="date"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={dataInput}
                onChange={e => setDataInput(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Período *</label>
              <div className="flex gap-6">
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    checked={periodo === 'MANHA'}
                    onChange={() => setPeriodo('MANHA')}
                  />
                  <span className="ml-2">Manhã</span>
                </label>
                <label className="inline-flex items-center">
                  <input
                    type="radio"
                    checked={periodo === 'TARDE'}
                    onChange={() => setPeriodo('TARDE')}
                  />
                  <span className="ml-2">Tarde</span>
                </label>
              </div>
            </div>
          </div>

          {/* Dados do Cliente */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label className="block mb-2 font-semibold">Nome *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={nome}
                onChange={e => setNome(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Telefone *</label>
              <input
                type="tel"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={telefone}
                onChange={e => setTelefone(maskPhone(e.target.value))}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">E-mail *</label>
              <input
                type="email"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">CEP *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={cep}
                onChange={e => setCep(maskCep(e.target.value))}
                onBlur={onBlurCep}
                required
              />
              {buscandoCep && (
                <p className="text-sm text-gray-500 mt-1">Buscando CEP…</p>
              )}
            </div>
            <div className="md:col-span-2">
              <label className="block mb-2 font-semibold">Logradouro *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={logradouro}
                onChange={e => setLogradouro(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Número *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={numero}
                onChange={e => setNumero(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Complemento</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={complemento}
                onChange={e => setComplemento(e.target.value)}
                placeholder="Opcional"
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Bairro *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={bairro}
                onChange={e => setBairro(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">Cidade *</label>
              <input
                type="text"
                className="w-full p-3 border border-gray-300 rounded-md"
                value={cidade}
                onChange={e => setCidade(e.target.value)}
                required
              />
            </div>
            <div>
              <label className="block mb-2 font-semibold">UF *</label>
              <select
                className="w-full p-3 border border-gray-300 rounded-md"
                value={uf}
                onChange={e => setUf(e.target.value)}
                required
              >
                <option value="">Selecione…</option>
                {UF_LIST.map(u => (
                  <option key={u} value={u}>{u}</option>
                ))}
              </select>
            </div>
          </div>

          {/* Técnico */}
          <div>
            <label className="block mb-2 font-semibold">Técnico *</label>
            <select
              className="w-full p-3 border border-gray-300 rounded-md"
              value={idTecnico}
              onChange={e => setIdTecnico(e.target.value)}
              required
            >
              <option value="">Selecione…</option>
              {tecnicos.map(t => (
                <option key={t.id} value={t.id!.toString()}>
                  {t.nome}
                </option>
              ))}
            </select>
            {slotBusy && (
              <p className="mt-1 text-sm text-red-600">{slotBusyMessage}</p>
            )}
          </div>

          {/* Serviços */}
          <div>
            <label className="block mb-2 font-semibold">Serviços *</label>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
              {servicos.map(s => (
                <label key={s.idServico} className="flex items-center">
                  <input
                    type="checkbox"
                    className="form-checkbox h-5 w-5 text-blue-600"
                    checked={idsServicos.includes(s.idServico!)}
                    value={s.idServico!.toString()}
                    onChange={e => {
                      const v = Number(e.target.value);
                      setIdsServicos(prev =>
                        prev.includes(v)
                          ? prev.filter(x => x !== v)
                          : [...prev, v]
                      );
                    }}
                  />
                  <span className="ml-2">{s.descricao}</span>
                </label>
              ))}
            </div>
          </div>

          {/* Detalhes do defeito */}
          <div className="border-t pt-4">
            <label className="block mb-2 font-semibold">Descreva os detalhes do defeito *</label>
            <textarea
              rows={4}
              className="w-full p-3 border border-gray-300 rounded-md"
              value={descricaoProblema}
              onChange={e => setDescricaoProblema(e.target.value)}
              required
            />
          </div>

          {/* Botão Enviar */}
          <button
            type="submit"
            disabled={slotBusy || !podeEnviar}
            className={`w-full py-4 rounded-full font-heading text-lg shadow-lg transition mt-2 ${
              slotBusy || !podeEnviar
                ? 'bg-gray-400 text-gray-200 cursor-not-allowed'
                : 'bg-blue-600 text-white hover:bg-blue-700'
            }`}
          >
            Enviar Agendamento
          </button>
        </form>
      </div>
    </div>
  );
}
