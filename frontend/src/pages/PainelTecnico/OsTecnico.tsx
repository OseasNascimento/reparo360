// src/pages/PainelTecnico/OsTecnico.tsx
import React, { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '@/services/api';
import { useAuth } from '@/hooks/useAuth';
import SidebarTecnico from '@/components/SidebarTecnico';

type Status =
  | 'AGENDADA'
  | 'EM_ANDAMENTO'
  | 'ASSINADA'
  | 'RECUSADA'
  | 'CONCLUIDA'
  | 'CANCELADA';

type OrdemServicoDTO = {
  id: number;
  agendamentoId: number;
  status: Status;
  valorServico?: number | null;
  valorMateriais?: number | null;
  kmDeslocamento?: number | null;
  observacoes?: string | null;
};

type AgendamentoDTO = {
  id: number;
  tecnicoId: number;
  nomeCliente: string;
  dataAgendamento: string;
};

const statusClasses: Record<Status, string> = {
  AGENDADA: 'bg-yellow-100 text-yellow-800',
  EM_ANDAMENTO: 'bg-indigo-100 text-indigo-800',
  ASSINADA: 'bg-emerald-100 text-emerald-800',
  RECUSADA: 'bg-red-100 text-red-800',
  CONCLUIDA: 'bg-emerald-200 text-emerald-800',
  CANCELADA: 'bg-gray-200 text-gray-700',
};

const currency = (v?: number | null) =>
  typeof v === 'number' ? v.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : '—';

export default function OsTecnico() {
  const { userId } = useAuth();
  const navigate = useNavigate();

  const [os, setOs] = useState<OrdemServicoDTO[]>([]);
  const [ags, setAgs] = useState<AgendamentoDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState<string | null>(null);

  useEffect(() => {
    let mounted = true;
    (async () => {
      try {
        setLoading(true);
        setErr(null);
        const [osResp, agResp] = await Promise.all([
          api.get<OrdemServicoDTO[]>('/os'),
          api.get<AgendamentoDTO[]>('/agendamentos'),
        ]);
        if (!mounted) return;
        setOs(osResp.data ?? []);
        setAgs(agResp.data ?? []);
      } catch (e) {
        console.error(e);
        setErr('Falha ao carregar suas Ordens de Serviço.');
      } finally {
        if (mounted) setLoading(false);
      }
    })();
    return () => { mounted = false; };
  }, []);

  const minhasOs = useMemo(() => {
    if (!userId) return [];
    const mapAg = new Map(ags.map(a => [a.id, a]));
    return os
      .filter(o => {
        const ag = mapAg.get(o.agendamentoId);
        return ag?.tecnicoId === userId;
      })
      .sort((a, b) => {
        const A = mapAg.get(a.agendamentoId)?.dataAgendamento ?? '';
        const B = mapAg.get(b.agendamentoId)?.dataAgendamento ?? '';
        return A.localeCompare(B);
      });
  }, [os, ags, userId]);

  if (loading) return (<div className="min-h-screen"><SidebarTecnico /><main className="ml-64 p-6">Carregando OS…</main></div>);
  if (err)      return (<div className="min-h-screen"><SidebarTecnico /><main className="ml-64 p-6 text-red-600">{err}</main></div>);

  return (
    <div className="min-h-screen bg-gray-50">
      <SidebarTecnico />
      <main className="ml-64 p-6">
        <h1 className="text-2xl font-bold mb-6">Minhas Ordens de Serviço</h1>

        {minhasOs.length === 0 ? (
          <div className="p-6 bg-white rounded-xl shadow text-gray-600">
            Nenhuma OS encontrada para você.
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {minhasOs.map((o) => {
              const ag = ags.find(a => a.id === o.agendamentoId);
              const quando = ag ? new Date(ag.dataAgendamento) : null;
              const data = quando ? quando.toLocaleDateString('pt-BR') : '—';
              const hora = quando ? quando.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) : '—';

              return (
                <div key={o.id} className="bg-white rounded-xl shadow p-5 flex flex-col">
                  <div className="flex justify-between items-center mb-3">
                    <span className="font-mono text-sm text-gray-500">OS #{o.id}</span>
                    <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusClasses[o.status]}`}>
                      {o.status.replace(/_/g, ' ')}
                    </span>
                  </div>

                  <div className="text-sm text-gray-700 space-y-1 mb-4">
                    <div><strong>Agendamento:</strong> #{o.agendamentoId}</div>
                    <div><strong>Data:</strong> {data} <strong>Hora:</strong> {hora}</div>
                    <div><strong>Serviço:</strong> {currency(o.valorServico)}</div>
                    <div><strong>Materiais:</strong> {currency(o.valorMateriais)}</div>
                    {typeof o.kmDeslocamento === 'number' && <div><strong>KM:</strong> {o.kmDeslocamento}</div>}
                    {o.observacoes && <div className="text-gray-600"><strong>Obs:</strong> {o.observacoes}</div>}
                  </div>

                  <div className="mt-auto">
                    <button
                      onClick={() => navigate(`/painel-tecnico/os/${o.id}`)}
                      className="px-3 py-1 rounded bg-emerald-600 text-white hover:bg-emerald-700 text-sm"
                    >
                      Visualizar Os
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </main>
    </div>
  );
}
