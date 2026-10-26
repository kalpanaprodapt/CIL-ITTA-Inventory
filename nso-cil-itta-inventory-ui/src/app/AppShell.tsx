import { useState } from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import { NAVIGATION } from '@/app/navigation';
import { useAuth } from '@/shared/auth/AuthProvider';

export function AppShell() {
  const { tenant, setTenant, signOut } = useAuth();
  const [open, setOpen] = useState<string | null>(null);

  return (
    <div className="shell">
      <header className="shell__top">
        <NavLink to="/" className="shell__brand">
          ITTA Inventory
        </NavLink>
        <label className="shell__tenant">
          Tenant
          <input value={tenant} onChange={(ev) => setTenant(ev.target.value)} aria-label="Tenant" />
        </label>
        <button type="button" onClick={signOut}>
          Sign out
        </button>
      </header>
      <nav className="shell__nav" aria-label="Inventory domains">
        {NAVIGATION.map((d) => (
          <section key={d.domain}>
            <button
              type="button"
              className="shell__domain"
              aria-expanded={open === d.domain}
              onClick={() => setOpen(open === d.domain ? null : d.domain)}
            >
              {d.title}
            </button>
            {open === d.domain ? (
              <ul>
                {d.entities.map((e) => (
                  <li key={e.name}>
                    <NavLink to={`/${d.domain}/${e.path}`}>{e.title}</NavLink>
                  </li>
                ))}
              </ul>
            ) : null}
          </section>
        ))}
      </nav>
      <main className="shell__main">
        <Outlet />
      </main>
    </div>
  );
}
