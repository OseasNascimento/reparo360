// src/pages/Login/LoginPage.tsx
import React, { FormEvent, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/useAuth'

const LoginPage: React.FC = () => {
  const { login } = useAuth()
  const navigate = useNavigate()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      const userRole = await login({ email, password })
      console.debug('[LoginPage] role retornada →', userRole)

      if (userRole === 'ADMIN') {
        navigate('/painel-admin')
      } else if (userRole === 'TECNICO') {
        navigate('/painel-tecnico')
      } else {
        setError('Perfil desconhecido. Contate o suporte.')
      }
    } catch (err: any) {
      setError(err.response?.data?.error || 'Falha no login')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form onSubmit={handleSubmit} className="w-full max-w-sm bg-white p-6 rounded shadow-lg">
        <h2 className="text-2xl font-bold mb-4 text-center">Entrar</h2>
        {error && <p className="text-red-600 mb-3">{error}</p>}
        <label className="block mb-2">
          <span className="text-gray-700">E-mail</span>
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
            className="mt-1 block w-full border rounded px-3 py-2"
          />
        </label>
        <label className="block mb-4">
          <span className="text-gray-700">Senha</span>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
            className="mt-1 block w-full border rounded px-3 py-2"
          />
        </label>
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          {loading ? 'Entrando...' : 'Entrar'}
        </button>
      </form>
    </div>
  )
}

export default LoginPage
