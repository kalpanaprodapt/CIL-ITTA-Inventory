import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeFiberStrandStatus, useDeleteFiberStrand, useFiberStrand } from './hooks';
import { FIBER_STRAND_CRITICALITY_LABELS } from './types';

export function FiberStrandDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useFiberStrand(id);
  const changeStatus = useChangeFiberStrandStatus();
  const remove = useDeleteFiberStrand();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Fiber Strand" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Fiber Strand." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/cabling/fiber-strands'),
    });
  }

  return (
    <section aria-labelledby="fiber-strand-title">
      <PageHeader
        id="fiber-strand-title"
        title={item.name}
        subtitle={`Fiber Strand · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/cabling/fiber-strands/${item.id}/edit`}>
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
          <DetailRow label="Cable Id">
            <ShortId value={item.cableId} full />
          </DetailRow>
          <DetailRow label="Strand Number">
            {formatNumber(item.strandNumber)}
          </DetailRow>
          <DetailRow label="Buffer Tube">
            {item.bufferTube ?? '—'}
          </DetailRow>
          <DetailRow label="Tube Color">
            {item.tubeColor ?? '—'}
          </DetailRow>
          <DetailRow label="Attenuation Db">
            {formatDecimal(item.attenuationDb)}
          </DetailRow>
          <DetailRow label="In Use">
            <BooleanCell value={item.inUse} />
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? FIBER_STRAND_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Fiber Strand"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
