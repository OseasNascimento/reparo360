// src/pages/PainelAdmin/Clientes.tsx
import React, { useEffect, useMemo, useState, FormEvent } from 'react';
import api from '@/services/api';
import type { ClienteDTO } from '@/api'; // deve conter: idCliente, nome, email, telefone, cep, logradouro, numero, complemento?, bairro, cidade, uf

type PartialCliente = Partial<ClienteDTO> & {
  // para compat com registros antigos que tenham "endereco" agregado
  endereco?: string;
};

const defaultForm: PartialCliente = {
  idCliente: undefined,
  nome: '',
  email: '',
  telefone: '',
  cep: '',
  logradouro: '',
  numero: '',
  complemento: '',
  bairro: '',
  cidade: '',
  uf: '',
};

const UF_LIST = [
  'AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO',
];

const mascaraCep = (v: string) =>
  v.replace(/\D/g, '').slice(0, 8).replace(/^(\d{5})(\d{0,3})$/, (_, a, b) => (b ? `${a}-${b}` : a));

const mascaraTelefone = (v: string) => {
  const d = v.replace(/\D/g, '').slice(0, 11);
  if (d.length <= 10) {
    return d
      .replace(/^(\d{2})(\d)/, '($1) $2')
      .replace(/(\d{4})(\d{1,4})$/, '$1-$2');
  }
  return d
    .replace(/^(\d{2})(\d)/, '($1) $2')
    .replace(/(\d{5})(\d{1,4})$/, '$1-$2');
};

const composeEndereco = (c: PartialCliente) => {
  if ((c as any).endereco) return (c as any).endereco; // fallback legados
  const partes = [c.logradouro, c.numero, c.complemento].filter(Boolean).join(', ');
  const local = [c.bairro, c.cidade, c?.uf].filter(Boolean).join(' - ');
  const cep = c.cep ? `CEP ${c.cep}` : '';
  return [partes, local, cep].filter(Boolean).join(' • ');
};

