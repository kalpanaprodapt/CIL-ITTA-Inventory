import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CircuitProviderForm } from './CircuitProviderForm';
import { useCreateCircuitProvider, useUpdateCircuitProvider, useCircuitProvider } from './hooks';
import type { CircuitProviderInput } from './types';

export function CircuitProviderCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCircuitProvider();
  const [error, setError] = useState<string>();

  function onSubmit(input: CircuitProviderInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/circuit/circuit-providers/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="circuit-provider-new-title">
      <PageHeader id="circuit-provider-new-title" title="New Circuit Provider" />
      <CircuitProviderForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/circuit/circuit-providers')}
      />
    </section>
  );
}

export function CircuitProviderEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuitProvider(id);
  const update = useUpdateCircuitProvider(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Circuit Provider" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit Provider." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CircuitProviderInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/circuit/circuit-providers/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="circuit-provider-edit-title">
      <PageHeader id="circuit-provider-edit-title" title={`Edit ${query.data.code}`} />
      <CircuitProviderForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/circuit/circuit-providers/${id}`)}
      />
    </section>
  );
}
