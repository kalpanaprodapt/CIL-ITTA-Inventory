import type { ReactNode } from 'react';
import { LIFECYCLE_LABELS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { shortId } from '@/shared/utils/format';

export function StatusBadge({ status }: { status: LifecycleStatus }) {
  return <span className={`badge badge--${status.toLowerCase()}`}>{LIFECYCLE_LABELS[status]}</span>;
}

export function BooleanCell({ value }: { value?: boolean }) {
  if (value === undefined) return <span>—</span>;
  return <span aria-label={value ? 'Yes' : 'No'}>{value ? '✓' : '✗'}</span>;
}

export function ShortId({ value, full = false }: { value?: string; full?: boolean }) {
  if (!value) return <span>—</span>;
  return (
    <code title={value} className="short-id">
      {full ? value : shortId(value)}
    </code>
  );
}

export function DetailRow({ label, children }: { label: string; children: ReactNode }) {
  return (
    <div className="detail-row">
      <dt>{label}</dt>
      <dd>{children}</dd>
    </div>
  );
}
