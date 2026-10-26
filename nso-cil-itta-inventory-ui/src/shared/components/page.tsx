import type { ReactNode } from 'react';
import { Button } from './Button';

export function PageHeader(props: { id?: string; title: string; subtitle?: string; actions?: ReactNode }) {
  const { id, title, subtitle, actions } = props;
  return (
    <header className="page-header">
      <div>
        <h1 id={id}>{title}</h1>
        {subtitle ? <p className="page-header__subtitle">{subtitle}</p> : null}
      </div>
      {actions ? <div className="page-header__actions">{actions}</div> : null}
    </header>
  );
}

export function Spinner({ label = 'Loading' }: { label?: string }) {
  return (
    <div role="status" className="spinner">
      {label}…
    </div>
  );
}

export function ErrorBanner({ message, onRetry }: { message: string; onRetry?: () => void }) {
  return (
    <div role="alert" className="error-banner">
      <span>{message}</span>
      {onRetry ? (
        <Button variant="secondary" onClick={onRetry}>
          Retry
        </Button>
      ) : null}
    </div>
  );
}

export function EmptyState({ title, hint }: { title: string; hint?: string }) {
  return (
    <div className="empty-state">
      <h2>{title}</h2>
      {hint ? <p>{hint}</p> : null}
    </div>
  );
}
