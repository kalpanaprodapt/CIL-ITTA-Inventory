import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeNetworkDeviceStatus, useDeleteNetworkDevice, useNetworkDevice } from './hooks';
import { NETWORK_DEVICE_DEVICE_ROLE_LABELS, NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS } from './types';

export function NetworkDeviceDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useNetworkDevice(id);
  const changeStatus = useChangeNetworkDeviceStatus();
  const remove = useDeleteNetworkDevice();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Network Device" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Network Device." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/device/network-devices'),
    });
  }

  return (
    <section aria-labelledby="network-device-title">
      <PageHeader
        id="network-device-title"
        title={item.name}
        subtitle={`Network Device · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/device/network-devices/${item.id}/edit`}>
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
          <DetailRow label="Hostname">
            {item.hostname ?? '—'}
          </DetailRow>
          <DetailRow label="Device Role">
            {item.deviceRole ? NETWORK_DEVICE_DEVICE_ROLE_LABELS[item.deviceRole] : '—'}
          </DetailRow>
          <DetailRow label="Mgmt Ip">
            {item.mgmtIp ?? '—'}
          </DetailRow>
          <DetailRow label="Serial Number">
            {item.serialNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Model Id">
            <ShortId value={item.modelId} full />
          </DetailRow>
          <DetailRow label="Rack Unit Id">
            <ShortId value={item.rackUnitId} full />
          </DetailRow>
          <DetailRow label="Effective To">
            {formatDate(item.effectiveTo)}
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Network Device"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
