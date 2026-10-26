import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from '@/shared/components/Button';
import { BooleanCell, DetailRow, ShortId, StatusBadge } from '@/shared/components/cells';
import { ConfirmDialog } from '@/shared/components/Modal';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { LIFECYCLE_STATUSES } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { formatDate, formatDateTime, formatDecimal, formatNumber } from '@/shared/utils/format';
import { useChangeLicenseKeyStatus, useDeleteLicenseKey, useLicenseKey } from './hooks';
import { LICENSE_KEY_LIFECYCLE_STAGE_LABELS } from './types';

export function LicenseKeyDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLicenseKey(id);
  const changeStatus = useChangeLicenseKeyStatus();
  const remove = useDeleteLicenseKey();
  const [confirmDelete, setConfirmDelete] = useState(false);

  if (query.isLoading) {
    return <Spinner label="Loading License Key" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this License Key." onRetry={() => query.refetch()} />;
  }
  const item = query.data;

  function onStatus(status: LifecycleStatus) {
    changeStatus.mutate({ id: item.id, status });
  }

  function onDelete() {
    remove.mutate(item.id, {
      onSuccess: () => navigate('/software/license-keies'),
    });
  }

  return (
    <section aria-labelledby="license-key-title">
      <PageHeader
        id="license-key-title"
        title={item.name}
        subtitle={`License Key · ${item.code}`}
        actions={
          <>
            <Link className="btn btn--secondary" to={`/software/license-keies/${item.id}/edit`}>
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
          <DetailRow label="Pool Id">
            <ShortId value={item.poolId} full />
          </DetailRow>
          <DetailRow label="Key Value">
            {item.keyValue ?? '—'}
          </DetailRow>
          <DetailRow label="Feature">
            {item.feature ?? '—'}
          </DetailRow>
          <DetailRow label="Expires On">
            {formatDate(item.expiresOn)}
          </DetailRow>
          <DetailRow label="Assigned Device Id">
            <ShortId value={item.assignedDeviceId} full />
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
          <DetailRow label="Labels">
            {item.labels ?? '—'}
          </DetailRow>
          <DetailRow label="Lifecycle Stage">
            {item.lifecycleStage ? LICENSE_KEY_LIFECYCLE_STAGE_LABELS[item.lifecycleStage] : '—'}
          </DetailRow>
          <DetailRow label="Version">{item.version}</DetailRow>
          <DetailRow label="Created">{formatDateTime(item.createdAt)} by {item.createdBy ?? '—'}</DetailRow>
          <DetailRow label="Updated">{formatDateTime(item.updatedAt)} by {item.updatedBy ?? '—'}</DetailRow>
      </dl>
      <ConfirmDialog
        open={confirmDelete}
        title="Delete License Key"
        message={`Delete ${item.code}? This cannot be undone.`}
        confirmLabel="Delete"
        onConfirm={onDelete}
        onCancel={() => setConfirmDelete(false)}
      />
    </section>
  );
}
