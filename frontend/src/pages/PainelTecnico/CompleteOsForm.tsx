import React, { useState, useEffect, ChangeEvent, FormEvent, useRef } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import api from '@/services/api'

// Tipos mínimos usados aqui
export interface OrdemServicoDTO {
  id: number
  agendamentoId: number
  status: string
  valorServico: number
  valorMateriais: number
  kmDeslocamento: number
  observacoes?: string
}
type AgendamentoDTO = {
  id: number
  nomeCliente: string
  telefone: string
  email: string
  logradouro: string
  numero: string
  complemento?: string
  bairro: string
  cidade: string
  uf: string
  cep: string
  dataAgendamento: string
  tecnicoId?: number
  observacoes?: string
  // opcionalmente pode vir servicos / servicosId
  servicos?: { idServico?: number; descricao?: string }[]
  servicosId?: number[]
}
type ClienteDTO = {
  idCliente?: number
  nome?: string
  telefone?: string
  email?: string
  logradouro?: string
  numero?: string
  complemento?: string
  bairro?: string
  cidade?: string
  uf?: string
  cep?: string
}
type TecnicoDTO = { tecnico_id?: number; id?: number; nome?: string }
type ServicoDTO = { idServico: number; descricao: string }

// helpers
const currency = (v?: number | null) =>
  typeof v === 'number' ? v.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : '—'

const fmtDate = (iso?: string | Date) => {
  try {
    if (!iso) return '—'
    const d = new Date(iso)
    if (isNaN(d.getTime())) return '—'
    return d.toLocaleString('pt-BR')
  } catch {
    return '—'
  }
}

