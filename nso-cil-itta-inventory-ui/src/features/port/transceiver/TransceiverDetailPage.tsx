import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeTransceiverStatus, useDeleteTransceiver, useTransceiver } from './hooks';
import { TRANSCEIVER_FORM_FACTOR_LABELS, TRANSCEIVER_LIFECYCLE_STAGE_LABELS } from './types';

export function TransceiverDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTransceiver(id);
  const changeStatus = useChangeTransceiverStatus();
  const remove = useDeleteTransceiver();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Transceiver" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Transceiver." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/port/transceivers'),
    });
  }

  return (
    <section aria-labelledby="transceiver-title">
      <PageHeader
        id="transceiver-title"
        title={item.name}
        subtitle={`Transceiver · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/port/transceivers/${item.id}/edit`}>
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
          <DetailRow label="Port Id">
            <ShortId value={item.portId} full />
          </DetailRow>
          <DetailRow label="Form Factor">
            {item.formFactor ? TRANSCEIVER_FORM_FACTOR_LABELS[item.formFactor] : '—'}
          </DetailRow>
          <DetailRow label="Wavelength Nm">
            {formatNumber(item.wavelengthNm)}
          </DetailRow>
          <DetailRow label="Reach Km">
            {formatDecimal(item.reachKm)}
          </DetailRow>
          <DetailRow label="Serial Number">
            {item.serialNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Vendor Part">
            {item.vendorPart ?? '—'}
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? TRANSCEIVER_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Transceiver"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
