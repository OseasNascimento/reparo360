import React, { useEffect, useState, FormEvent } from 'react'
import api from '@/services/api'
import type { TecnicoDTO, RoleDTO } from '@/api'

const Tecnicos: React.FC = () => {
  const [tecnicos, setTecnicos] = useState<TecnicoDTO[]>([])
  const [rolesList, setRolesList] = useState<RoleDTO[]>([])
  const [form, setForm] = useState<Partial<TecnicoDTO>>({
    nome: '',
    email: '',
    telefone: '',
    especialidade: '',
    dataContratacao: '',
    senha: '',
    roles: []                         // <-- agora é array
  })
  const [editing, setEditing] = useState(false)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  // 1) Carrega técnicos e roles
  const fetchData = async () => {
    setLoading(true)
    try {
      const [tecResp, roleResp] = await Promise.all([
        api.get<TecnicoDTO[]>('/tecnicos'),
        api.get<RoleDTO[]>('/roles')
      ])
      setTecnicos(tecResp.data)
      setRolesList(roleResp.data)
      setError(null)
    } catch (err) {
      console.error(err)
      setError('Falha ao carregar dados.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchData() }, [])

  // 2) Atualiza form (inputs e select multiple)
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target

    // select múltiplo de roles
    if (name === 'roles' && e.target instanceof HTMLSelectElement && e.target.multiple) {
      const selected = Array.from(e.target.selectedOptions)
        .map(opt => rolesList.find(r => r.idRole === Number(opt.value))!)
        .filter(Boolean) as RoleDTO[]
      setForm(f => ({ ...f, roles: selected }))
      return
    }

    // outros inputs
    const fieldValue = e.target.type === 'number'
      ? Number(value)
      : value

    setForm(f => ({ ...f, [name]: fieldValue }))
  }

  // 3) Submete criação/edição
  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    setLoading(true)
    try {
      const payload = { ...(form as TecnicoDTO), roles: form.roles || [] }

      if (editing && form.id) {
        await api.put(`/tecnicos/${form.id}`, payload)
      } else {
        await api.post('/tecnicos', payload)
      }
      setEditing(false)
      setForm({ nome: '', email: '', telefone: '', especialidade: '', dataContratacao: '', senha: '', roles: [] })
      await fetchData()
    } catch (err: any) {
      console.error(err)
      setError(err.response?.data?.message || 'Erro ao salvar técnico.')
    } finally {
      setLoading(false)
    }
  }

  // 4) Preenche form para edição
  const handleEdit = (t: TecnicoDTO) => {
    setForm({
      id: t.id,
      nome: t.nome,
      email: t.email,
      telefone: t.telefone,
      especialidade: t.especialidade,
      dataContratacao: t.dataContratacao.slice(0,16),
      senha: '',
      roles: t.roles || []
    })
    setEditing(true)
  }

  // 5) Exclui técnico
  const handleDelete = async (id: number) => {
    if (!confirm('Deseja realmente excluir?')) return
    setLoading(true)
    try {
      await api.delete(`/tecnicos/${id}`)
      await fetchData()
    } catch {
      setError('Erro ao excluir técnico.')
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <p className="text-center mt-8">Carregando técnicos…</p>
  if (error)   return <p className="text-center mt-8 text-red-600">{error}</p>

  return (
    <div className="p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-bold mb-4">Técnicos</h2>
      <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
        <input
          name="nome" value={form.nome||''} onChange={handleChange}
          placeholder="Nome completo" required
          className="border rounded px-3 py-2"
        />
        <input
          name="email" type="email" value={form.email||''} onChange={handleChange}
          placeholder="E-mail" required
          className="border rounded px-3 py-2"
        />
        <input
          name="telefone" value={form.telefone||''} onChange={handleChange}
          placeholder="Telefone" required
          className="border rounded px-3 py-2"
        />
        <input
          name="especialidade" value={form.especialidade||''} onChange={handleChange}
          placeholder="Especialidade" required
          className="border rounded px-3 py-2"
        />
        <input
          name="dataContratacao" type="datetime-local" value={form.dataContratacao||''}
          onChange={handleChange} required
          className="border rounded px-3 py-2"
        />
        <input
          name="senha" type="password" value={form.senha||''} onChange={handleChange}
          placeholder={editing?'Deixe em branco para não alterar':'Senha'} required={!editing}
          className="border rounded px-3 py-2"
        />

        <label className="col-span-full block">
          Roles:
          <select
            name="roles" multiple
            value={(form.roles||[]).map(r=>String(r.idRole))}
            onChange={handleChange}
            className="mt-1 w-full border rounded px-3 py-2 h-32"
          >
            {rolesList.map(role=>(
              <option key={role.idRole} value={role.idRole}>
                {role.nomeRole}
              </option>
            ))}
          </select>
        </label>

        <button
          type="submit"
          disabled={loading}
          className="col-span-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
        >
          {editing ? 'Atualizar Técnico' : 'Criar Técnico'}
        </button>
      </form>

      <div className="overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-4 py-2 text-left">Nome</th>
              <th className="px-4 py-2 text-left">E-mail</th>
              <th className="px-4 py-2 text-left">Telefone</th>
              <th className="px-4 py-2 text-left">Especialidade</th>
              <th className="px-4 py-2 text-left">Ações</th>
            </tr>
          </thead>
          <tbody>
            {tecnicos.map(t=>(
              <tr key={t.id} className="border-t">
                <td className="px-4 py-2">{t.nome}</td>
                <td className="px-4 py-2">{t.email}</td>
                <td className="px-4 py-2">{t.telefone}</td>
                <td className="px-4 py-2">{t.especialidade}</td>
                <td className="px-4 py-2 flex space-x-2">
                  <button onClick={()=>handleEdit(t)}
                          className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600">
                    Editar
                  </button>
                  <button onClick={()=>handleDelete(t.id!)}
                          className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600">
                    Excluir
                  </button>
                </td>
              </tr>
            ))}
            {tecnicos.length===0 && (
              <tr>
                <td colSpan={5} className="p-4 text-center text-gray-500">
                  Nenhum técnico encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default Tecnicos
