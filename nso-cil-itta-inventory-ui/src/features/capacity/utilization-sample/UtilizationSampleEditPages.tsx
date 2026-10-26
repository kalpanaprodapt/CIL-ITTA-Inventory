import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { UtilizationSampleForm } from './UtilizationSampleForm';
import { useCreateUtilizationSample, useUpdateUtilizationSample, useUtilizationSample } from './hooks';
import type { UtilizationSampleInput } from './types';

export function UtilizationSampleCreatePage() {
  const navigate = useNavigate();
  const create = useCreateUtilizationSample();
  const [error, setError] = useState<string>();

  function onSubmit(input: UtilizationSampleInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/capacity/utilization-samples/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="utilization-sample-new-title">
      <PageHeader id="utilization-sample-new-title" title="New Utilization Sample" />
      <UtilizationSampleForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/capacity/utilization-samples')}
      />
    </section>
  );
}

export function UtilizationSampleEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useUtilizationSample(id);
  const update = useUpdateUtilizationSample(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Utilization Sample" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Utilization Sample." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: UtilizationSampleInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/capacity/utilization-samples/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="utilization-sample-edit-title">
      <PageHeader id="utilization-sample-edit-title" title={`Edit ${query.data.code}`} />
      <UtilizationSampleForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/capacity/utilization-samples/${id}`)}
      />
    </section>
  );
}
