import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeMaintenanceWindowStatus, useDeleteMaintenanceWindow, useMaintenanceWindow } from './hooks';
import { MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS, MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS } from './types';

export function MaintenanceWindowDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useMaintenanceWindow(id);
  const changeStatus = useChangeMaintenanceWindowStatus();
  const remove = useDeleteMaintenanceWindow();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Maintenance Window" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Maintenance Window." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/change/maintenance-windows'),
    });
  }

  return (
    <section aria-labelledby="maintenance-window-title">
      <PageHeader
        id="maintenance-window-title"
        title={item.name}
        subtitle={`Maintenance Window · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/change/maintenance-windows/${item.id}/edit`}>
              Edit
            </Link>
            <Button variant="danger" onClick={() => setConfirmDelete(true)}>
              Delete
            </Button>
          </>
        }
      />
      <div className="status-actions" role="group" aria-label="Change status">
        {LIFECYCLE_STATUSES.map((s) => (
          <Button
            key={s}
            variant={item.status === s ? 'primary' : 'ghost'}
            disabled={item.status === s || changeStatus.isPending}
            onClick={() => onStatus(s)}
          >
            {s}
          </Button>
        ))}
      </div>
      <dl className="detail-grid">
          <DetailRow label="Code">
            {item.code ?? '—'}
          </DetailRow>
          <DetailRow label="Name">
            {item.name ?? '—'}
          </DetailRow>
          <DetailRow label="Description">
            {item.description ?? '—'}
          </DetailRow>
          <DetailRow label="Status">
            <StatusBadge status={item.status} />
          </DetailRow>
          <DetailRow label="External Ref">
            {item.externalRef ?? '—'}
          </DetailRow>
          <DetailRow label="Window Name">
            {item.windowName ?? '—'}
          </DetailRow>
          <DetailRow label="Starts At">
            {formatDateTime(item.startsAt)}
          </DetailRow>
          <DetailRow label="Ends At">
            {formatDateTime(item.endsAt)}
          </DetailRow>
          <DetailRow label="Scope">
            {item.scope ?? '—'}
          </DetailRow>
          <DetailRow label="Impact Level">
            {item.impactLevel ? MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS[item.impactLevel] : '—'}
          </DetailRow>
          <DetailRow label="Effective To">
            {formatDate(item.effectiveTo)}
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Maintenance Window"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
