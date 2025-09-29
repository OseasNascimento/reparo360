// src/hooks/AuthContext.tsx
import React, {
  createContext,
  ReactNode,
  useState,
  useEffect
} from 'react'
import api from '@/services/api'
import {
  login as loginService,
  LoginCredentials,
  AuthData
} from '@/services/authService'

interface TecnicoDTO { nome: string }

export interface AuthContextType {
  token: string | null
  role: string | null
  userId: number | null
  userName: string | null
  isAuthenticated: boolean
  login: (creds: LoginCredentials) => Promise<string | null>
  logout: () => void
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
)

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children
}) => {
  const [token, setToken] = useState<string | null>(() =>
    localStorage.getItem('token')
  )
  const [role, setRole] = useState<string | null>(() =>
    localStorage.getItem('role')
  )
  const [userId, setUserId] = useState<number | null>(() => {
    const v = localStorage.getItem('tecnicoId')
    return v ? Number(v) : null
  })
  const [userName, setUserName] = useState<string | null>(() =>
    localStorage.getItem('userName')
  )

  useEffect(() => {
    if (token) api.defaults.headers.common['Authorization'] = `Bearer ${token}`
    else delete api.defaults.headers.common['Authorization']
  }, [token])

  const login = async (
    creds: LoginCredentials
  ): Promise<string | null> => {
    const data: AuthData = await loginService(creds)

    setToken(data.token)
    localStorage.setItem('token', data.token)

    setUserId(data.tecnicoId)
    localStorage.setItem('tecnicoId', String(data.tecnicoId))

    setRole(data.role)
    localStorage.setItem('role', data.role)

    // busca o nome do técnico
    const resp = await api.get<TecnicoDTO>(
      `/tecnicos/${data.tecnicoId}`
    )
    setUserName(resp.data.nome)
    localStorage.setItem('userName', resp.data.nome)

    return data.role
  }

  const logout = () => {
    setToken(null)
    setRole(null)
    setUserId(null)
    setUserName(null)
    localStorage.clear()
  }

  return (
    <AuthContext.Provider
      value={{
        token,
        role,
        userId,
        userName,
        isAuthenticated: !!token,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}
