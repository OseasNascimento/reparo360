// src/services/api.ts
import axios from 'axios';

const api = axios.create({
  // Em dev, use o proxy do Vite ("/api"); em prod, defina VITE_API_BASE_URL.
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
});

// Request: injeta o Bearer token em todas as chamadas
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    // garante objeto de headers
    config.headers = config.headers ?? {};
    (config.headers as any).Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response: trata sessão expirada/sem permissão globalmente
api.interceptors.response.use(
  (res) => res,
  (err) => {
    const status = err?.response?.status;
    if (status === 401 || status === 403) {
      try {
        localStorage.removeItem('token');
      } catch {}
      // evita loop caso já esteja na página de login
      if (!window.location.pathname.startsWith('/login')) {
        const returnTo = encodeURIComponent(
          window.location.pathname + window.location.search
        );
        window.location.href = `/login?expired=1&returnTo=${returnTo}`;
      }
    }
    return Promise.reject(err);
  }
);

export default api;
