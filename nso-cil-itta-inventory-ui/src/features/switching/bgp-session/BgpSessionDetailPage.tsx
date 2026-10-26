import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeBgpSessionStatus, useDeleteBgpSession, useBgpSession } from './hooks';
import { BGP_SESSION_SESSION_STATE_LABELS, BGP_SESSION_CRITICALITY_LABELS } from './types';

export function BgpSessionDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBgpSession(id);
  const changeStatus = useChangeBgpSessionStatus();
  const remove = useDeleteBgpSession();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Bgp Session" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Bgp Session." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/switching/bgp-sessions'),
    });
  }

  return (
    <section aria-labelledby="bgp-session-title">
      <PageHeader
        id="bgp-session-title"
        title={item.name}
        subtitle={`Bgp Session · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/switching/bgp-sessions/${item.id}/edit`}>
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
          <DetailRow label="Local Device Id">
            <ShortId value={item.localDeviceId} full />
          </DetailRow>
          <DetailRow label="Peer Address">
            {item.peerAddress ?? '—'}
          </DetailRow>
          <DetailRow label="Local Asn">
            {formatNumber(item.localAsn)}
          </DetailRow>
          <DetailRow label="Remote Asn">
            {formatNumber(item.remoteAsn)}
          </DetailRow>
          <DetailRow label="Session State">
            {item.sessionState ? BGP_SESSION_SESSION_STATE_LABELS[item.sessionState] : '—'}
          </DetailRow>
          <DetailRow label="Prefix Limit">
            {formatNumber(item.prefixLimit)}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? BGP_SESSION_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Bgp Session"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
