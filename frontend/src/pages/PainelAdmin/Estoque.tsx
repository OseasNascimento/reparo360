import React, { useEffect, useState, FormEvent } from 'react';
import api from '@/services/api';
import type { ProdutoDTO } from '@/api';
import SEO from '@/components/SEO';

const Estoque: React.FC = () => {
  const [produtos, setProdutos] = useState<ProdutoDTO[]>([]);
  const [form, setForm] = useState<Partial<ProdutoDTO>>({
    id: undefined,
    nome: '',
    descricao: '',
    quantidadeEstoque: undefined,
    valor: undefined,
  });
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchProdutos = () => {
    api
      .get<ProdutoDTO[]>('/estoque/produtos')
      .then(resp => setProdutos(resp.data))
      .catch(err => console.error(err));
  };

  useEffect(fetchProdutos, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm(f => ({
      ...f,
      [name]:
        name === 'quantidadeEstoque' || name === 'valor'
          ? value === ''
            ? undefined
            : Number(value)
          : value,
    }));
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      if (editing && form.id != null) {
        await api.put<ProdutoDTO>(`/estoque/produtos/${form.id}`, form);
      } else {
        await api.post<ProdutoDTO>('/estoque/produtos', form);
      }
      setForm({
        id: undefined,
        nome: '',
        descricao: '',
        quantidadeEstoque: undefined,
        valor: undefined,
      });
      setEditing(false);
      fetchProdutos();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao salvar produto');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (p: ProdutoDTO) => {
    setForm(p);
    setEditing(true);
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir este produto?')) return;
    try {
      await api.delete(`/estoque/produtos/${id}`);
      fetchProdutos();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <div className="p-6 bg-white rounded shadow">
        <h2 className="text-xl font-bold mb-4">Estoque de Produtos</h2>
        {error && <p className="text-red-600 mb-2">{error}</p>}

        <form onSubmit={handleSubmit} className="mb-6 grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            name="nome"
            value={form.nome || ''}
            onChange={handleChange}
            required
            placeholder="Digite o nome do produto"
            className="border rounded px-3 py-2"
          />
          <input
            name="descricao"
            value={form.descricao || ''}
            onChange={handleChange}
            placeholder="Descrição do produto (opcional)"
            className="border rounded px-3 py-2"
          />
          <input
            name="quantidadeEstoque"
            type="number"
            min="0"
            value={form.quantidadeEstoque ?? ''}
            onChange={handleChange}
            required
            placeholder="Quantidade em estoque"
            className="border rounded px-3 py-2"
          />
          <input
            name="valor"
            type="number"
            step="0.01"
            min="0"
            value={form.valor ?? ''}
            onChange={handleChange}
            required
            placeholder="Valor do produto (R$)"
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
              ? 'Atualizar Produto'
              : 'Criar Produto'}
          </button>
        </form>

        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Nome</th>
              <th className="px-4 py-2 text-left">Descrição</th>
              <th className="px-4 py-2 text-left">Qtd. Estoque</th>
              <th className="px-4 py-2 text-left">Valor (R$)</th>
              <th className="px-4 py-2 text-left">Ações</th>
            </tr>
          </thead>
          <tbody>
            {produtos.map(p => (
              <tr key={p.id} className="border-t">
                <td className="px-4 py-2">{p.nome}</td>
                <td className="px-4 py-2">{p.descricao}</td>
                <td className="px-4 py-2">{p.quantidadeEstoque}</td>
                <td className="px-4 py-2">R$ {p.valor.toFixed(2)}</td>
                <td className="px-4 py-2 space-x-2">
                  <button
                    onClick={() => handleEdit(p)}
                    className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition"
                  >
                    Editar
                  </button>
                  <button
                    onClick={() => p.id != null && handleDelete(p.id)}
                    className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
                  >
                    Excluir
                  </button>
                </td>
              </tr>
            ))}
            {produtos.length === 0 && (
              <tr>
                <td colSpan={5} className="p-4 text-center text-gray-500">
                  Nenhum produto encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default Estoque;
