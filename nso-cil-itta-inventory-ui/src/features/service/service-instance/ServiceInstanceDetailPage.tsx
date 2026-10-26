import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeServiceInstanceStatus, useDeleteServiceInstance, useServiceInstance } from './hooks';
import { SERVICE_INSTANCE_SERVICE_STATE_LABELS, SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS, SERVICE_INSTANCE_CRITICALITY_LABELS } from './types';

export function ServiceInstanceDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceInstance(id);
  const changeStatus = useChangeServiceInstanceStatus();
  const remove = useDeleteServiceInstance();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Service Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Instance." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/service/service-instances'),
    });
  }

  return (
    <section aria-labelledby="service-instance-title">
      <PageHeader
        id="service-instance-title"
        title={item.name}
        subtitle={`Service Instance · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/service/service-instances/${item.id}/edit`}>
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
          <DetailRow label="Catalog Item Id">
            <ShortId value={item.catalogItemId} full />
          </DetailRow>
          <DetailRow label="Customer Id">
            <ShortId value={item.customerId} full />
          </DetailRow>
          <DetailRow label="Activated On">
            {formatDate(item.activatedOn)}
          </DetailRow>
          <DetailRow label="Bandwidth Mbps">
            {formatNumber(item.bandwidthMbps)}
          </DetailRow>
          <DetailRow label="Service State">
            {item.serviceState ? SERVICE_INSTANCE_SERVICE_STATE_LABELS[item.serviceState] : '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
            {item.criticality ? SERVICE_INSTANCE_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Service Instance"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
