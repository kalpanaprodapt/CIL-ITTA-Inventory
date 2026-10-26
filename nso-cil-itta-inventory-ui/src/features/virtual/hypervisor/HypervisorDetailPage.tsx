import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeHypervisorStatus, useDeleteHypervisor, useHypervisor } from './hooks';
import { HYPERVISOR_HYPERVISOR_TYPE_LABELS, HYPERVISOR_CRITICALITY_LABELS } from './types';

export function HypervisorDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useHypervisor(id);
  const changeStatus = useChangeHypervisorStatus();
  const remove = useDeleteHypervisor();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Hypervisor" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Hypervisor." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/virtual/hypervisors'),
    });
  }

  return (
    <section aria-labelledby="hypervisor-title">
      <PageHeader
        id="hypervisor-title"
        title={item.name}
        subtitle={`Hypervisor · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/virtual/hypervisors/${item.id}/edit`}>
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
          <DetailRow label="Host Device Id">
            <ShortId value={item.hostDeviceId} full />
          </DetailRow>
          <DetailRow label="Hypervisor Type">
            {item.hypervisorType ? HYPERVISOR_HYPERVISOR_TYPE_LABELS[item.hypervisorType] : '—'}
          </DetailRow>
          <DetailRow label="Hv Version">
            {item.hvVersion ?? '—'}
          </DetailRow>
          <DetailRow label="Vcpu Capacity">
            {formatNumber(item.vcpuCapacity)}
          </DetailRow>
          <DetailRow label="Memory Gb Capacity">
            {formatNumber(item.memoryGbCapacity)}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? HYPERVISOR_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Cost Center">
            {item.costCenter ?? '—'}
          </DetailRow>
          <DetailRow label="Notes">
            {item.notes ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Hypervisor"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
