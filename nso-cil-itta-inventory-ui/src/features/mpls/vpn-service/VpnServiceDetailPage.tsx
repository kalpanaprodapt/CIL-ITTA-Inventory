import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeVpnServiceStatus, useDeleteVpnService, useVpnService } from './hooks';
import { VPN_SERVICE_VPN_TYPE_LABELS, VPN_SERVICE_TOPOLOGY_LABELS, VPN_SERVICE_CRITICALITY_LABELS } from './types';

export function VpnServiceDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVpnService(id);
  const changeStatus = useChangeVpnServiceStatus();
  const remove = useDeleteVpnService();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Vpn Service" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vpn Service." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/mpls/vpn-services'),
    });
  }

  return (
    <section aria-labelledby="vpn-service-title">
      <PageHeader
        id="vpn-service-title"
        title={item.name}
        subtitle={`Vpn Service · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/mpls/vpn-services/${item.id}/edit`}>
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
          <DetailRow label="Vpn Name">
            {item.vpnName ?? '—'}
          </DetailRow>
          <DetailRow label="Vpn Type">
            {item.vpnType ? VPN_SERVICE_VPN_TYPE_LABELS[item.vpnType] : '—'}
          </DetailRow>
          <DetailRow label="Vrf Id">
            <ShortId value={item.vrfId} full />
          </DetailRow>
          <DetailRow label="Customer Id">
            <ShortId value={item.customerId} full />
          </DetailRow>
          <DetailRow label="Topology">
            {item.topology ? VPN_SERVICE_TOPOLOGY_LABELS[item.topology] : '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? VPN_SERVICE_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Vpn Service"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