const CompleteOsForm: React.FC = () => {
  const { osId } = useParams<{ osId: string }>()
  const navigate = useNavigate()

  const [form, setForm] = useState<OrdemServicoDTO>({
    id: 0,
    agendamentoId: 0,
    status: 'ASSINADA',
    valorServico: 0,
    valorMateriais: 0,
    kmDeslocamento: 0,
    observacoes: ''
  })
  const [loading, setLoading] = useState<boolean>(true)

  // dados extras para o Resumo e para a Visualização
  const [ag, setAg] = useState<AgendamentoDTO | null>(null)
  const [cliente, setCliente] = useState<ClienteDTO | null>(null)
  const [tecnico, setTecnico] = useState<TecnicoDTO | null>(null)
  const [nomesServicos, setNomesServicos] = useState<string[]>([])
  const [pecas, setPecas] = useState<{ id?: number; produtoId?: number; descricao?: string; quantidade?: number }[]>([])

  // modal de pré-visualização
  const [previewOpen, setPreviewOpen] = useState(false)
  const [previewHtml, setPreviewHtml] = useState('')
  const iframeRef = useRef<HTMLIFrameElement | null>(null)

  useEffect(() => {
    const run = async () => {
      if (!osId) return
      try {
        // 1) carrega OS
        const { data: os } = await api.get<OrdemServicoDTO>(`/os/${osId}`)
        setForm(os)

        // 2) carrega extra
        await fetchAllExtra(os)
      } catch (err) {
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    run()
  }, [osId])

  // carrega os dados “extra” (mesmos do admin)
  const fetchAllExtra = async (os: OrdemServicoDTO) => {
    // agendamento
    const { data: agData } = await api.get<AgendamentoDTO>(`/agendamentos/${os.agendamentoId}`)
    setAg(agData)

    // cliente (por id ou por email)
    let c: ClienteDTO | null = null
    try {
      const anyAg: any = agData
      if (anyAg.clienteId) {
        const resp = await api.get<ClienteDTO>(`/clientes/${anyAg.clienteId}`)
        c = resp.data
      } else if (agData.email) {
        const resp = await api.get<ClienteDTO>(`/clientes/email/${encodeURIComponent(agData.email)}`)
        c = resp.data
      }
    } catch {}
    setCliente(c)

    // técnico
    let t: TecnicoDTO | null = null
    try {
      if (agData.tecnicoId) {
        const resp = await api.get<TecnicoDTO>(`/tecnicos/${agData.tecnicoId}`)
        t = resp.data
      }
    } catch {}
    setTecnico(t)

    // nomes dos serviços
    try {
      const anyAg = agData as any
      if (Array.isArray(anyAg.servicos) && anyAg.servicos.length) {
        setNomesServicos(anyAg.servicos.map((s: any) => s.descricao ?? `Serviço #${s.idServico ?? ''}`))
      } else if (Array.isArray(anyAg.servicosId) && anyAg.servicosId.length) {
        const todos = await api.get<ServicoDTO[]>('/servicos')
        const mapa = new Map(todos.data.map(s => [s.idServico, s.descricao]))
        setNomesServicos(anyAg.servicosId.map((id: number) => mapa.get(id) ?? `Serviço #${id}`))
      } else {
        setNomesServicos([])
      }
    } catch {
      setNomesServicos([])
    }

    // peças
    try {
      const p = await api.get(`/os/${os.id}/pecas`)
      setPecas(p.data ?? [])
    } catch {
      setPecas([])
    }
  }

  // HTML idêntico ao do admin (estilo PDF)
  const buildPrintHtml = () => {
    if (!ag) return ''
    const os = form
    const logoUrl = '/Logo.png'
    const empresaLinha1 = 'REPARO 360'
    const empresaLinha2 = 'CNPJ 22.365.324/0001-21 • (12) 98306-9312'
    const empresaLinha3 = 'reparo360.vale@gmail.com'

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
      : `<div class="muted">Nenhuma peça registrada.</div>`

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
    .signature { margin-top:28px; display:flex; gap:40px }
    .signature .box { flex:1; text-align:center }
    .signature .line { margin-top:46px; border-top:1px solid #111; width:100% }
    @media print { body { margin: 16px; } }
  </style>
</head>
<body>

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

  <div class="card">
    <h3>Peças Utilizadas</h3>
    ${pecasTable}
  </div>

  <div class="card">
    <h3>Valores</h3>
    <div class="kv"><b>Total do Serviço</b> ${currency(os.valorServico as any)}</div>
    <div class="muted" style="margin-top:6px">
      * Materiais informados no sistema são <u>apenas informativos</u> e não compõem o total acima.
    </div>
  </div>

  <div class="card" style="font-size:12px; line-height:1.4">
    <b>Termo de Garantia – Reparo360</b><br/>
    Garantia legal mínima de 90 dias para serviços e peças de natureza durável. Cobertura: reexecução do serviço e/ou substituição
    da peça, conforme o Código de Defesa do Consumidor. Para acionar, contate (12) 98306-9312 informando esta OS.
  </div>

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
</html>`
  }

  const openPreview = () => {
    const html = buildPrintHtml()
    if (!html) return
    setPreviewHtml(html)
    setPreviewOpen(true)
  }

  const printIframe = () => {
    const iframe = iframeRef.current
    if (!iframe) return
    try {
      iframe.contentWindow?.focus()
      iframe.contentWindow?.print()
    } catch {
      const w = window.open('', '_blank')
      if (!w) return
      w.document.open()
      w.document.write(previewHtml)
      w.document.close()
      setTimeout(() => w.print(), 300)
    }
  }

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setForm(prev => ({
      ...prev,
      [name]: name === 'observacoes' ? value : Number(value)
    }) as OrdemServicoDTO)
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    if (!osId) return
    await api.put<OrdemServicoDTO>(`/os/${osId}`, form)
    navigate('/painel-tecnico/os') // volta para a lista do técnico
  }

  if (loading) return <div className="p-6">Carregando OS...</div>

  return (
    <div className="max-w-3xl mx-auto p-6 bg-white rounded shadow">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-2xl font-semibold">Completar Ordem de Serviço</h2>
        <div className="flex gap-2">
          <button
            onClick={() => navigate(-1)}
            className="px-3 py-2 rounded border border-gray-300 hover:bg-gray-50"
          >
            Voltar
          </button>
          <button
            onClick={openPreview}
            className="px-3 py-2 rounded bg-gray-800 text-white hover:bg-black"
            title="Pré-visualizar / Imprimir"
          >
            Visualizar / Imprimir
          </button>
        </div>
      </div>

      {/* Resumo completo (somente leitura) */}
      {ag && (
        <div className="mb-6 border rounded-lg p-4 bg-gray-50">
          <div className="flex items-center justify-between">
            <div className="font-medium">OS #{form.id}</div>
            <span className="text-xs px-2 py-1 rounded-full bg-indigo-100 text-indigo-800">
              {form.status.replace('_', ' ')}
            </span>
          </div>
          <div className="grid md:grid-cols-2 gap-3 mt-3 text-sm">
            <div>
              <div><b>Cliente:</b> {cliente?.nome ?? ag.nomeCliente ?? '—'}</div>
              <div><b>Telefone:</b> {cliente?.telefone ?? ag.telefone ?? '—'}</div>
              <div><b>E-mail:</b> {cliente?.email ?? ag.email ?? '—'}</div>
            </div>
            <div>
              <div><b>Data/Hora:</b> {fmtDate(ag.dataAgendamento)}</div>
              <div><b>Técnico:</b> {tecnico?.nome ?? ag.tecnicoId ?? '—'}</div>
              <div><b>Serviços:</b> {nomesServicos.length ? nomesServicos.join(', ') : '—'}</div>
            </div>
            <div className="md:col-span-2">
              <b>Endereço:</b>{' '}
              {[ag.logradouro || cliente?.logradouro, ag.numero || cliente?.numero, ag.complemento || cliente?.complemento, ag.bairro || cliente?.bairro, (ag.cidade || cliente?.cidade) && `${ag.cidade || cliente?.cidade}/${ag.uf || cliente?.uf}`, ag.cep || cliente?.cep].filter(Boolean).join(' - ') || '—'}
            </div>
            <div className="md:col-span-2">
              <b>Observações:</b> {form.observacoes || ag.observacoes || '—'}
            </div>
            <div className="md:col-span-2">
              <b>Peças:</b>{' '}
              {pecas.length
                ? pecas.map(p => `${p.descricao ?? '—'} (${p.quantidade ?? '—'})`).join(', ')
                : 'Nenhuma peça registrada.'}
            </div>
            <div className="md:col-span-2">
              <b>Valores:</b> Serviço {currency(form.valorServico)} • Materiais {currency(form.valorMateriais)} • KM {form.kmDeslocamento}
            </div>
          </div>
        </div>
      )}

      {/* Formulário de edição */}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="valorServico" className="block text-sm">Valor do Serviço</label>
          <input
            id="valorServico"
            name="valorServico"
            type="number"
            step="0.01"
            value={form.valorServico}
            onChange={handleChange}
            className="mt-1 block w-full border rounded p-2"
            required
          />
        </div>

        <div>
          <label htmlFor="valorMateriais" className="block text-sm">Valor dos Materiais</label>
          <input
            id="valorMateriais"
            name="valorMateriais"
            type="number"
            step="0.01"
            value={form.valorMateriais}
            onChange={handleChange}
            className="mt-1 block w-full border rounded p-2"
            required
          />
        </div>

        <div>
          <label htmlFor="kmDeslocamento" className="block text-sm">KM de Deslocamento</label>
          <input
            id="kmDeslocamento"
            name="kmDeslocamento"
            type="number"
            value={form.kmDeslocamento}
            onChange={handleChange}
            className="mt-1 block w-full border rounded p-2"
            required
          />
        </div>

        <div>
          <label htmlFor="observacoes" className="block text-sm">Observações</label>
          <textarea
            id="observacoes"
            name="observacoes"
            rows={3}
            value={form.observacoes}
            onChange={handleChange}
            className="mt-1 block w-full border rounded p-2"
          />
        </div>

        <div className="flex gap-2">
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="px-4 py-2 border rounded hover:bg-gray-50"
          >
            Voltar
          </button>
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white font-medium rounded hover:bg-blue-700 transition"
          >
            Salvar OS
          </button>
        </div>
      </form>

      {/* Modal de Pré-visualização */}
      {previewOpen && (
        <div className="fixed inset-0 z-50 bg-black/50 flex items-center justify-center p-4">
          <div className="bg-white w-full max-w-5xl rounded-lg shadow-xl overflow-hidden flex flex-col">
            <div className="px-4 py-3 border-b flex items-center justify-between">
              <h3 className="font-semibold">Pré-visualização da OS</h3>
              <button onClick={() => setPreviewOpen(false)} className="text-gray-500 hover:text-gray-700">
                Fechar ✕
              </button>
            </div>
            <div className="h-[70vh]">
              <iframe ref={iframeRef} title="preview-os-tecnico" className="w-full h-full" srcDoc={previewHtml} />
            </div>
            <div className="px-4 py-3 border-t flex gap-2 justify-end">
              <button onClick={() => setPreviewOpen(false)} className="px-4 py-2 rounded border border-gray-300 hover:bg-gray-50">
                Fechar
              </button>
              <button onClick={printIframe} className="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
                Imprimir
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default CompleteOsForm
