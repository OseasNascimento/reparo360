/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string
  readonly VITE_WHATSAPP_NUMBER: string
  // aqui você pode adicionar outras variáveis que começarem com VITE_
  
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
