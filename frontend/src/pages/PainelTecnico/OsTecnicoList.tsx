// src/pages/PainelTecnico/OsTecnicoList.tsx
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '@/services/api'
import { useAuth } from '@/hooks/useAuth'
import SidebarTecnico from '@/components/SidebarTecnico'
import type { AgendamentoDTO } from './PainelTecnico' // reaproveita o tipo já definido
import type { OrdemServicoDTO } from '@/api'

const brl = (n?: number|null) =>
  typeof n === 'number' ? n.toLocaleString('pt-BR',{style:'currency',currency:'BRL'}) : '—'

export default function OsTecnicoList() {
  const { userId, role } = useAuth()
  const [loading, setLoading] = useState(true)
  const [rows, setRows] = useState<(OrdemServicoDTO & {ag: AgendamentoDTO | undefined})[]>([])

  useEffect(() => {
    (async () => {
      try {
        const [osRes, agRes] = await Promise.all([
          api.get<OrdemServicoDTO[]>('/os'),
          api.get<AgendamentoDTO[]>('/agendamentos')
        ])

        // mapa agendamentoId -> agendamento
        const mapaAg = new Map<number, AgendamentoDTO>()
        agRes.data.forEach(a => mapaAg.set(a.id, a))

        // se for técnico, filtra só OS cujos agendamentos pertencem a ele
        const filtradas = (role === 'TECNICO' && userId)
          ? osRes.data.filter(o => {
              const ag = mapaAg.get(o.agendamentoId as number)
              return ag?.tecnicoId === userId
            })
          : osRes.data

        setRows(filtradas.map(o => ({ ...o, ag: mapaAg.get(o.agendamentoId as number) })))
      } finally {
        setLoading(false)
      }
    })()
  }, [role, userId])

  if (loading) return <div className="ml-64 p-6">Carregando OS…</div>

  return (
    <>
      <SidebarTecnico />
      <main className="ml-64 p-6 min-h-screen bg-gray-50">
        <h1 className="text-2xl font-bold mb-6">Minhas Ordens de Serviço</h1>

        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-4 py-2 text-left">OS</th>
                <th className="px-4 py-2 text-left">Cliente</th>
                <th className="px-4 py-2 text-left">Status</th>
                <th className="px-4 py-2 text-left">Serviço</th>
                <th className="px-4 py-2 text-left">Materiais</th>
                <th className="px-4 py-2 text-left">Ações</th>
              </tr>
            </thead>
            <tbody>
              {rows.map(o => (
                <tr key={o.id} className="border-t">
                  <td className="px-4 py-2">#{o.id}</td>
                  <td className="px-4 py-2">{o.ag?.nomeCliente ?? '—'}</td>
                  <td className="px-4 py-2">{o.status.replace('_',' ')}</td>
                  <td className="px-4 py-2">{brl(o.valorServico as any)}</td>
                  <td className="px-4 py-2">{brl(o.valorMateriais as any)}</td>
                  <td className="px-4 py-2">
                    <Link
                      to={`/painel-tecnico/os/${o.id}`}
                      className="px-3 py-1 rounded bg-blue-600 text-white hover:bg-blue-700 text-sm"
                    >
                      Completar / Editar
                    </Link>
                  </td>
                </tr>
              ))}
              {rows.length === 0 && (
                <tr>
                  <td colSpan={6} className="px-4 py-6 text-center text-gray-500">
                    Nenhuma OS para exibir.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </>
  )
}
