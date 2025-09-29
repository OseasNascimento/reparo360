// src/components/Sidebar.tsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import type { LucideIcon } from 'lucide-react';
import {
  Home,
  Users,
  UserCog,
  Wrench,
  Calendar,
  DollarSign,
  Box,
  FileText,
  Settings,
} from 'lucide-react';

type MenuItem = {
  name: string;
  to: string;
  icon: LucideIcon;
};

const menu: MenuItem[] = [
  { name: 'Dashboard',         to: '/painel-admin/dashboard',     icon: Home },
  { name: 'Clientes',          to: '/painel-admin/clientes',       icon: Users },
  { name: 'Técnicos',          to: '/painel-admin/tecnicos',       icon: UserCog },
  { name: 'Serviços',          to: '/painel-admin/servicos',       icon: Wrench },
  { name: 'Agendamentos',      to: '/painel-admin/agendamentos',   icon: Calendar },
  { name: 'Financeiro',        to: '/painel-admin/financeiro',     icon: DollarSign },
  { name: 'Estoque',           to: '/painel-admin/estoque',        icon: Box },
  { name: 'Ordens de Serviço', to: '/painel-admin/os',             icon: FileText },
  { name: 'Configurações',     to: '/painel-admin/config',         icon: Settings },
];

export default function Sidebar() {
  return (
    <aside className="fixed inset-y-0 left-0 w-64 bg-[#0E294B] text-white shadow-lg flex flex-col">
      {/* Logo (public/Logo.png) */}
      <div className="p-6 border-b border-[#0C253B] flex items-center justify-center">
        <img
          src="/Logo.png"
          alt="Reparo360"
          className="h-20 w-auto object-contain"
        />
      </div>

      <nav className="mt-4 flex-1 overflow-y-auto">
        {menu.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                [
                  'flex items-center gap-3 px-6 py-3 transition-colors',
                  'hover:bg-[#0C253B]/50',
                  isActive ? 'bg-[#0C253B] font-semibold' : '',
                ].join(' ')
              }
            >
              <Icon size={18} />
              <span>{item.name}</span>
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
}
