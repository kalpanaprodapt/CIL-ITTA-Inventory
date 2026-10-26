import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeMplsLspStatus, useDeleteMplsLsp, useMplsLsp } from './hooks';
import { MPLS_LSP_PATH_TYPE_LABELS, MPLS_LSP_CRITICALITY_LABELS } from './types';

export function MplsLspDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useMplsLsp(id);
  const changeStatus = useChangeMplsLspStatus();
  const remove = useDeleteMplsLsp();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Mpls Lsp" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Mpls Lsp." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/mpls/mpls-lsps'),
    });
  }

  return (
    <section aria-labelledby="mpls-lsp-title">
      <PageHeader
        id="mpls-lsp-title"
        title={item.name}
        subtitle={`Mpls Lsp · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/mpls/mpls-lsps/${item.id}/edit`}>
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
          <DetailRow label="Lsp Name">
            {item.lspName ?? '—'}
          </DetailRow>
          <DetailRow label="Head End Id">
            <ShortId value={item.headEndId} full />
          </DetailRow>
          <DetailRow label="Tail End Id">
            <ShortId value={item.tailEndId} full />
          </DetailRow>
          <DetailRow label="Bandwidth Mbps">
            {formatNumber(item.bandwidthMbps)}
          </DetailRow>
          <DetailRow label="Setup Priority">
            {formatNumber(item.setupPriority)}
          </DetailRow>
          <DetailRow label="Path Type">
            {item.pathType ? MPLS_LSP_PATH_TYPE_LABELS[item.pathType] : '—'}
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
            {item.criticality ? MPLS_LSP_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Mpls Lsp"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
