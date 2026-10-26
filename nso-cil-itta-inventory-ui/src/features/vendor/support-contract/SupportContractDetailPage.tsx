import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeSupportContractStatus, useDeleteSupportContract, useSupportContract } from './hooks';
import { SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS, SUPPORT_CONTRACT_CRITICALITY_LABELS } from './types';

export function SupportContractDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSupportContract(id);
  const changeStatus = useChangeSupportContractStatus();
  const remove = useDeleteSupportContract();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Support Contract" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Support Contract." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/vendor/support-contracts'),
    });
  }

  return (
    <section aria-labelledby="support-contract-title">
      <PageHeader
        id="support-contract-title"
        title={item.name}
        subtitle={`Support Contract · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/vendor/support-contracts/${item.id}/edit`}>
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
          <DetailRow label="Vendor Id">
            <ShortId value={item.vendorId} full />
          </DetailRow>
          <DetailRow label="Contract Number">
            {item.contractNumber ?? '—'}
          </DetailRow>
          <DetailRow label="Start Date">
            {formatDate(item.startDate)}
          </DetailRow>
          <DetailRow label="End Date">
            {formatDate(item.endDate)}
          </DetailRow>
          <DetailRow label="Coverage Level">
            {item.coverageLevel ? SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS[item.coverageLevel] : '—'}
          </DetailRow>
          <DetailRow label="Annual Cost">
            {formatDecimal(item.annualCost)}
          </DetailRow>
          <DetailRow label="Owner">
            {item.owner ?? '—'}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? SUPPORT_CONTRACT_CRITICALITY_LABELS[item.criticality] : '—'}
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
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Support Contract"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
