import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeUtilizationSampleStatus, useDeleteUtilizationSample, useUtilizationSample } from './hooks';
import { UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS, UTILIZATION_SAMPLE_CRITICALITY_LABELS } from './types';

export function UtilizationSampleDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useUtilizationSample(id);
  const changeStatus = useChangeUtilizationSampleStatus();
  const remove = useDeleteUtilizationSample();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Utilization Sample" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Utilization Sample." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/capacity/utilization-samples'),
    });
  }

  return (
    <section aria-labelledby="utilization-sample-title">
      <PageHeader
        id="utilization-sample-title"
        title={item.name}
        subtitle={`Utilization Sample · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/capacity/utilization-samples/${item.id}/edit`}>
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
          <DetailRow label="Target Ref">
            {item.targetRef ?? '—'}
          </DetailRow>
          <DetailRow label="Metric">
            {item.metric ?? '—'}
          </DetailRow>
          <DetailRow label="Sampled At">
            {formatDateTime(item.sampledAt)}
          </DetailRow>
          <DetailRow label="Value Pct">
            {formatDecimal(item.valuePct)}
          </DetailRow>
          <DetailRow label="Peak">
            <BooleanCell value={item.peak} />
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
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
            {item.criticality ? UTILIZATION_SAMPLE_CRITICALITY_LABELS[item.criticality] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Utilization Sample"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
