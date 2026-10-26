import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeComplianceCheckStatus, useDeleteComplianceCheck, useComplianceCheck } from './hooks';
import { COMPLIANCE_CHECK_RESULT_LABELS, COMPLIANCE_CHECK_CRITICALITY_LABELS } from './types';

export function ComplianceCheckDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useComplianceCheck(id);
  const changeStatus = useChangeComplianceCheckStatus();
  const remove = useDeleteComplianceCheck();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Compliance Check" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Compliance Check." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/compliance/compliance-checks'),
    });
  }

  return (
    <section aria-labelledby="compliance-check-title">
      <PageHeader
        id="compliance-check-title"
        title={item.name}
        subtitle={`Compliance Check · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/compliance/compliance-checks/${item.id}/edit`}>
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
          <DetailRow label="Rule Id">
            <ShortId value={item.ruleId} full />
          </DetailRow>
          <DetailRow label="Target Ref">
            {item.targetRef ?? '—'}
          </DetailRow>
          <DetailRow label="Checked At">
            {formatDateTime(item.checkedAt)}
          </DetailRow>
          <DetailRow label="Result">
            {item.result ? COMPLIANCE_CHECK_RESULT_LABELS[item.result] : '—'}
          </DetailRow>
          <DetailRow label="Evidence">
            {item.evidence ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? COMPLIANCE_CHECK_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Compliance Check"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
