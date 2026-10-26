import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeChangeRequestStatus, useDeleteChangeRequest, useChangeRequest } from './hooks';
import { CHANGE_REQUEST_CHANGE_TYPE_LABELS, CHANGE_REQUEST_RISK_LEVEL_LABELS, CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS } from './types';

export function ChangeRequestDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useChangeRequest(id);
  const changeStatus = useChangeChangeRequestStatus();
  const remove = useDeleteChangeRequest();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Change Request" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Change Request." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/change/change-requests'),
    });
  }

  return (
    <section aria-labelledby="change-request-title">
      <PageHeader
        id="change-request-title"
        title={item.name}
        subtitle={`Change Request · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/change/change-requests/${item.id}/edit`}>
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
          <DetailRow label="Change Number">
            {item.changeNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Change Type">
            {item.changeType ? CHANGE_REQUEST_CHANGE_TYPE_LABELS[item.changeType] : '—'}
          </DetailRow>
          <DetailRow label="Risk Level">
            {item.riskLevel ? CHANGE_REQUEST_RISK_LEVEL_LABELS[item.riskLevel] : '—'}
          </DetailRow>
          <DetailRow label="Planned Start">
            {formatDateTime(item.plannedStart)}
          </DetailRow>
          <DetailRow label="Planned End">
            {formatDateTime(item.plannedEnd)}
          </DetailRow>
          <DetailRow label="Notes">
            {item.notes ?? '—'}
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
            {item.lifecycleStage ? CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Change Request"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
