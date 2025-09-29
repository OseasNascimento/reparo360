// src/pages/PainelTecnico/PainelTecnico.tsx
import React, { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import api from '@/services/api';
import { useAuth } from '@/hooks/useAuth';
import AgendaCalendario from '@/components/AgendaCalendario';
import SidebarTecnico from '@/components/SidebarTecnico';

export type Status =
  | 'AGENDADO'
  | 'TECNICO_A_CAMINHO'
  | 'TECNICO_CHEGOU'
  | 'EM_ANDAMENTO'
  | 'CONCLUIDO'
  | 'CANCELADO';

export interface AgendamentoDTO {
  id: number;
  nomeCliente: string;
  telefone: string;
  email: string;
  logradouro: string;
  numero: string;
  complemento?: string;
  bairro: string;
  cidade: string;
  uf: string;
  cep: string;
  dataAgendamento: string;
  status: Status;
  tecnicoId: number;
  observacoes: string;
}

const TODOS_STATUS: Status[] = [
  'AGENDADO',
  'TECNICO_A_CAMINHO',
  'TECNICO_CHEGOU',
  'EM_ANDAMENTO',
  'CONCLUIDO',
  'CANCELADO',
];

const statusClasses: Record<Status, string> = {
  AGENDADO: 'bg-yellow-100 text-yellow-800',
  TECNICO_A_CAMINHO: 'bg-blue-100 text-blue-800',
  TECNICO_CHEGOU: 'bg-green-100 text-green-800',
  EM_ANDAMENTO: 'bg-indigo-100 text-indigo-800',
  CONCLUIDO: 'bg-green-200 text-green-800',
  CANCELADO: 'bg-red-100 text-red-800',
};

export default function PainelTecnico() {
  const { userId, role, userName } = useAuth();
  const [agendamentos, setAgendamentos] = useState<AgendamentoDTO[]>([]);
  const [updatingId, setUpdatingId] = useState<number | null>(null);

  useEffect(() => {
    if (!userId) return;
    api
      .get<AgendamentoDTO[]>('/agendamentos')
      .then(({ data }) => {
        const visiveis = role === 'TECNICO' ? data.filter(a => a.tecnicoId === userId) : data;
        setAgendamentos(visiveis);
      })
      .catch(console.error);
  }, [userId, role]);

  const handleStatusChange = async (id: number, novoStatus: Status) => {
    setUpdatingId(id);
    try {
      await api.patch(`/agendamentos/${id}/status`, null, { params: { status: novoStatus } });
      setAgendamentos(prev => prev.map(a => (a.id === id ? { ...a, status: novoStatus } : a)));
    } catch (err) {
      console.error(err);
    } finally {
      setUpdatingId(null);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <SidebarTecnico />
      {/* a margem evita que a sidebar cubra o conteúdo */}
      <main className="ml-64 p-6">
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ type: 'spring', stiffness: 150, damping: 20 }}
          className="mb-6 p-4 bg-indigo-50 border-l-4 border-indigo-400 rounded-lg shadow"
        >
          <h2 className="text-lg font-medium text-gray-800">
            Bem-vindo, {userName || 'Técnico'}!
          </h2>
        </motion.div>

        <h1 className="text-2xl font-bold mb-6">Meus Agendamentos</h1>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {agendamentos.map((a, i) => {
            const dt = new Date(a.dataAgendamento);
            const dateStr = dt.toLocaleDateString('pt-BR');
            const timeStr = dt.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
            const periodStr = dt.getHours() < 12 ? 'MANHÃ' : 'TARDE';

            return (
              <motion.div
                key={a.id}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ type: 'spring', stiffness: 100, damping: 12, delay: i * 0.05 }}
                whileHover={{ scale: 1.01 }}
                className="bg-white rounded-xl shadow p-5 flex flex-col"
              >
                <div className="flex justify-between items-center mb-4">
                  <span className="font-mono text-sm text-gray-500">#{a.id}</span>
                  <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusClasses[a.status]}`}>
                    {a.status.replace(/_/g, ' ')}
                  </span>
                </div>

                <div className="space-y-1 mb-4">
                  <h2 className="font-semibold text-lg">{a.nomeCliente}</h2>
                  <p className="text-sm text-gray-600">{a.telefone}</p>
                  {a.email && (
                    <a href={`mailto:${a.email}`} className="text-sm text-blue-600 hover:underline">
                      {a.email}
                    </a>
                  )}
                </div>

                <div className="text-sm text-gray-700 mb-4 space-y-1">
                  <p>{a.logradouro}, {a.numero}{a.complemento ? `, ${a.complemento}` : ''}</p>
                  <p>{a.bairro} – {a.cidade}/{a.uf}</p>
                  <p>CEP: {a.cep}</p>
                </div>

                <div className="text-sm text-gray-700 mb-4 space-y-1">
                  <p><strong>Data:</strong> {dateStr}</p>
                  <p><strong>Horário:</strong> {timeStr}</p>
                  <p><strong>Período:</strong> {periodStr}</p>
                  <p><strong>Defeito:</strong> {a.observacoes || '—'}</p>
                </div>

                <div className="mt-auto flex items-center gap-2">
                  <select
                    disabled={updatingId === a.id}
                    value={a.status}
                    onChange={e => handleStatusChange(a.id, e.target.value as Status)}
                    className="border rounded px-2 py-1 text-sm"
                  >
                    {TODOS_STATUS.map(s => (
                      <option key={s} value={s}>{s.replace(/_/g, ' ')}</option>
                    ))}
                  </select>
                </div>
              </motion.div>
            );
          })}
        </div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="mt-10 p-4 bg-white rounded-xl shadow max-w-5xl"
        >
          <h3 className="text-xl font-semibold text-gray-800 mb-4">Visualização em Calendário</h3>
          <div className="overflow-auto">
            <AgendaCalendario agendamentos={agendamentos} setAgendamentos={setAgendamentos} />
          </div>
        </motion.div>
      </main>
    </div>
  );
}
