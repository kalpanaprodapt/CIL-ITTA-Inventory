import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeServiceCatalogItemStatus, useDeleteServiceCatalogItem, useServiceCatalogItem } from './hooks';
import { SERVICE_CATALOG_ITEM_FAMILY_LABELS, SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS } from './types';

export function ServiceCatalogItemDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceCatalogItem(id);
  const changeStatus = useChangeServiceCatalogItemStatus();
  const remove = useDeleteServiceCatalogItem();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Service Catalog Item" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Catalog Item." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/service/service-catalog-items'),
    });
  }

  return (
    <section aria-labelledby="service-catalog-item-title">
      <PageHeader
        id="service-catalog-item-title"
        title={item.name}
        subtitle={`Service Catalog Item · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/service/service-catalog-items/${item.id}/edit`}>
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
          <DetailRow label="Item Name">
            {item.itemName ?? '—'}
          </DetailRow>
          <DetailRow label="Family">
            {item.family ? SERVICE_CATALOG_ITEM_FAMILY_LABELS[item.family] : '—'}
          </DetailRow>
          <DetailRow label="Base Price">
            {formatDecimal(item.basePrice)}
          </DetailRow>
          <DetailRow label="Billing Unit">
            {item.billingUnit ?? '—'}
          </DetailRow>
          <DetailRow label="Orderable">
            <BooleanCell value={item.orderable} />
          </DetailRow>
          <DetailRow label="Effective To">
            {formatDate(item.effectiveTo)}
          </DetailRow>
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Source System">
            {item.sourceSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Region">
            {item.region ?? '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete Service Catalog Item"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
