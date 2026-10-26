import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeServiceDependencyStatus, useDeleteServiceDependency, useServiceDependency } from './hooks';
import { SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS, SERVICE_DEPENDENCY_CRITICALITY_LABELS } from './types';

export function ServiceDependencyDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceDependency(id);
  const changeStatus = useChangeServiceDependencyStatus();
  const remove = useDeleteServiceDependency();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Service Dependency" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Dependency." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/service/service-dependencies'),
    });
  }

  return (
    <section aria-labelledby="service-dependency-title">
      <PageHeader
        id="service-dependency-title"
        title={item.name}
        subtitle={`Service Dependency · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/service/service-dependencies/${item.id}/edit`}>
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
          <DetailRow label="Service Id">
            <ShortId value={item.serviceId} full />
          </DetailRow>
          <DetailRow label="Depends On Id">
            <ShortId value={item.dependsOnId} full />
          </DetailRow>
          <DetailRow label="Dependency Type">
            {item.dependencyType ? SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS[item.dependencyType] : '—'}
          </DetailRow>
          <DetailRow label="Impact Score">
            {formatDecimal(item.impactScore)}
          </DetailRow>
          <DetailRow label="Criticality">
            {item.criticality ? SERVICE_DEPENDENCY_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Service Dependency"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
