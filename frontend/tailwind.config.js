// tailwind.config.js
/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: ["./index.html", "./src/**/*.{ts,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'ui-sans-serif', 'system-ui'],
      },
      colors: {               // <— aqui
        primary: '#4F46E5',
        success: '#10B981',
        warning: '#F59E0B',
        danger:  '#EF4444',
      }
    },
  },
  plugins: [],
}
