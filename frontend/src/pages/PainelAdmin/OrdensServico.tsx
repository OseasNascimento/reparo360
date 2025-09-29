// src/pages/PainelAdmin/OrdensServico.tsx
import React, { useEffect, useRef, useState, FormEvent } from 'react';
import api from '@/services/api';
import type { OrdemServicoDTO, AgendamentoDTO, ClienteDTO, TecnicoDTO, ServicoDTO } from '@/api';

type PecaUtilizada = {
  id?: number;
  produtoId?: number;
  descricao?: string;
  quantidade?: number;
};

const currency = (v?: number | null) =>
  typeof v === 'number' ? v.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : '—';

const fmtDate = (iso?: string | Date) => {
  try {
    if (!iso) return '—';
    const d = new Date(iso);
    if (isNaN(d.getTime())) return '—';
    return d.toLocaleString('pt-BR');
  } catch {
    return '—';
  }
};

const OrdensServico: React.FC = () => {
  const [ordens, setOrdens] = useState<OrdemServicoDTO[]>([]);
  const [agendamentos, setAgendamentos] = useState<AgendamentoDTO[]>([]);
  const [form, setForm] = useState<Partial<OrdemServicoDTO>>({
    id: undefined,
    agendamentoId: undefined,
    status: 'AGENDADA',
    valorServico: undefined,
    valorMateriais: undefined,
    kmDeslocamento: undefined,
    observacoes: '',
  });
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Modal preview
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewHtml, setPreviewHtml] = useState<string>('');
  const iframeRef = useRef<HTMLIFrameElement | null>(null);

  // 1) Busca OS e Agendamentos
  const fetchDados = async () => {
    setLoading(true);
    setError(null);
    try {
      const [osResp, agResp] = await Promise.all([
        api.get<OrdemServicoDTO[]>('/os'),
        api.get<AgendamentoDTO[]>('/agendamentos'),
      ]);
      setOrdens(osResp.data);
      setAgendamentos(agResp.data);
    } catch (err) {
      console.error('Erro ao carregar dados:', err);
      setError('Falha ao carregar ordens de serviço.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDados();
  }, []);

  // 2) Atualiza o formulário
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setForm((f) => ({
      ...f,
      [name]:
        ['agendamentoId', 'valorServico', 'valorMateriais', 'kmDeslocamento'].includes(name)
          ? value === ''
            ? undefined
            : Number(value)
          : value,
    }));
  };

  // 3) Cria ou atualiza
  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      if (editing && form.id != null) {
        // EDITAR: PUT /api/os/{id}
        await api.put<OrdemServicoDTO>(`/os/${form.id}`, form);
      } else {
        // CRIAR: POST /api/agendamentos/{agendamentoId}/os -> depois PUT /api/os/{id}
        if (form.agendamentoId == null) {
          throw new Error('agendamentoId é obrigatório');
        }

        const createResp = await api.post<OrdemServicoDTO>(
          `/agendamentos/${form.agendamentoId}/os`
        );
        const nova = createResp.data;

        await api.put<OrdemServicoDTO>(`/os/${nova.id}`, {
          ...form,
          id: nova.id,
        });
      }

      // reseta formulário
      setForm({
        id: undefined,
        agendamentoId: undefined,
        status: 'AGENDADA',
        valorServico: undefined,
        valorMateriais: undefined,
        kmDeslocamento: undefined,
        observacoes: '',
      });
      setEditing(false);
      await fetchDados();
    } catch (err: any) {
      console.error('Erro ao salvar OS:', err);
      const st = err?.response?.status;
      if (st === 405) {
        setError('Método não suportado pelo backend. Verifique os endpoints de criação/edição.');
      } else if (st === 401 || st === 403) {
        setError('Sessão expirada ou sem permissão. Faça login novamente.');
      } else {
        setError(err?.response?.data?.message || err?.message || 'Erro ao salvar ordem de serviço.');
      }
    } finally {
      setLoading(false);
    }
  };

  // 4) Preenche form para editar
  const handleEdit = (o: OrdemServicoDTO) => {
    setForm({ ...o });
    setEditing(true);
  };

  // 5) Exclui OS
  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir esta ordem de serviço?')) return;
    setLoading(true);
    setError(null);
    try {
      await api.delete(`/os/${id}`);
      await fetchDados();
    } catch (err) {
      console.error('Erro ao excluir OS:', err);
      setError('Erro ao excluir ordem de serviço.');
    } finally {
      setLoading(false);
    }
  };

  // -------- Helpers para Impressão/Preview --------

  const fetchPrintData = async (os: OrdemServicoDTO) => {
    const agResp = await api.get<AgendamentoDTO>(`/agendamentos/${os.agendamentoId}`);
    const ag = agResp.data;

    let cliente: ClienteDTO | null = null;
    try {
      if ((ag as any).clienteId) {
        const c = await api.get<ClienteDTO>(`/clientes/${(ag as any).clienteId}`);
        cliente = c.data;
      } else if (ag.email) {
        const c = await api.get<ClienteDTO>(`/clientes/email/${encodeURIComponent(ag.email)}`);
        cliente = c.data;
      }
    } catch {
      cliente = null;
    }

    let tecnico: TecnicoDTO | null = null;
    try {
      if (ag.tecnicoId) {
        const t = await api.get<TecnicoDTO>(`/tecnicos/${ag.tecnicoId}`);
        tecnico = t.data;
      }
    } catch {
      tecnico = null;
    }

    let nomesServicos: string[] = [];
    try {
      const asAny = ag as any;
      if (Array.isArray(asAny.servicos) && asAny.servicos.length) {
        nomesServicos = asAny.servicos.map((s: any) => s.descricao ?? `Serviço #${s.idServico ?? ''}`);
      } else if (Array.isArray(asAny.servicosId) && asAny.servicosId.length) {
        const todos = await api.get<ServicoDTO[]>('/servicos');
        const mapa = new Map(todos.data.map(s => [s.idServico, s.descricao]));
        nomesServicos = asAny.servicosId.map((id: number) => mapa.get(id) ?? `Serviço #${id}`);
      }
    } catch {
      nomesServicos = [];
    }

    let pecas: { id?: number; produtoId?: number; descricao?: string; quantidade?: number }[] = [];
    try {
      const p = await api.get(`/os/${os.id}/pecas`);
      pecas = p.data;
    } catch {
      pecas = [];
    }

    return { ag, cliente, tecnico, nomesServicos, pecas };
  };

  // ===== NOVO layout do comprovante (estilo do PDF) =====
  const buildPrintHtml = (os: OrdemServicoDTO, extra: Awaited<ReturnType<typeof fetchPrintData>>) => {
    const { ag, cliente, tecnico, nomesServicos, pecas } = extra;

    // Total exibido é somente o valor do serviço
    const totalServico = os.valorServico ?? 0;

    // dados da empresa (ajuste livre se quiser parametrizar)
    const logoUrl = '/Logo.png';
    const empresaLinha1 = 'REPARO 360';
    const empresaLinha2 = 'CNPJ 22.365.324/0001-21 • (12) 98306-9312';
    const empresaLinha3 = 'reparo360.vale@gmail.com';

    const pecasTable = pecas.length
      ? `
      <table>
        <thead><tr><th>Descrição</th><th class="center">Qtde</th></tr></thead>
        <tbody>
          ${pecas
            .map(
              (p) => `
            <tr>
              <td>${p.descricao ?? '—'}</td>
              <td class="center">${p.quantidade ?? '—'}</td>
            </tr>`
            )
            .join('')}
        </tbody>
      </table>`
      : `<div class="muted">Nenhuma peça registrada.</div>`;

    return `<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8" />
  <title>OS #${os.id}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <style>
    * { box-sizing: border-box; }
    body { font-family: Arial, Helvetica, sans-serif; color: #111; margin: 24px; }
    h1,h2,h3 { margin: 0 0 8px 0; }
    .header { display:flex; align-items:center; gap:16px; margin-bottom:12px; }
    .brand { font-size:20px; font-weight:700; letter-spacing:.2px; }
    .muted { color:#6b7280; font-size:12px; }
    .card { border:1px solid #e5e7eb; border-radius:8px; padding:14px; margin-bottom:14px; }
    .row { display:flex; gap:16px; }
    .col { flex:1; }
    .kv { margin:2px 0 }
    .kv b { display:inline-block; min-width:140px; }
    .badge { display:inline-block; padding:4px 10px; background:#eef2ff; color:#3730a3; border-radius:999px; font-weight:600; font-size:12px }
    table { width:100%; border-collapse: collapse; }
    th, td { border-bottom:1px solid #e5e7eb; padding:8px; text-align:left; font-size:13px; }
    th { background:#f8fafc; }
    .center { text-align:center }
    .right { text-align:right }
    .signature { margin-top:28px; display:flex; gap:40px }
    .signature .box { flex:1; text-align:center }
    .signature .line { margin-top:46px; border-top:1px solid #111; width:100% }
    @media print { body { margin: 16px; } }
  </style>
</head>
<body>

  <!-- Cabeçalho -->
  <div class="header">
    <img src="${logoUrl}" alt="Logo" style="height:58px" onerror="this.style.display='none'"/>
    <div>
      <div class="brand">${empresaLinha1}</div>
      <div class="muted">${empresaLinha2}</div>
      <div class="muted">${empresaLinha3}</div>
    </div>
    <div style="margin-left:auto; text-align:right">
      <div class="kv"><b>OS:</b> ${os.id ?? '—'}</div>
      <div class="kv"><b>Status:</b> <span class="badge">${(os.status ?? '—').toString().replace('_',' ')}</span></div>
      <div class="kv"><b>Emissão:</b> ${new Date().toLocaleDateString('pt-BR')}</div>
    </div>
  </div>

  <!-- Cliente -->
  <div class="card">
    <h3>Cliente</h3>
    <div class="kv"><b>Nome</b> ${cliente?.nome ?? (ag.nomeCliente ?? '—')}</div>
    <div class="kv"><b>Telefone</b> ${cliente?.telefone ?? ag.telefone ?? '—'}</div>
    <div class="kv"><b>E-mail</b> ${cliente?.email ?? ag.email ?? '—'}</div>
    <div class="kv"><b>Endereço</b>
      ${
        [
          ag.logradouro ?? cliente?.logradouro,
          ag.numero ?? cliente?.numero,
          ag.complemento ?? cliente?.complemento,
          ag.bairro ?? cliente?.bairro,
          (ag.cidade ?? cliente?.cidade) && `${ag.cidade ?? cliente?.cidade}/${ag.uf ?? cliente?.uf ?? ''}`,
          ag.cep ?? cliente?.cep,
        ].filter(Boolean).join(' - ') || '—'
      }
    </div>
  </div>

  <!-- Agendamento -->
  <div class="card">
    <h3>Agendamento</h3>
    <div class="row">
      <div class="col">
        <div class="kv"><b>Data/Hora</b> ${fmtDate(ag.dataAgendamento)}</div>
        <div class="kv"><b>Serviços</b> ${nomesServicos.length ? nomesServicos.join(', ') : '—'}</div>
      </div>
      <div class="col">
        <div class="kv"><b>Técnico</b> ${tecnico?.nome ?? ag.tecnicoId ?? '—'}</div>
      </div>
    </div>
    <div class="kv"><b>Observações</b> ${os.observacoes ?? ag.observacoes ?? '—'}</div>
  </div>

  <!-- Peças -->
  <div class="card">
    <h3>Peças Utilizadas</h3>
    ${pecasTable}
  </div>

  <!-- Valores -->
  <div class="card">
    <h3>Valores</h3>
    <div class="kv"><b>Total do Serviço</b> ${currency(os.valorServico as any)}</div>
    <div class="muted" style="margin-top:6px">
      * Materiais informados no sistema são <u>apenas informativos</u> e não compõem o total acima.
    </div>
  </div>

  <!-- Garantia -->
  <div class="card" style="font-size:12px; line-height:1.4">
    <b>Termo de Garantia – Reparo360</b><br/>
    Garantia legal mínima de 90 dias para serviços e peças de natureza durável. Cobertura: reexecução do serviço e/ou substituição
    da peça, conforme o Código de Defesa do Consumidor. Para acionar, contate (12) 98306-9312 informando esta OS.
  </div>

  <!-- Assinaturas -->
  <div class="signature">
    <div class="box">
      <div class="line"></div>
      <div>Assinatura do Cliente</div>
    </div>
    <div class="box">
      <div class="line"></div>
      <div>Assinatura do Técnico</div>
    </div>
  </div>

</body>
</html>`;
  };

  const handlePreview = async (os: OrdemServicoDTO) => {
    try {
      const extra = await fetchPrintData(os);
      const html = buildPrintHtml(os, extra);
      setPreviewHtml(html);
      setPreviewOpen(true);
    } catch (e) {
      console.error(e);
      alert('Não foi possível montar a pré-visualização desta OS.');
    }
  };

  const printIframe = () => {
    const iframe = iframeRef.current;
    if (!iframe) return;
    try {
      iframe.contentWindow?.focus();
      iframe.contentWindow?.print();
    } catch {
      const w = window.open('', '_blank');
      if (!w) return;
      w.document.open();
      w.document.write(previewHtml);
      w.document.close();
      setTimeout(() => w.print(), 300);
    }
  };

  // 6) UI de loading / erro
  if (loading) {
    return <p className="text-center mt-8">Carregando ordens de serviço…</p>;
  }
  if (error) {
    return <p className="text-center mt-8 text-red-600">{error}</p>;
  }

  // 7) Markup final
  return (
    <div className="p-6 bg-white rounded shadow space-y-6 min-h-screen">
      <h2 className="text-2xl font-bold">Ordens de Serviço</h2>

      <form
        onSubmit={handleSubmit}
        className="grid grid-cols-1 md:grid-cols-3 gap-4"
      >
        {/* Agendamento */}
        <select
          name="agendamentoId"
          value={form.agendamentoId ?? ''}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        >
          <option value="" disabled>
            Selecione um agendamento
          </option>
          {agendamentos.map((a) => (
            <option key={a.id} value={a.id}>
              {a.nomeCliente} — {new Date(a.dataAgendamento).toLocaleDateString('pt-BR')}
            </option>
          ))}
        </select>

        {/* Status */}
        <select
          name="status"
          value={form.status}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        >
          <option value="" disabled>
            Selecione o status
          </option>
          {[
            'AGENDADA',
            'EM_ANDAMENTO',
            'ASSINADA',
            'RECUSADA',
            'CONCLUIDA',
            'CANCELADA',
          ].map((s) => (
            <option key={s} value={s}>
              {s.replace('_', ' ')}
            </option>
          ))}
        </select>

        {/* Valor Serviço */}
        <input
          name="valorServico"
          type="number"
          min={0.01}
          step={0.01}
          placeholder="Valor do serviço (R$) — ex: 150.00"
          value={form.valorServico ?? ''}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        />

        {/* Valor Materiais */}
        <input
          name="valorMateriais"
          type="number"
          min={0}
          step={0.01}
          placeholder="Valor dos materiais (R$) — ex: 50.00"
          value={form.valorMateriais ?? ''}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        />

        {/* KM Deslocamento */}
        <input
          name="kmDeslocamento"
          type="number"
          min={0}
          placeholder="KM de deslocamento — ex: 10"
          value={form.kmDeslocamento ?? ''}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        />

        {/* Observações */}
        <input
          name="observacoes"
          type="text"
          placeholder="Observações (opcional)"
          value={form.observacoes ?? ''}
          onChange={handleChange}
          className="border rounded px-3 py-2"
        />

        {/* Botão */}
        <button
          type="submit"
          disabled={loading}
          className="col-span-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          {editing ? 'Atualizar OS' : 'Criar OS'}
        </button>
      </form>

      {/* Tabela */}
      <div className="overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2">Agendamento</th>
              <th className="px-4 py-2">Status</th>
              <th className="px-4 py-2">Valor Serviço</th>
              <th className="px-4 py-2">Valor Materiais</th>
              <th className="px-4 py-2">KM Deslocamento</th>
              <th className="px-4 py-2">Ações</th>
            </tr>
          </thead>
          <tbody>
            {ordens.map((o) => (
              <tr key={o.id} className="border-t">
                <td className="px-4 py-2">{o.agendamentoId}</td>
                <td className="px-4 py-2">{o.status.replace('_', ' ')}</td>
                <td className="px-4 py-2">{currency(o.valorServico as any)}</td>
                <td className="px-4 py-2">{currency(o.valorMateriais as any)}</td>
                <td className="px-4 py-2">{o.kmDeslocamento}</td>
                <td className="px-4 py-2 flex flex-wrap gap-2">
                  <button
                    onClick={() => { setForm({ ...o }); setEditing(true); }}
                    className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition"
                  >
                    Editar
                  </button>
                  <button
                    onClick={() => handleDelete(o.id as number)}
                    className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
                  >
                    Excluir
                  </button>
                  <button
                    onClick={() => handlePreview(o)}
                    className="px-2 py-1 bg-gray-800 text-white rounded hover:bg-black transition"
                    title="Pré-visualizar / Imprimir OS"
                  >
                    Pré-visualizar
                  </button>
                </td>
              </tr>
            ))}
            {ordens.length === 0 && (
              <tr>
                <td colSpan={6} className="p-4 text-center text-gray-500">
                  Nenhuma OS encontrada.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Modal de Pré-visualização */}
      {previewOpen && (
        <div className="fixed inset-0 z-50 bg-black/50 flex items-center justify-center p-4">
          <div className="bg-white w-full max-w-5xl rounded-lg shadow-xl overflow-hidden flex flex-col">
            <div className="px-4 py-3 border-b flex items-center justify-between">
              <h3 className="font-semibold">Pré-visualização da OS</h3>
              <button
                onClick={() => setPreviewOpen(false)}
                className="text-gray-500 hover:text-gray-700"
              >
                Fechar ✕
              </button>
            </div>
            <div className="h-[70vh]">
              <iframe
                ref={iframeRef}
                title="preview-os"
                className="w-full h-full"
                srcDoc={previewHtml}
              />
            </div>
            <div className="px-4 py-3 border-t flex gap-2 justify-end">
              <button
                onClick={() => setPreviewOpen(false)}
                className="px-4 py-2 rounded border border-gray-300 hover:bg-gray-50"
              >
                Fechar
              </button>
              <button
                onClick={printIframe}
                className="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700"
              >
                Imprimir
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default OrdensServico;
