import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeBuildingStatus, useDeleteBuilding, useBuilding } from './hooks';
import { BUILDING_OWNERSHIP_LABELS, BUILDING_LIFECYCLE_STAGE_LABELS, BUILDING_CRITICALITY_LABELS } from './types';

export function BuildingDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBuilding(id);
  const changeStatus = useChangeBuildingStatus();
  const remove = useDeleteBuilding();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Building" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Building." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/geography/buildings'),
    });
  }

  return (
    <section aria-labelledby="building-title">
      <PageHeader
        id="building-title"
        title={item.name}
        subtitle={`Building · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/geography/buildings/${item.id}/edit`}>
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
          <DetailRow label="Site Id">
            <ShortId value={item.siteId} full />
          </DetailRow>
          <DetailRow label="Floors">
            {formatNumber(item.floors)}
          </DetailRow>
          <DetailRow label="Year Built">
            {formatNumber(item.yearBuilt)}
          </DetailRow>
          <DetailRow label="Ownership">
            {item.ownership ? BUILDING_OWNERSHIP_LABELS[item.ownership] : '—'}
          </DetailRow>
          <DetailRow label="Lease Expiry">
            {formatDate(item.leaseExpiry)}
          </DetailRow>
          <DetailRow label="Security Level">
            {formatNumber(item.securityLevel)}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? BUILDING_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
          <DetailRow label="Criticality">
            {item.criticality ? BUILDING_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Building"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
