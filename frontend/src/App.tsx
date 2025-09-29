
// src/App.tsx

import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from '@/hooks/AuthContext';
import ProtectedRoute from '@/components/ProtectedRoute';
import Layout from '@/components/Layout';

import Home from '@/pages/Home/Home'
import AgendamentoPage from '@/pages/Agendamento/AgendamentoPage'
import StatusPage from '@/pages/Status/StatusPage'
import LoginPage from '@/pages/Login/LoginPage'

// ADMIN
import PainelAdmin    from '@/pages/PainelAdmin/PainelAdmin';
import Dashboard      from '@/pages/PainelAdmin/Dashboard';
import Tecnicos       from '@/pages/PainelAdmin/Tecnicos';
import Clientes       from '@/pages/PainelAdmin/Clientes';
import Servicos       from '@/pages/PainelAdmin/Servicos';
import Agendamentos   from '@/pages/PainelAdmin/Agendamentos';
import Financeiro     from '@/pages/PainelAdmin/Financeiro';
import Estoque        from '@/pages/PainelAdmin/Estoque';
import OrdensServico  from '@/pages/PainelAdmin/OrdensServico';
import OsTecnico      from '@/pages/PainelTecnico/OsTecnico';

// TÉCNICO
import PainelTecnico  from '@/pages/PainelTecnico/PainelTecnico';
import CompleteOsForm from '@/pages/PainelTecnico/CompleteOsForm';



export default function App() {
  return (
    <AuthProvider>
      <Routes>
      {/* ─── ROTAS PÚBLICAS ─────────────────────────────────────── */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/"      element={<Home />} />
          <Route path="/agendamentos" element={<AgendamentoPage />} />
          <Route path="/agendamentos/:id/status" element={<StatusPage />} />
// ROTAS TÉCNICO
<Route element={<ProtectedRoute allowedRoles={['TECNICO']} />}>
  <Route path="/painel-tecnico" element={<PainelTecnico />} />
  {/* ✅ lista de OS do técnico */}
  <Route path="/painel-tecnico/os" element={<OsTecnico />} />
  {/* ✅ formulário para uma OS específica */}
  <Route path="/painel-tecnico/os/:osId" element={<CompleteOsForm />} />
</Route>

        {/* tudo que vai ter sidebar/layout já envolto aqui */}
        <Route element={<Layout />}>
          
          {/* ROTAS ADMIN */}
          <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
            <Route path="/painel-admin" element={<Navigate to="/painel-admin/dashboard" replace />} />
            <Route path="/painel-admin/dashboard" element={<Dashboard />} />
            <Route path="/painel-admin/tecnicos" element={<Tecnicos />} />
            <Route path="/painel-admin/clientes" element={<Clientes />} />
            <Route path="/painel-admin/servicos" element={<Servicos />} />
            <Route path="/painel-admin/agendamentos" element={<Agendamentos />} />
            <Route path="/painel-admin/financeiro" element={<Financeiro />} />
            <Route path="/painel-admin/estoque" element={<Estoque />} />
            <Route path="/painel-admin/os" element={<OrdensServico />} />
          </Route>
        </Route>
        
      </Routes>
    </AuthProvider>
  );
}
