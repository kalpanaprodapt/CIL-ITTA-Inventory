import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangePolicyDocumentStatus, useDeletePolicyDocument, usePolicyDocument } from './hooks';
import { POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS, POLICY_DOCUMENT_CRITICALITY_LABELS } from './types';

export function PolicyDocumentDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePolicyDocument(id);
  const changeStatus = useChangePolicyDocumentStatus();
  const remove = useDeletePolicyDocument();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Policy Document" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Policy Document." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/compliance/policy-documents'),
    });
  }

  return (
    <section aria-labelledby="policy-document-title">
      <PageHeader
        id="policy-document-title"
        title={item.name}
        subtitle={`Policy Document · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/compliance/policy-documents/${item.id}/edit`}>
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
          <DetailRow label="Title">
            {item.title ?? '—'}
          </DetailRow>
          <DetailRow label="Doc Version">
            {item.docVersion ?? '—'}
          </DetailRow>
          <DetailRow label="Effective Date">
            {formatDate(item.effectiveDate)}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Document Url">
            {item.documentUrl ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? POLICY_DOCUMENT_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Policy Document"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
