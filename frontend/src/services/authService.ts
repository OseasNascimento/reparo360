// src/services/authService.ts
import api from '@/services/api'

export interface LoginCredentials {
  email: string
  password: string
}

export interface AuthData {
  token: string
  tecnicoId: number
  role: string
}

export async function login(creds: LoginCredentials): Promise<AuthData> {
  // POST /api/auth/login retorna { token, tecnicoId, role }
  const resp = await api.post<AuthData>('/auth/login', creds)
  return resp.data
}
