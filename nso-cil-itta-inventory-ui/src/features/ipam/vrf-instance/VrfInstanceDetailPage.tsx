import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeVrfInstanceStatus, useDeleteVrfInstance, useVrfInstance } from './hooks';
import { VRF_INSTANCE_LIFECYCLE_STAGE_LABELS } from './types';

export function VrfInstanceDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVrfInstance(id);
  const changeStatus = useChangeVrfInstanceStatus();
  const remove = useDeleteVrfInstance();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Vrf Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vrf Instance." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/ipam/vrf-instances'),
    });
  }

  return (
    <section aria-labelledby="vrf-instance-title">
      <PageHeader
        id="vrf-instance-title"
        title={item.name}
        subtitle={`Vrf Instance · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/ipam/vrf-instances/${item.id}/edit`}>
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
          <DetailRow label="Vrf Name">
            {item.vrfName ?? '—'}
          </DetailRow>
          <DetailRow label="Route Distinguisher">
            {item.routeDistinguisher ?? '—'}
          </DetailRow>
          <DetailRow label="Import Targets">
            {item.importTargets ?? '—'}
          </DetailRow>
          <DetailRow label="Export Targets">
            {item.exportTargets ?? '—'}
          </DetailRow>
          <DetailRow label="Device Id">
            <ShortId value={item.deviceId} full />
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? VRF_INSTANCE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Vrf Instance"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
