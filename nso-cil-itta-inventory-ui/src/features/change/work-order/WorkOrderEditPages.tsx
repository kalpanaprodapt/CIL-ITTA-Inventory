import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { WorkOrderForm } from './WorkOrderForm';
import { useCreateWorkOrder, useUpdateWorkOrder, useWorkOrder } from './hooks';
import type { WorkOrderInput } from './types';

export function WorkOrderCreatePage() {
  const navigate = useNavigate();
  const create = useCreateWorkOrder();
  const [error, setError] = useState<string>();

  function onSubmit(input: WorkOrderInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/change/work-orders/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="work-order-new-title">
      <PageHeader id="work-order-new-title" title="New Work Order" />
      <WorkOrderForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/change/work-orders')}
      />
    </section>
  );
}

export function WorkOrderEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useWorkOrder(id);
  const update = useUpdateWorkOrder(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Work Order" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Work Order." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: WorkOrderInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/change/work-orders/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="work-order-edit-title">
      <PageHeader id="work-order-edit-title" title={`Edit ${query.data.code}`} />
      <WorkOrderForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/change/work-orders/${id}`)}
      />
    </section>
  );
}