const Clientes: React.FC = () => {
  const [clientes, setClientes] = useState<ClienteDTO[]>([]);
  const [form, setForm] = useState<PartialCliente>(defaultForm);
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [buscandoCep, setBuscandoCep] = useState(false);

  const podeSalvar = useMemo(() => {
    return Boolean(
      form.nome &&
        form.email &&
        form.telefone &&
        form.cep &&
        form.logradouro &&
        form.numero &&
        form.bairro &&
        form.cidade &&
        form.uf
    );
  }, [form]);

  const fetchClientes = async () => {
    try {
      const resp = await api.get<ClienteDTO[]>('/clientes');
      setClientes(resp.data);
    } catch (err) {
      console.error('Erro ao buscar clientes:', err);
      setError('Falha ao carregar clientes.');
    }
  };

  useEffect(() => {
    fetchClientes();
  }, []);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setForm(f => ({
      ...f,
      [name]:
        name === 'cep' ? mascaraCep(value)
        : name === 'telefone' ? mascaraTelefone(value)
        : value,
    }));
  };

  const buscarViaCep = async (cepMasc: string) => {
    const cep = cepMasc.replace(/\D/g, '');
    if (cep.length !== 8) return;

    setBuscandoCep(true);
    setError(null);
    try {
      // ViaCEP direto (sem passar pelo axios baseURL)
      const res = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
      if (!res.ok) throw new Error('Falha ao consultar CEP');
      const data = await res.json();

      if (data.erro) {
        setError('CEP não encontrado.');
        return;
      }

      setForm(f => ({
        ...f,
        logradouro: data.logradouro ?? f.logradouro ?? '',
        bairro: data.bairro ?? f.bairro ?? '',
        cidade: data.localidade ?? f.cidade ?? '',
        uf: data.uf ?? f.uf ?? '',
      }));
    } catch (e) {
      console.error(e);
      setError('Não foi possível buscar o CEP.');
    } finally {
      setBuscandoCep(false);
    }
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!podeSalvar) {
      setError('Preencha todos os campos obrigatórios.');
      return;
    }

    setLoading(true);
    setError(null);

    // monta payload compatível com o DTO
    const payload: ClienteDTO = {
      idCliente: form.idCliente ?? 0,
      nome: form.nome!.trim(),
      email: form.email!.trim(),
      telefone: form.telefone!.trim(),
      cep: (form.cep || '').trim(),
      logradouro: form.logradouro!.trim(),
      numero: form.numero!.trim(),
      complemento: (form.complemento ?? '').trim(),
      bairro: form.bairro!.trim(),
      cidade: form.cidade!.trim(),
      uf: form.uf!.trim(),
      // os seguintes são gerenciados no backend; não enviamos aqui:
      dataCadastro: undefined as any,
      origem: undefined as any,
    };

    try {
      if (editing && form.idCliente) {
        await api.put<ClienteDTO>(`/clientes/${form.idCliente}`, payload);
      } else {
        await api.post<ClienteDTO>('/clientes', payload);
      }
      setForm(defaultForm);
      setEditing(false);
      await fetchClientes();
    } catch (err: any) {
      console.error(err);
      setError(err?.response?.data?.message || 'Erro ao salvar cliente.');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (c: ClienteDTO) => {
    setForm({
      idCliente: c.idCliente,
      nome: c.nome,
      email: c.email,
      telefone: mascaraTelefone(c.telefone || ''),
      cep: mascaraCep(c.cep || ''),
      logradouro: c.logradouro || '',
      numero: c.numero || '',
      complemento: c.complemento || '',
      bairro: c.bairro || '',
      cidade: c.cidade || '',
      uf: c.uf || '',
    });
    setEditing(true);
    setError(null);
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir este cliente?')) return;
    try {
      await api.delete(`/clientes/${id}`);
      await fetchClientes();
    } catch (err) {
      console.error('Erro ao excluir cliente:', err);
      setError('Erro ao excluir cliente.');
    }
  };

  const handleCancel = () => {
    setForm(defaultForm);
    setEditing(false);
    setError(null);
  };

  return (
    <div className="p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-4">Clientes</h2>

      {error && <p className="text-red-600 mb-4">{error}</p>}

      <form
        onSubmit={handleSubmit}
        className="mb-6 grid grid-cols-1 md:grid-cols-4 gap-4"
      >
        {/* Coluna 1 */}
        <input
          name="nome"
          value={form.nome || ''}
          onChange={handleChange}
          required
          placeholder="Nome completo *"
          className="border rounded px-3 py-2 md:col-span-2"
        />
        <input
          name="email"
          type="email"
          value={form.email || ''}
          onChange={handleChange}
          required
          placeholder="E-mail *"
          className="border rounded px-3 py-2"
        />
        <input
          name="telefone"
          value={form.telefone || ''}
          onChange={handleChange}
          required
          placeholder="Telefone *"
          className="border rounded px-3 py-2"
        />

        {/* Endereço */}
        <input
          name="cep"
          value={form.cep || ''}
          onChange={handleChange}
          onBlur={() => form.cep && buscarViaCep(form.cep)}
          required
          placeholder="CEP *"
          className="border rounded px-3 py-2"
        />
        <input
          name="logradouro"
          value={form.logradouro || ''}
          onChange={handleChange}
          required
          placeholder="Logradouro *"
          className="border rounded px-3 py-2 md:col-span-2"
        />
        <input
          name="numero"
          value={form.numero || ''}
          onChange={handleChange}
          required
          placeholder="Número *"
          className="border rounded px-3 py-2"
        />
        <input
          name="complemento"
          value={form.complemento || ''}
          onChange={handleChange}
          placeholder="Complemento"
          className="border rounded px-3 py-2"
        />
        <input
          name="bairro"
          value={form.bairro || ''}
          onChange={handleChange}
          required
          placeholder="Bairro *"
          className="border rounded px-3 py-2"
        />
        <input
          name="cidade"
          value={form.cidade || ''}
          onChange={handleChange}
          required
          placeholder="Cidade *"
          className="border rounded px-3 py-2"
        />
        <select
          name="uf"
          value={form.uf || ''}
          onChange={handleChange}
          required
          className="border rounded px-3 py-2"
        >
          <option value="" disabled>
            UF *
          </option>
          {UF_LIST.map(uf => (
            <option key={uf} value={uf}>
              {uf}
            </option>
          ))}
        </select>

        {/* Ações */}
        <div className="md:col-span-4 flex gap-3">
          <button
            type="submit"
            disabled={loading || buscandoCep || !podeSalvar}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition disabled:opacity-50"
          >
            {loading
              ? editing
                ? 'Atualizando...'
                : 'Criando...'
              : editing
              ? 'Atualizar Cliente'
              : 'Criar Cliente'}
          </button>

          {editing && (
            <button
              type="button"
              onClick={handleCancel}
              className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 transition"
            >
              Cancelar
            </button>
          )}

          {buscandoCep && (
            <span className="self-center text-sm text-gray-500">
              Buscando CEP…
            </span>
          )}
        </div>
      </form>

      <div className="overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Nome</th>
              <th className="px-4 py-2 text-left">E-mail</th>
              <th className="px-4 py-2 text-left">Telefone</th>
              <th className="px-4 py-2 text-left">Endereço</th>
              <th className="px-4 py-2 text-left">Ações</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map(c => (
              <tr key={c.idCliente} className="border-t">
                <td className="px-4 py-2">{c.nome}</td>
                <td className="px-4 py-2">{c.email}</td>
                <td className="px-4 py-2">{mascaraTelefone(c.telefone || '')}</td>
                <td className="px-4 py-2">
                  {composeEndereco(c as PartialCliente)}
                </td>
                <td className="px-4 py-2 flex gap-2">
                  <button
                    onClick={() => handleEdit(c)}
                    className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition"
                  >
                    Editar
                  </button>
                  <button
                    onClick={() => c.idCliente && handleDelete(c.idCliente)}
                    className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
                  >
                    Excluir
                  </button>
                </td>
              </tr>
            ))}

            {clientes.length === 0 && (
              <tr>
                <td colSpan={5} className="p-4 text-center text-gray-500">
                  Nenhum cliente encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Clientes;
