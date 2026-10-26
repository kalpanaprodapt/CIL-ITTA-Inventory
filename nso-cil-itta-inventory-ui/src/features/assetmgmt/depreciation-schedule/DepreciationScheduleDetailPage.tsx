import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeDepreciationScheduleStatus, useDeleteDepreciationSchedule, useDepreciationSchedule } from './hooks';
import { DEPRECIATION_SCHEDULE_METHOD_LABELS, DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS } from './types';

export function DepreciationScheduleDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDepreciationSchedule(id);
  const changeStatus = useChangeDepreciationScheduleStatus();
  const remove = useDeleteDepreciationSchedule();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Depreciation Schedule" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Depreciation Schedule." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/assetmgmt/depreciation-schedules'),
    });
  }

  return (
    <section aria-labelledby="depreciation-schedule-title">
      <PageHeader
        id="depreciation-schedule-title"
        title={item.name}
        subtitle={`Depreciation Schedule · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/assetmgmt/depreciation-schedules/${item.id}/edit`}>
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
          <DetailRow label="Asset Id">
            <ShortId value={item.assetId} full />
          </DetailRow>
          <DetailRow label="Method">
            {item.method ? DEPRECIATION_SCHEDULE_METHOD_LABELS[item.method] : '—'}
          </DetailRow>
          <DetailRow label="Useful Life Months">
            {formatNumber(item.usefulLifeMonths)}
          </DetailRow>
          <DetailRow label="Salvage Value">
            {formatDecimal(item.salvageValue)}
          </DetailRow>
          <DetailRow label="Start Date">
            {formatDate(item.startDate)}
          </DetailRow>
          <DetailRow label="Effective From">
            {formatDate(item.effectiveFrom)}
          </DetailRow>
          <DetailRow label="Effective To">
            {formatDate(item.effectiveTo)}
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Depreciation Schedule"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
