import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeIpAddressStatus, useDeleteIpAddress, useIpAddress } from './hooks';
import { IP_ADDRESS_ASSIGNMENT_TYPE_LABELS } from './types';

export function IpAddressDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIpAddress(id);
  const changeStatus = useChangeIpAddressStatus();
  const remove = useDeleteIpAddress();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Ip Address" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ip Address." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/ipam/ip-addresses'),
    });
  }

  return (
    <section aria-labelledby="ip-address-title">
      <PageHeader
        id="ip-address-title"
        title={item.name}
        subtitle={`Ip Address · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/ipam/ip-addresses/${item.id}/edit`}>
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
          <DetailRow label="Subnet Id">
            <ShortId value={item.subnetId} full />
          </DetailRow>
          <DetailRow label="Address">
            {item.address ?? '—'}
          </DetailRow>
          <DetailRow label="Assigned To Id">
            <ShortId value={item.assignedToId} full />
          </DetailRow>
          <DetailRow label="Assignment Type">
            {item.assignmentType ? IP_ADDRESS_ASSIGNMENT_TYPE_LABELS[item.assignmentType] : '—'}
          </DetailRow>
          <DetailRow label="Dns Name">
            {item.dnsName ?? '—'}
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
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Ip Address"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
