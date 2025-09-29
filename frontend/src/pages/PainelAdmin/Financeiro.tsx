// src/pages/Financeiro/Financeiro.tsx
import React, { useEffect, useState, FormEvent } from 'react';
import api from '@/services/api';
import type {
  LancamentoFinanceiroDTO,
  ContaCaixaDTO,
  CategoriaFinanceiraDTO
} from '@/api';
import SEO from '@/components/SEO';

const Financeiro: React.FC = () => {
  const [lancamentos, setLancamentos] = useState<LancamentoFinanceiroDTO[]>([]);
  const [contas, setContas] = useState<ContaCaixaDTO[]>([]);
  const [categorias, setCategorias] = useState<CategoriaFinanceiraDTO[]>([]);
  const [form, setForm] = useState<Partial<LancamentoFinanceiroDTO>>({
    id: 0,
    contaCaixaId: 0,
    categoriaFinanceiraId: 0,
    tipoTransacao: 'RECEITA',
    valor: 0,
    dataLancamento: '',
    descricao: ''
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchDados = () => {
    api.get<LancamentoFinanceiroDTO[]>('/financeiro/lancamentos')
      .then(resp => setLancamentos(resp.data))
      .catch(err => console.error(err));

    api.get<ContaCaixaDTO[]>('/financeiro/contas')
      .then(resp => setContas(resp.data))
      .catch(err => console.error(err));

    api.get<CategoriaFinanceiraDTO[]>('/financeiro/categorias')
      .then(resp => setCategorias(resp.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchDados();
  }, []);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setForm(f => ({
      ...f,
      [name]:
        name === 'contaCaixaId' ||
        name === 'categoriaFinanceiraId' ||
        name === 'valor'
          ? Number(value)
          : value
    }));
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await api.post<LancamentoFinanceiroDTO>(
        '/financeiro/lancamentos',
        form
      );
      setForm({
        id: 0,
        contaCaixaId: 0,
        categoriaFinanceiraId: 0,
        tipoTransacao: 'RECEITA',
        valor: 0,
        dataLancamento: '',
        descricao: ''
      });
      fetchDados();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao salvar lançamento');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir este lançamento?')) return;
    try {
      await api.delete(`/financeiro/lancamentos/${id}`);
      fetchDados();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      {/* <SEO
        title="Financeiro"
        description="Gerencie contas, categorias e lançamentos financeiros."
      /> */}

      <div className="p-6 bg-white rounded shadow space-y-6">
        <h2 className="text-xl font-bold">Lançamentos Financeiros</h2>
        {error && <p className="text-red-600">{error}</p>}

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-3 gap-4"
        >
          <select
            name="contaCaixaId"
            value={form.contaCaixaId || ''}
            onChange={handleChange}
            required
            className="border rounded px-3 py-2"
          >
            <option value="" disabled>
              Selecione a conta
            </option>
            {contas.map(c => (
              <option key={c.id} value={c.id}>
                {c.nome}
              </option>
            ))}
          </select>

          <select
            name="categoriaFinanceiraId"
            value={form.categoriaFinanceiraId || ''}
            onChange={handleChange}
            required
            className="border rounded px-3 py-2"
          >
            <option value="" disabled>
              Selecione a categoria
            </option>
            {categorias.map(c => (
              <option key={c.id} value={c.id}>
                {c.nome}
              </option>
            ))}
          </select>

          <select
            name="tipoTransacao"
            value={form.tipoTransacao || 'RECEITA'}
            onChange={handleChange}
            className="border rounded px-3 py-2"
          >
            <option value="RECEITA">Receita</option>
            <option value="DESPESA">Despesa</option>
          </select>

          <input
            name="valor"
            type="number"
            min="0.01"
            step="0.01"
            value={form.valor || ''}
            onChange={handleChange}
            required
            placeholder="Valor"
            className="border rounded px-3 py-2"
          />

          <input
            name="dataLancamento"
            type="datetime-local"
            value={form.dataLancamento || ''}
            onChange={handleChange}
            required
            className="border rounded px-3 py-2"
          />

          <input
            name="descricao"
            value={form.descricao || ''}
            onChange={handleChange}
            placeholder="Descrição"
            className="border rounded px-3 py-2"
          />

          <button
            type="submit"
            disabled={loading}
            className="col-span-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
          >
            {loading ? 'Salvando...' : 'Adicionar Lançamento'}
          </button>
        </form>

        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2">Conta</th>
              <th className="px-4 py-2">Categoria</th>
              <th className="px-4 py-2">Tipo</th>
              <th className="px-4 py-2">Valor</th>
              <th className="px-4 py-2">Data</th>
              <th className="px-4 py-2">Ações</th>
            </tr>
          </thead>
          <tbody>
            {lancamentos.map(l => (
              <tr key={l.id} className="border-t">
                <td className="px-4 py-2">
                  {contas.find(c => c.id === l.contaCaixaId)?.nome}
                </td>
                <td className="px-4 py-2">
                  {categorias.find(
                    c => c.id === l.categoriaFinanceiraId
                  )?.nome}
                </td>
                <td className="px-4 py-2">{l.tipoTransacao}</td>
                <td className="px-4 py-2">R$ {l.valor.toFixed(2)}</td>
                <td className="px-4 py-2">
                  {l.dataLancamento
                    ? new Date(l.dataLancamento).toLocaleString('pt-BR')
                    : 'Data não informada'}
                </td>
                <td className="px-4 py-2">
                  <button
                    onClick={() => handleDelete(l.id as number)}
                    className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
                  >
                    Excluir
                  </button>
                </td>
              </tr>
            ))}
            {lancamentos.length === 0 && (
              <tr>
                <td
                  colSpan={6}
                  className="p-4 text-center text-gray-500"
                >
                  Nenhum lançamento encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default Financeiro;
