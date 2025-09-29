// src/pages/PainelAdmin/Servicos.tsx
import React, { useEffect, useState, FormEvent } from 'react';
import api from '@/services/api';
import type { ServicoDTO } from '@/api';
import SEO from '@/components/SEO';

const Servicos: React.FC = () => {
  const [servicos, setServicos] = useState<ServicoDTO[]>([]);
  const [form, setForm] = useState<Partial<ServicoDTO>>({
    idServico: undefined,
    descricao: '',
    categoria: '',
    valorEstimado: undefined,
    tempoEstimado: undefined,
  });
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchServicos = async () => {
    try {
      const resp = await api.get<ServicoDTO[]>('/servicos');
      setServicos(resp.data);
    } catch (err) {
      console.error('Erro ao buscar serviços:', err);
    }
  };

  useEffect(() => {
    fetchServicos();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm(f => ({
      ...f,
      [name]:
        name === 'valorEstimado' || name === 'tempoEstimado'
          ? value === ''
            ? undefined
            : Number(value)
          : value,
    }));
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      if (editing && form.idServico != null) {
        await api.put<ServicoDTO>(`/servicos/${form.idServico}`, form);
      } else {
        await api.post<ServicoDTO>('/servicos', form);
      }
      setForm({
        idServico: undefined,
        descricao: '',
        categoria: '',
        valorEstimado: undefined,
        tempoEstimado: undefined,
      });
      setEditing(false);
      await fetchServicos();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao salvar serviço');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (s: ServicoDTO) => {
    setForm({
      idServico: s.idServico,
      descricao: s.descricao,
      categoria: s.categoria,
      valorEstimado: s.valorEstimado,
      tempoEstimado: s.tempoEstimado,
    });
    setEditing(true);
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir este serviço?')) return;
    try {
      await api.delete(`/servicos/${id}`);
      await fetchServicos();
    } catch (err) {
      console.error('Erro ao excluir serviço:', err);
    }
  };

  return (
    <>
      <div className="p-6 bg-white rounded shadow">
        <h2 className="text-2xl font-bold mb-4">Serviços</h2>

        {error && <p className="text-red-600 mb-4">{error}</p>}

        <form
          onSubmit={handleSubmit}
          className="mb-6 grid grid-cols-1 md:grid-cols-2 gap-4"
        >
          <input
            name="descricao"
            value={form.descricao || ''}
            onChange={handleChange}
            required
            placeholder="Digite a descrição do serviço"
            className="border rounded px-3 py-2"
          />
          <input
            name="categoria"
            value={form.categoria || ''}
            onChange={handleChange}
            required
            placeholder="Digite a categoria (ex: Elétrica, Refrigeração)"
            className="border rounded px-3 py-2"
          />
          <input
            name="valorEstimado"
            type="number"
            min="0"
            step="0.01"
            value={form.valorEstimado ?? ''}
            onChange={handleChange}
            required
            placeholder="Valor estimado (R$) — ex: 150.00"
            className="border rounded px-3 py-2"
          />
          <input
            name="tempoEstimado"
            type="number"
            min="1"
            step="1"
            value={form.tempoEstimado ?? ''}
            onChange={handleChange}
            required
            placeholder="Tempo estimado (min) — mínimo 1"
            className="border rounded px-3 py-2"
          />
          <button
            type="submit"
            disabled={loading}
            className="col-span-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
          >
            {loading
              ? editing
                ? 'Atualizando...'
                : 'Criando...'
              : editing
              ? 'Atualizar Serviço'
              : 'Criar Serviço'}
          </button>
        </form>

        <div className="overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead>
              <tr className="bg-gray-100">
                <th className="px-4 py-2 text-left">Descrição</th>
                <th className="px-4 py-2 text-left">Categoria</th>
                <th className="px-4 py-2 text-left">Valor (R$)</th>
                <th className="px-4 py-2 text-left">Tempo (min)</th>
                <th className="px-4 py-2 text-left">Ações</th>
              </tr>
            </thead>
            <tbody>
              {servicos.map(s => (
                <tr key={s.idServico} className="border-t">
                  <td className="px-4 py-2">{s.descricao}</td>
                  <td className="px-4 py-2">{s.categoria}</td>
                  <td className="px-4 py-2">R$ {s.valorEstimado.toFixed(2)}</td>
                  <td className="px-4 py-2">{s.tempoEstimado}</td>
                  <td className="px-4 py-2 flex space-x-2">
                    <button
                      onClick={() => handleEdit(s)}
                      className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => s.idServico != null && handleDelete(s.idServico)}
                      className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
                    >
                      Excluir
                    </button>
                  </td>
                </tr>
              ))}
              {servicos.length === 0 && (
                <tr>
                  <td colSpan={5} className="p-4 text-center text-gray-500">
                    Nenhum serviço encontrado.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
};

export default Servicos;
