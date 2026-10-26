import { createContext, useContext, useMemo, useState } from 'react';
import type { ReactNode } from 'react';
import { getAccessToken, setAccessToken } from './token';

interface AuthState {
  token: string | null;
  tenant: string;
  signIn: (token: string) => void;
  signOut: () => void;
  setTenant: (tenant: string) => void;
}

const AuthContext = createContext<AuthState | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(getAccessToken());
  const [tenant, setTenantState] = useState<string>(sessionStorage.getItem('itta.tenant') ?? 'uniti');

  const value = useMemo<AuthState>(
    () => ({
      token,
      tenant,
      signIn: (t) => {
        setAccessToken(t);
        setToken(t);
      },
      signOut: () => {
        setAccessToken(null);
        setToken(null);
      },
      setTenant: (t) => {
        sessionStorage.setItem('itta.tenant', t);
        setTenantState(t);
      },
    }),
    [token, tenant],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthState {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
}
