import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeContractStatus, useDeleteContract, useContract } from './hooks';
import { CONTRACT_CRITICALITY_LABELS } from './types';

export function ContractDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useContract(id);
  const changeStatus = useChangeContractStatus();
  const remove = useDeleteContract();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Contract" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Contract." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/customer/contracts'),
    });
  }

  return (
    <section aria-labelledby="contract-title">
      <PageHeader
        id="contract-title"
        title={item.name}
        subtitle={`Contract · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/customer/contracts/${item.id}/edit`}>
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
          <DetailRow label="Customer Id">
            <ShortId value={item.customerId} full />
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
          <DetailRow label="Term Months">
            {formatNumber(item.termMonths)}
          </DetailRow>
          <DetailRow label="Auto Renew">
            <BooleanCell value={item.autoRenew} />
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
            {item.criticality ? CONTRACT_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Contract"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
