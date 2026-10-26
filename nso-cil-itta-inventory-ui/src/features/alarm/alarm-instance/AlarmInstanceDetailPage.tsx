import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeAlarmInstanceStatus, useDeleteAlarmInstance, useAlarmInstance } from './hooks';
import { ALARM_INSTANCE_ALARM_STATE_LABELS, ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS } from './types';

export function AlarmInstanceDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAlarmInstance(id);
  const changeStatus = useChangeAlarmInstanceStatus();
  const remove = useDeleteAlarmInstance();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Alarm Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Alarm Instance." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/alarm/alarm-instances'),
    });
  }

  return (
    <section aria-labelledby="alarm-instance-title">
      <PageHeader
        id="alarm-instance-title"
        title={item.name}
        subtitle={`Alarm Instance · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/alarm/alarm-instances/${item.id}/edit`}>
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
          <DetailRow label="Definition Id">
            <ShortId value={item.definitionId} full />
          </DetailRow>
          <DetailRow label="Source Ref">
            {item.sourceRef ?? '—'}
          </DetailRow>
          <DetailRow label="Raised At">
            {formatDateTime(item.raisedAt)}
          </DetailRow>
          <DetailRow label="Cleared At">
            {formatDateTime(item.clearedAt)}
          </DetailRow>
          <DetailRow label="Acked By">
            {item.ackedBy ?? '—'}
          </DetailRow>
          <DetailRow label="Alarm State">
            {item.alarmState ? ALARM_INSTANCE_ALARM_STATE_LABELS[item.alarmState] : '—'}
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
            {item.lifecycleStage ? ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
        title="Delete Alarm Instance"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
