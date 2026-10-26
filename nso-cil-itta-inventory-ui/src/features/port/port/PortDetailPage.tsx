import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangePortStatus, useDeletePort, usePort } from './hooks';
import { PORT_MEDIA_TYPE_LABELS, PORT_ADMIN_STATE_LABELS, PORT_CRITICALITY_LABELS } from './types';

export function PortDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePort(id);
  const changeStatus = useChangePortStatus();
  const remove = useDeletePort();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Port" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Port." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/port/ports'),
    });
  }

  return (
    <section aria-labelledby="port-title">
      <PageHeader
        id="port-title"
        title={item.name}
        subtitle={`Port · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/port/ports/${item.id}/edit`}>
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
          <DetailRow label="Card Id">
            <ShortId value={item.cardId} full />
          </DetailRow>
          <DetailRow label="Port Number">
            {item.portNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Speed Mbps">
            {formatNumber(item.speedMbps)}
          </DetailRow>
          <DetailRow label="Media Type">
            {item.mediaType ? PORT_MEDIA_TYPE_LABELS[item.mediaType] : '—'}
          </DetailRow>
          <DetailRow label="Admin State">
            {item.adminState ? PORT_ADMIN_STATE_LABELS[item.adminState] : '—'}
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
            {item.criticality ? PORT_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Cost Center">
            {item.costCenter ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Port"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
