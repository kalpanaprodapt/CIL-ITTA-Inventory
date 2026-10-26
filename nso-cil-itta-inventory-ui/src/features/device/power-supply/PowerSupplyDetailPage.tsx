import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangePowerSupplyStatus, useDeletePowerSupply, usePowerSupply } from './hooks';
import { POWER_SUPPLY_CRITICALITY_LABELS } from './types';

export function PowerSupplyDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePowerSupply(id);
  const changeStatus = useChangePowerSupplyStatus();
  const remove = useDeletePowerSupply();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Power Supply" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Power Supply." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/device/power-supplies'),
    });
  }

  return (
    <section aria-labelledby="power-supply-title">
      <PageHeader
        id="power-supply-title"
        title={item.name}
        subtitle={`Power Supply · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/device/power-supplies/${item.id}/edit`}>
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
          <DetailRow label="Chassis Id">
            <ShortId value={item.chassisId} full />
          </DetailRow>
          <DetailRow label="Slot Number">
            {formatNumber(item.slotNumber)}
          </DetailRow>
          <DetailRow label="Wattage">
            {formatNumber(item.wattage)}
          </DetailRow>
          <DetailRow label="Feed Id">
            <ShortId value={item.feedId} full />
          </DetailRow>
          <DetailRow label="Serial Number">
            {item.serialNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Efficiency">
            {formatDecimal(item.efficiency)}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? POWER_SUPPLY_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Power Supply"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
