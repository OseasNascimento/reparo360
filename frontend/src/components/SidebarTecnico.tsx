// src/components/SidebarTecnico.tsx
import { NavLink } from "react-router-dom";
import { CalendarDays, FileText } from "lucide-react";

export default function SidebarTecnico() {
  return (
    <aside className="fixed left-0 top-0 h-screen w-64 bg-[#0E294B] text-white shadow-lg z-40">
      <div className="p-6 border-b border-[#0C253B] flex items-center justify-center">
        <img src="/Logo.png" alt="Reparo360" className="h-20 w-auto object-contain" />
      </div>

      <nav className="mt-3 space-y-1">
        <NavLink
          to="/painel-tecnico"
          className={({ isActive }) =>
            `flex items-center gap-3 px-5 py-3 rounded ${
              isActive ? "bg-[#0C253B] font-semibold" : "hover:bg-[#0C253B]/50"
            }`
          }
        >
          <CalendarDays size={18} /> Agendamentos
        </NavLink>

        {/* Lista de OS do técnico */}
        <NavLink
          to="/painel-tecnico/os"
          className={({ isActive }) =>
            `flex items-center gap-3 px-5 py-3 rounded ${
              isActive ? "bg-[#0C253B] font-semibold" : "hover:bg-[#0C253B]/50"
            }`
          }
        >
          <FileText size={18} /> Ordens de Serviço
        </NavLink>
      </nav>
    </aside>
  );
}
