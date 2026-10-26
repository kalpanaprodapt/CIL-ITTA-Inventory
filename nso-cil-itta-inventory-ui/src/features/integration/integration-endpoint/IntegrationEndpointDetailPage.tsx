import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeIntegrationEndpointStatus, useDeleteIntegrationEndpoint, useIntegrationEndpoint } from './hooks';
import { INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS, INTEGRATION_ENDPOINT_CRITICALITY_LABELS } from './types';

export function IntegrationEndpointDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIntegrationEndpoint(id);
  const changeStatus = useChangeIntegrationEndpointStatus();
  const remove = useDeleteIntegrationEndpoint();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading Integration Endpoint" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Integration Endpoint." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/integration/integration-endpoints'),
    });
  }

  return (
    <section aria-labelledby="integration-endpoint-title">
      <PageHeader
        id="integration-endpoint-title"
        title={item.name}
        subtitle={`Integration Endpoint · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/integration/integration-endpoints/${item.id}/edit`}>
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
          <DetailRow label="Endpoint Name">
            {item.endpointName ?? '—'}
          </DetailRow>
          <DetailRow label="Target System">
            {item.targetSystem ?? '—'}
          </DetailRow>
          <DetailRow label="Base Url">
            {item.baseUrl ?? '—'}
          </DetailRow>
          <DetailRow label="Auth Type">
            {item.authType ? INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS[item.authType] : '—'}
          </DetailRow>
          <DetailRow label="Timeout Ms">
            {formatNumber(item.timeoutMs)}
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
            {item.criticality ? INTEGRATION_ENDPOINT_CRITICALITY_LABELS[item.criticality] : '—'}
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
        title="Delete Integration Endpoint"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
