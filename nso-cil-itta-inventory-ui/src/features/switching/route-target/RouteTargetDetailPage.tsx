import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeRouteTargetStatus, useDeleteRouteTarget, useRouteTarget } from './hooks';
import { ROUTE_TARGET_RT_TYPE_LABELS, ROUTE_TARGET_LIFECYCLE_STAGE_LABELS, ROUTE_TARGET_CRITICALITY_LABELS } from './types';

export function RouteTargetDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRouteTarget(id);
  const changeStatus = useChangeRouteTargetStatus();
  const remove = useDeleteRouteTarget();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Route Target" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Route Target." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/switching/route-targets'),
    });
  }

  return (
    <section aria-labelledby="route-target-title">
      <PageHeader
        id="route-target-title"
        title={item.name}
        subtitle={`Route Target · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/switching/route-targets/${item.id}/edit`}>
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
          <DetailRow label="Asn">
            {formatNumber(item.asn)}
          </DetailRow>
          <DetailRow label="Assigned Number">
            {formatNumber(item.assignedNumber)}
          </DetailRow>
          <DetailRow label="Rt Type">
            {item.rtType ? ROUTE_TARGET_RT_TYPE_LABELS[item.rtType] : '—'}
          </DetailRow>
          <DetailRow label="Vrf Id">
            <ShortId value={item.vrfId} full />
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? ROUTE_TARGET_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? ROUTE_TARGET_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Route Target"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
