import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '@/hooks/useAuth';

interface ProtectedRouteProps {
  allowedRoles?: string[];
}

/**
 * Se o usuário não estiver autenticado, redireciona para /login.
 * Se houver allowedRoles e o role for nulo OU não estiver na lista, redireciona para home (/).
 * Caso contrário, renderiza as rotas-filhas via <Outlet />.
 */
const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
  const { isAuthenticated, role } = useAuth();

  // 1) não logado? vai pro login
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // 2) se definiu allowedRoles E (não tem role OU role não está na lista) → ban
  if (
    allowedRoles &&
    ( !role || !allowedRoles.includes(role) )
  ) {
    return <Navigate to="/" replace />;
  }

  // 3) tudo certo, renderiza rotas-filhas
  return <Outlet />;
};

export default ProtectedRoute;
