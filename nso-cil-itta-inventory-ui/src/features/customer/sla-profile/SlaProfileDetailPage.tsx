import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeSlaProfileStatus, useDeleteSlaProfile, useSlaProfile } from './hooks';
import { SLA_PROFILE_CRITICALITY_LABELS } from './types';

export function SlaProfileDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSlaProfile(id);
  const changeStatus = useChangeSlaProfileStatus();
  const remove = useDeleteSlaProfile();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Sla Profile" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sla Profile." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/customer/sla-profiles'),
    });
  }

  return (
    <section aria-labelledby="sla-profile-title">
      <PageHeader
        id="sla-profile-title"
        title={item.name}
        subtitle={`Sla Profile · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/customer/sla-profiles/${item.id}/edit`}>
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
          <DetailRow label="Profile Name">
            {item.profileName ?? '—'}
          </DetailRow>
          <DetailRow label="Availability Pct">
            {formatDecimal(item.availabilityPct)}
          </DetailRow>
          <DetailRow label="Mttr Minutes">
            {formatNumber(item.mttrMinutes)}
          </DetailRow>
          <DetailRow label="Latency Ms">
            {formatDecimal(item.latencyMs)}
          </DetailRow>
          <DetailRow label="Jitter Ms">
            {formatDecimal(item.jitterMs)}
          </DetailRow>
          <DetailRow label="Credit Policy">
            {item.creditPolicy ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? SLA_PROFILE_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Sla Profile"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
