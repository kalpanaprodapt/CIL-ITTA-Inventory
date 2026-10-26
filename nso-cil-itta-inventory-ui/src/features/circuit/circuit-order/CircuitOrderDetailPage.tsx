import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeCircuitOrderStatus, useDeleteCircuitOrder, useCircuitOrder } from './hooks';
import { CIRCUIT_ORDER_ORDER_STATUS_LABELS, CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS } from './types';

export function CircuitOrderDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuitOrder(id);
  const changeStatus = useChangeCircuitOrderStatus();
  const remove = useDeleteCircuitOrder();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Circuit Order" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit Order." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/circuit/circuit-orders'),
    });
  }

  return (
    <section aria-labelledby="circuit-order-title">
      <PageHeader
        id="circuit-order-title"
        title={item.name}
        subtitle={`Circuit Order · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/circuit/circuit-orders/${item.id}/edit`}>
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
          <DetailRow label="Circuit Id">
            <ShortId value={item.circuitId} full />
          </DetailRow>
          <DetailRow label="Order Number">
            {item.orderNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Ordered On">
            {formatDate(item.orderedOn)}
          </DetailRow>
          <DetailRow label="Requested Date">
            {formatDate(item.requestedDate)}
          </DetailRow>
          <DetailRow label="Committed Date">
            {formatDate(item.committedDate)}
          </DetailRow>
          <DetailRow label="Order Status">
            {item.orderStatus ? CIRCUIT_ORDER_ORDER_STATUS_LABELS[item.orderStatus] : '—'}
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
            {item.lifecycleStage ? CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
        title="Delete Circuit Order"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
