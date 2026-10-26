import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CircuitTerminationForm } from './CircuitTerminationForm';
import { useCreateCircuitTermination, useUpdateCircuitTermination, useCircuitTermination } from './hooks';
import type { CircuitTerminationInput } from './types';

export function CircuitTerminationCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCircuitTermination();
  const [error, setError] = useState<string>();

  function onSubmit(input: CircuitTerminationInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/circuit/circuit-terminations/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="circuit-termination-new-title">
      <PageHeader id="circuit-termination-new-title" title="New Circuit Termination" />
      <CircuitTerminationForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/circuit/circuit-terminations')}
      />
    </section>
  );
}

export function CircuitTerminationEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuitTermination(id);
  const update = useUpdateCircuitTermination(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Circuit Termination" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit Termination." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CircuitTerminationInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/circuit/circuit-terminations/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="circuit-termination-edit-title">
      <PageHeader id="circuit-termination-edit-title" title={`Edit ${query.data.code}`} />
      <CircuitTerminationForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/circuit/circuit-terminations/${id}`)}
      />
    </section>
  );
}
