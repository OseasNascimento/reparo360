// src/components/AgendaCalendario.tsx
import React, { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import ptBrLocale from '@fullcalendar/core/locales/pt-br';
import api from '@/services/api';
import { useAuth } from '@/hooks/useAuth';
import { AgendamentoDTO, Status } from '@/pages/PainelTecnico/PainelTecnico';

const statusColors: Record<Status, string> = {
  AGENDADO: '#2563EB',
  TECNICO_A_CAMINHO: '#FBBF24',
  TECNICO_CHEGOU: '#10B981',
  EM_ANDAMENTO: '#3B82F6',
  CONCLUIDO: '#6B7280',
  CANCELADO: '#EF4444',
};

interface AgendaCalendarioProps {
  agendamentos: AgendamentoDTO[];
  setAgendamentos: React.Dispatch<React.SetStateAction<AgendamentoDTO[]>>;
}

export default function AgendaCalendario({
  agendamentos,
  setAgendamentos,
}: AgendaCalendarioProps) {
  const { userId } = useAuth();
  const [selectedEvent, setSelectedEvent] = useState<AgendamentoDTO | null>(null);
  const [createDate, setCreateDate] = useState<string | null>(null);
  const [formData, setFormData] = useState<Partial<AgendamentoDTO>>({});

  const eventos = agendamentos.map(a => ({
    id: a.id.toString(),
    title: `${a.nomeCliente} - ${a.status}`,
    start: a.dataAgendamento,
    backgroundColor: statusColors[a.status],
  }));

  async function handleEventDrop(info: any) {
    const id = info.event.id;
    const newDate = info.event.start?.toISOString();
    if (!newDate) return;
    try {
      await api.put(`/api/agendamentos/${id}`, { dataAgendamento: newDate });
      setAgendamentos(prev =>
        prev.map(a =>
          a.id.toString() === id ? { ...a, dataAgendamento: newDate } : a
        )
      );
      alert('Agendamento reagendado com sucesso.');
    } catch (err) {
      console.error(err);
      info.revert();
      alert('Erro ao reagendar');
    }
  }

  function handleDateClick(info: any) {
    setCreateDate(info.dateStr);
    setFormData({
      dataAgendamento: info.dateStr,
      status: 'AGENDADO',
      tecnicoId: userId!,
    });
  }

  async function submitNew(e: React.FormEvent) {
    e.preventDefault();
    try {
      const resp = await api.post<AgendamentoDTO>('/api/agendamentos', formData);
      setAgendamentos(prev => [...prev, resp.data]);
      setCreateDate(null);
    } catch (err) {
      console.error(err);
      alert('Erro ao criar agendamento');
    }
  }

  return (
    <div className="mt-12">
      <h2 className="text-xl font-bold mb-4">Visualização em Calendário</h2>
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        headerToolbar={{
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay',
        }}
        locale={ptBrLocale}
        events={eventos}
        height="auto"
        editable={true}
        selectable={true}
        eventDrop={handleEventDrop}
        dateClick={handleDateClick}
        eventClick={info => {
          const id = info.event.id;
          const found = agendamentos.find(a => a.id.toString() === id) || null;
          setSelectedEvent(found);
        }}
      />

      {(selectedEvent || createDate) && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-lg p-6 w-full max-w-md">
            {selectedEvent && (
              <>
                <h3 className="text-lg font-bold mb-4">Detalhes do Agendamento</h3>
                <p><strong>Cliente:</strong> {selectedEvent.nomeCliente}</p>
                <p><strong>Data / Hora:</strong>{' '}
                  {new Date(selectedEvent.dataAgendamento).toLocaleString('pt-BR')}
                </p>
                <p><strong>Status:</strong> {selectedEvent.status}</p>
                <p><strong>Endereço:</strong> {`${selectedEvent.logradouro}, ${selectedEvent.numero}`}</p>
                {/* pode adicionar mais campos se quiser */}
              </>
            )}

            {createDate && (
              <>
                <h3 className="text-lg font-bold mb-4">Novo Agendamento em {createDate}</h3>
                <form onSubmit={submitNew} className="space-y-3">
                  <input
                    type="text"
                    placeholder="Nome do Cliente"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.nomeCliente || ''}
                    onChange={e => setFormData({ ...formData, nomeCliente: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Telefone"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.telefone || ''}
                    onChange={e => setFormData({ ...formData, telefone: e.target.value })}
                  />
                  <input
                    type="email"
                    placeholder="E-mail"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.email || ''}
                    onChange={e => setFormData({ ...formData, email: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Logradouro"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.logradouro || ''}
                    onChange={e => setFormData({ ...formData, logradouro: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Número"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.numero || ''}
                    onChange={e => setFormData({ ...formData, numero: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Bairro"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.bairro || ''}
                    onChange={e => setFormData({ ...formData, bairro: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="Cidade"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.cidade || ''}
                    onChange={e => setFormData({ ...formData, cidade: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="UF"
                    required
                    className="w-full border px-3 py-2 rounded"
                    maxLength={2}
                    value={formData.uf || ''}
                    onChange={e => setFormData({ ...formData, uf: e.target.value })}
                  />
                  <input
                    type="text"
                    placeholder="CEP"
                    required
                    className="w-full border px-3 py-2 rounded"
                    value={formData.cep || ''}
                    onChange={e => setFormData({ ...formData, cep: e.target.value })}
                  />
                  <button
                    type="submit"
                    className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700 transition"
                  >
                    Criar Agendamento
                  </button>
                </form>
              </>
            )}

            <button
              onClick={() => {
                setSelectedEvent(null);
                setCreateDate(null);
              }}
              className="mt-4 w-full bg-gray-300 text-gray-700 py-2 rounded hover:bg-gray-400 transition"
            >
              Fechar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
