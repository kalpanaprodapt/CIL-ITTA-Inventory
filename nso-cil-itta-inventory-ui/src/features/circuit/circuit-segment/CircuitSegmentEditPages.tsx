import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CircuitSegmentForm } from './CircuitSegmentForm';
import { useCreateCircuitSegment, useUpdateCircuitSegment, useCircuitSegment } from './hooks';
import type { CircuitSegmentInput } from './types';

export function CircuitSegmentCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCircuitSegment();
  const [error, setError] = useState<string>();

  function onSubmit(input: CircuitSegmentInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/circuit/circuit-segments/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="circuit-segment-new-title">
      <PageHeader id="circuit-segment-new-title" title="New Circuit Segment" />
      <CircuitSegmentForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/circuit/circuit-segments')}
      />
    </section>
  );
}

export function CircuitSegmentEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCircuitSegment(id);
  const update = useUpdateCircuitSegment(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Circuit Segment" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Circuit Segment." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CircuitSegmentInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/circuit/circuit-segments/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="circuit-segment-edit-title">
      <PageHeader id="circuit-segment-edit-title" title={`Edit ${query.data.code}`} />
      <CircuitSegmentForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/circuit/circuit-segments/${id}`)}
      />
    </section>
  );
}
