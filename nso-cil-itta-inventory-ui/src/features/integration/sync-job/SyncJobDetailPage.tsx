import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeSyncJobStatus, useDeleteSyncJob, useSyncJob } from './hooks';
import { SYNC_JOB_DIRECTION_LABELS, SYNC_JOB_LAST_STATUS_LABELS, SYNC_JOB_CRITICALITY_LABELS } from './types';

export function SyncJobDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSyncJob(id);
  const changeStatus = useChangeSyncJobStatus();
  const remove = useDeleteSyncJob();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Sync Job" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sync Job." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/integration/sync-jobs'),
    });
  }

  return (
    <section aria-labelledby="sync-job-title">
      <PageHeader
        id="sync-job-title"
        title={item.name}
        subtitle={`Sync Job · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/integration/sync-jobs/${item.id}/edit`}>
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
          <DetailRow label="Endpoint Id">
            <ShortId value={item.endpointId} full />
          </DetailRow>
          <DetailRow label="Direction">
            {item.direction ? SYNC_JOB_DIRECTION_LABELS[item.direction] : '—'}
          </DetailRow>
          <DetailRow label="Schedule Cron">
            {item.scheduleCron ?? '—'}
          </DetailRow>
          <DetailRow label="Last Run At">
            {formatDateTime(item.lastRunAt)}
          </DetailRow>
          <DetailRow label="Last Status">
            {item.lastStatus ? SYNC_JOB_LAST_STATUS_LABELS[item.lastStatus] : '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? SYNC_JOB_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Cost Center">
            {item.costCenter ?? '—'}
          </DetailRow>
          <DetailRow label="Notes">
            {item.notes ?? '—'}
          </DetailRow>
          <DetailRow label="Effective From">
            {formatDate(item.effectiveFrom)}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Sync Job"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
