import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeTunnelEndpointStatus, useDeleteTunnelEndpoint, useTunnelEndpoint } from './hooks';
import { TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS, TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS } from './types';

export function TunnelEndpointDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTunnelEndpoint(id);
  const changeStatus = useChangeTunnelEndpointStatus();
  const remove = useDeleteTunnelEndpoint();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Tunnel Endpoint" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Tunnel Endpoint." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/mpls/tunnel-endpoints'),
    });
  }

  return (
    <section aria-labelledby="tunnel-endpoint-title">
      <PageHeader
        id="tunnel-endpoint-title"
        title={item.name}
        subtitle={`Tunnel Endpoint · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/mpls/tunnel-endpoints/${item.id}/edit`}>
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
          <DetailRow label="Device Id">
            <ShortId value={item.deviceId} full />
          </DetailRow>
          <DetailRow label="Tunnel Name">
            {item.tunnelName ?? '—'}
          </DetailRow>
          <DetailRow label="Tunnel Type">
            {item.tunnelType ? TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS[item.tunnelType] : '—'}
          </DetailRow>
          <DetailRow label="Source Ip">
            {item.sourceIp ?? '—'}
          </DetailRow>
          <DetailRow label="Destination Ip">
            {item.destinationIp ?? '—'}
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
            {item.lifecycleStage ? TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
        title="Delete Tunnel Endpoint"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
