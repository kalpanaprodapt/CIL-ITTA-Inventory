import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeVirtualMachineStatus, useDeleteVirtualMachine, useVirtualMachine } from './hooks';
import { VIRTUAL_MACHINE_POWER_STATE_LABELS, VIRTUAL_MACHINE_CRITICALITY_LABELS } from './types';

export function VirtualMachineDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVirtualMachine(id);
  const changeStatus = useChangeVirtualMachineStatus();
  const remove = useDeleteVirtualMachine();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Virtual Machine" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Virtual Machine." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/virtual/virtual-machines'),
    });
  }

  return (
    <section aria-labelledby="virtual-machine-title">
      <PageHeader
        id="virtual-machine-title"
        title={item.name}
        subtitle={`Virtual Machine · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/virtual/virtual-machines/${item.id}/edit`}>
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
          <DetailRow label="Hypervisor Id">
            <ShortId value={item.hypervisorId} full />
          </DetailRow>
          <DetailRow label="Vm Name">
            {item.vmName ?? '—'}
          </DetailRow>
          <DetailRow label="Vcpu">
            {formatNumber(item.vcpu)}
          </DetailRow>
          <DetailRow label="Memory Gb">
            {formatNumber(item.memoryGb)}
          </DetailRow>
          <DetailRow label="Disk Gb">
            {formatNumber(item.diskGb)}
          </DetailRow>
          <DetailRow label="Power State">
            {item.powerState ? VIRTUAL_MACHINE_POWER_STATE_LABELS[item.powerState] : '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? VIRTUAL_MACHINE_CRITICALITY_LABELS[item.criticality] : '—'}
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
          <DetailRow label="Effective To">
            {formatDate(item.effectiveTo)}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Virtual Machine"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
