import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeAmplifierStatus, useDeleteAmplifier, useAmplifier } from './hooks';
import { AMPLIFIER_AMP_TYPE_LABELS, AMPLIFIER_LIFECYCLE_STAGE_LABELS, AMPLIFIER_CRITICALITY_LABELS } from './types';

export function AmplifierDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAmplifier(id);
  const changeStatus = useChangeAmplifierStatus();
  const remove = useDeleteAmplifier();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Amplifier" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Amplifier." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/optical/amplifiers'),
    });
  }

  return (
    <section aria-labelledby="amplifier-title">
      <PageHeader
        id="amplifier-title"
        title={item.name}
        subtitle={`Amplifier · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/optical/amplifiers/${item.id}/edit`}>
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
          <DetailRow label="System Id">
            <ShortId value={item.systemId} full />
          </DetailRow>
          <DetailRow label="Amp Type">
            {item.ampType ? AMPLIFIER_AMP_TYPE_LABELS[item.ampType] : '—'}
          </DetailRow>
          <DetailRow label="Gain Db">
            {formatDecimal(item.gainDb)}
          </DetailRow>
          <DetailRow label="Output Power Dbm">
            {formatDecimal(item.outputPowerDbm)}
          </DetailRow>
          <DetailRow label="Noise Figure Db">
            {formatDecimal(item.noiseFigureDb)}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? AMPLIFIER_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
            {item.criticality ? AMPLIFIER_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Amplifier"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
