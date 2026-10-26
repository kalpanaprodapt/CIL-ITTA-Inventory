import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CircuitOrderForm } from './CircuitOrderForm';
import { useCreateCircuitOrder, useUpdateCircuitOrder, useCircuitOrder } from './hooks';
import type { CircuitOrderInput } from './types';

export function CircuitOrderCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCircuitOrder();
  const [error, setError] = useState<string>();

  function onSubmit(input: CircuitOrderInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/circuit/circuit-orders/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="circuit-order-new-title">
      <PageHeader id="circuit-order-new-title" title="New Circuit Order" />
      <CircuitOrderForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/circuit/circuit-orders')}
      />
    </section>
  );
}

export function CircuitOrderEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuitOrder(id);
  const update = useUpdateCircuitOrder(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Circuit Order" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit Order." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CircuitOrderInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/circuit/circuit-orders/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="circuit-order-edit-title">
      <PageHeader id="circuit-order-edit-title" title={`Edit ${query.data.code}`} />
      <CircuitOrderForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/circuit/circuit-orders/${id}`)}
      />
    </section>
  );
}
