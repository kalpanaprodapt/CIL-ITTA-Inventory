import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PurchaseOrderForm } from './PurchaseOrderForm';
import { useCreatePurchaseOrder, useUpdatePurchaseOrder, usePurchaseOrder } from './hooks';
import type { PurchaseOrderInput } from './types';

export function PurchaseOrderCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePurchaseOrder();
  const [error, setError] = useState<string>();

  function onSubmit(input: PurchaseOrderInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/assetmgmt/purchase-orders/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="purchase-order-new-title">
      <PageHeader id="purchase-order-new-title" title="New Purchase Order" />
      <PurchaseOrderForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/assetmgmt/purchase-orders')}
      />
    </section>
  );
}

export function PurchaseOrderEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePurchaseOrder(id);
  const update = useUpdatePurchaseOrder(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Purchase Order" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Purchase Order." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PurchaseOrderInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/assetmgmt/purchase-orders/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="purchase-order-edit-title">
      <PageHeader id="purchase-order-edit-title" title={`Edit ${query.data.code}`} />
      <PurchaseOrderForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/assetmgmt/purchase-orders/${id}`)}
      />
    </section>
  );
}
