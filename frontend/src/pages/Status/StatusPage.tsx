// src/pages/StatusPage.tsx
import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import api from '@/services/api';

// 1) Função utilitária para parse de JWT
type JwtPayload = { sub: string; roles: string[] }
function parseJwt<T>(token: string): T | null {
  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const json = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    return JSON.parse(json) as T
  } catch {
    return null
  }
}

// 2) Tipagens mínimas
interface AgendamentoDTO {
  id: number
  nomeCliente: string
  dataAgendamento: string
  status: string
  servicosId: number[]
  logradouro: string
  numero: string
  bairro: string
  cidade: string
  uf: string
  cep: string
}
interface ServicoDTO {
  idServico: number
  descricao: string
}

// 3) Definição sequencial dos passos + labels + mensagens
const steps = [
  {
    key: 'AGENDADO',
    label: 'Agendamento confirmado',
    message: 'Seu agendamento foi confirmado com sucesso.'
  },
  {
    key: 'TECNICO_A_CAMINHO',
    label: 'Técnico a caminho',
    message: 'Nosso técnico está a caminho.'
  },
  {
    key: 'TECNICO_CHEGOU',
    label: 'Técnico chegou',
    message: 'O técnico acabou de chegar ao local.'
  },
  {
    key: 'EM_ANDAMENTO',
    label: 'Serviço em andamento',
    message: 'Seu serviço está em andamento.'
  },
  {
    key: 'CONCLUIDO',
    label: 'Serviço concluído',
    message: 'Serviço concluído. Obrigado pela preferência!'
  },
  {
    key: 'CANCELADO',
    label: 'Agendamento cancelado',
    message: 'Seu agendamento foi cancelado.'
  }
]

const StatusPage: React.FC = () => {
  const { id } = useParams<{ id: string }>()
  const [agendamento, setAgendamento] = useState<AgendamentoDTO | null>(null)
  const [servicos, setServicos] = useState<ServicoDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [isTecnico, setIsTecnico] = useState(false)

  // 4) Detecta se é técnico
  useEffect(() => {
    const token = localStorage.getItem('token')
    if (!token) return
    const payload = parseJwt<JwtPayload>(token)
    setIsTecnico(payload?.roles.includes('ROLE_TECNICO') ?? false)
  }, [])

  // 5) Fetch + polling a cada 5s
  useEffect(() => {
    if (!id) {
      setError('ID de agendamento inválido.')
      setLoading(false)
      return
    }

    let timer: number
    const fetchData = async () => {
      try {
        const [{ data: ag }, { data: allS }] = await Promise.all([
          api.get<AgendamentoDTO>(`/agendamentos/${id}`),
          api.get<ServicoDTO[]>('/servicos')
        ])
        setAgendamento(ag)
        setServicos(allS.filter(s => ag.servicosId.includes(s.idServico)))
        setError(null)
      } catch (err: any) {
        setError('Não foi possível carregar o status.')
        clearInterval(timer)
      } finally {
        setLoading(false)
      }
    }

    fetchData()
    timer = window.setInterval(fetchData, 5000)
    return () => clearInterval(timer)
  }, [id])

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <span className="text-xl">Carregando status do agendamento…</span>
      </div>
    )
  }

  if (error || !agendamento) {
    return (
      <div className="max-w-md mx-auto mt-20 p-6 bg-red-100 text-red-800 rounded-lg">
        <h1 className="text-2xl font-bold mb-4">Ops!</h1>
        <p>{error || 'Agendamento não encontrado.'}</p>
        <Link to="/" className="mt-4 inline-block text-blue-600 hover:underline">
          Voltar à página inicial
        </Link>
      </div>
    )
  }

  // 6) Em qual passo estamos?
  const currentIndex = steps.findIndex(s => s.key === agendamento.status)
  const currentStep = steps[currentIndex]

  return (
    <div className="min-h-screen bg-gray-50 py-10 px-4">
      <div className="max-w-2xl mx-auto bg-white shadow-lg rounded-2xl p-8">
        <h1 className="text-3xl font-semibold mb-6 text-center">
          Status do Agendamento #{agendamento.id}
        </h1>

        {/* Stepper visual */}
        <div className="flex justify-between mb-4">
          {steps.map((step, idx) => {
            const done = idx < currentIndex
            const active = idx === currentIndex
            return (
              <div key={step.key} className="flex-1 text-center relative">
                <div
                  className={`
                    w-8 h-8 mx-auto rounded-full flex items-center justify-center
                    ${done ? 'bg-green-500 text-white' : active ? 'bg-blue-500 text-white' : 'bg-gray-300 text-gray-600'}
                  `}
                >
                  {idx + 1}
                </div>
                <p className="text-xs mt-2">{step.label}</p>
                {idx < steps.length - 1 && (
                  <div
                    className={`absolute top-4 left-1/2 w-full h-1 transform -translate-x-1/2 ${
                      done ? 'bg-green-500' : 'bg-gray-300'
                    }`}
                    style={{ zIndex: -1 }}
                  />
                )}
              </div>
            )
          })}
        </div>

        {/* Mensagem do passo atual */}
        {currentStep && (
          <p className="text-center italic text-gray-600 mb-6">
            {currentStep.message}
          </p>
        )}

        {/* Detalhes do agendamento */}
        <div className="space-y-4 mb-6">
          <div>
            <h2 className="font-medium">Cliente</h2>
            <p>{agendamento.nomeCliente}</p>
          </div>
          <div>
            <h2 className="font-medium">Data e Hora</h2>
            <p>{new Date(agendamento.dataAgendamento).toLocaleString('pt-BR')}</p>
          </div>
          <div>
            <h2 className="font-medium">Endereço</h2>
            <p>
              {agendamento.logradouro}, {agendamento.numero} – {agendamento.bairro},{' '}
              {agendamento.cidade} – {agendamento.uf}, {agendamento.cep}
            </p>
          </div>
          <div>
            <h2 className="font-medium">Serviços</h2>
            <ul className="list-disc list-inside">
              {servicos.map(s => (
                <li key={s.idServico}>{s.descricao}</li>
              ))}
            </ul>
          </div>
        </div>

        <Link to="/" className="block text-center text-blue-600 hover:underline">
          Voltar à página inicial
        </Link>
      </div>
    </div>
  )
}

export default StatusPage
