import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CircuitForm } from './CircuitForm';
import { useCreateCircuit, useUpdateCircuit, useCircuit } from './hooks';
import type { CircuitInput } from './types';

export function CircuitCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCircuit();
  const [error, setError] = useState<string>();

  function onSubmit(input: CircuitInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/circuit/circuits/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="circuit-new-title">
      <PageHeader id="circuit-new-title" title="New Circuit" />
      <CircuitForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/circuit/circuits')}
      />
    </section>
  );
}

export function CircuitEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuit(id);
  const update = useUpdateCircuit(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Circuit" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CircuitInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/circuit/circuits/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="circuit-edit-title">
      <PageHeader id="circuit-edit-title" title={`Edit ${query.data.code}`} />
      <CircuitForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/circuit/circuits/${id}`)}
      />
    </section>
  );
}
