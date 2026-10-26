import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeIpSubnetStatus, useDeleteIpSubnet, useIpSubnet } from './hooks';
import { IP_SUBNET_IP_VERSION_LABELS, IP_SUBNET_CRITICALITY_LABELS } from './types';

export function IpSubnetDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIpSubnet(id);
  const changeStatus = useChangeIpSubnetStatus();
  const remove = useDeleteIpSubnet();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Ip Subnet" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ip Subnet." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/ipam/ip-subnets'),
    });
  }

  return (
    <section aria-labelledby="ip-subnet-title">
      <PageHeader
        id="ip-subnet-title"
        title={item.name}
        subtitle={`Ip Subnet · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/ipam/ip-subnets/${item.id}/edit`}>
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
          <DetailRow label="Cidr">
            {item.cidr ?? '—'}
          </DetailRow>
          <DetailRow label="Ip Version">
            {item.ipVersion ? IP_SUBNET_IP_VERSION_LABELS[item.ipVersion] : '—'}
          </DetailRow>
          <DetailRow label="Vrf Id">
            <ShortId value={item.vrfId} full />
          </DetailRow>
          <DetailRow label="Gateway">
            {item.gateway ?? '—'}
          </DetailRow>
          <DetailRow label="Vlan Id">
            <ShortId value={item.vlanId} full />
          </DetailRow>
          <DetailRow label="Utilization Pct">
            {formatDecimal(item.utilizationPct)}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? IP_SUBNET_CRITICALITY_LABELS[item.criticality] : '—'}
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
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Ip Subnet"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
