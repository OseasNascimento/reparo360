import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';

export default defineConfig({
  plugins: [
    react(),
  ],
  resolve: {
    alias: {
      '@': '/src'
    }
  },
  server: {
    port: 3000,
    proxy: {
      // todas as chamadas a /api/* serão encaminhadas para o seu backend Spring
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        // se o seu backend já espera /api no path, não precisa de rewrite; caso contrário:
        // rewrite: path => path.replace(/^\/api/, '')
      }
    }
  }
})
