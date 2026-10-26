import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangePowerFeedStatus, useDeletePowerFeed, usePowerFeed } from './hooks';
import { POWER_FEED_FEED_TYPE_LABELS, POWER_FEED_CRITICALITY_LABELS } from './types';

export function PowerFeedDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePowerFeed(id);
  const changeStatus = useChangePowerFeedStatus();
  const remove = useDeletePowerFeed();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Power Feed" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Power Feed." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/facility/power-feeds'),
    });
  }

  return (
    <section aria-labelledby="power-feed-title">
      <PageHeader
        id="power-feed-title"
        title={item.name}
        subtitle={`Power Feed · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/facility/power-feeds/${item.id}/edit`}>
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
          <DetailRow label="Room Id">
            <ShortId value={item.roomId} full />
          </DetailRow>
          <DetailRow label="Feed Type">
            {item.feedType ? POWER_FEED_FEED_TYPE_LABELS[item.feedType] : '—'}
          </DetailRow>
          <DetailRow label="Voltage">
            {formatDecimal(item.voltage)}
          </DetailRow>
          <DetailRow label="Amperage">
            {formatDecimal(item.amperage)}
          </DetailRow>
          <DetailRow label="Phase">
            {item.phase ?? '—'}
          </DetailRow>
          <DetailRow label="Breaker Label">
            {item.breakerLabel ?? '—'}
          </DetailRow>
          <DetailRow label="Redundancy Group">
            {item.redundancyGroup ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? POWER_FEED_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Power Feed"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
