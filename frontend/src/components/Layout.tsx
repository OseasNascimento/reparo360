// src/components/Layout.tsx
import React from 'react'
import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar'

const Layout: React.FC = () => (
  <div className="flex min-h-screen">
    <Sidebar />
    {/* ml-64 = mesma largura do sidebar */}
    <main className="ml-64 flex-1 p-6 bg-gray-100 overflow-auto">
      <Outlet />
    </main>
  </div>
)

export default Layout
