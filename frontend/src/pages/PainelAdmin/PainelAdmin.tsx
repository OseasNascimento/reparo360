// src/pages/PainelAdmin/PainelAdmin.tsx
import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '@/components/Sidebar';

const PainelAdmin: React.FC = () => (
  <div className="flex flex-col md:flex-row">
    <Sidebar />
    <main className="flex-1 p-4 md:p-6 bg-gray-50 dark:bg-gray-800 min-h-screen">
      <Outlet />
    </main>
  </div>
);

export default PainelAdmin;
