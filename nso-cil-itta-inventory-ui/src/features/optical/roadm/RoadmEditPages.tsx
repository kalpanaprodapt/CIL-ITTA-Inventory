import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RoadmForm } from './RoadmForm';
import { useCreateRoadm, useUpdateRoadm, useRoadm } from './hooks';
import type { RoadmInput } from './types';

export function RoadmCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRoadm();
  const [error, setError] = useState<string>();

  function onSubmit(input: RoadmInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/optical/roadms/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="roadm-new-title">
      <PageHeader id="roadm-new-title" title="New Roadm" />
      <RoadmForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/optical/roadms')}
      />
    </section>
  );
}

export function RoadmEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRoadm(id);
  const update = useUpdateRoadm(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Roadm" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Roadm." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RoadmInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/optical/roadms/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="roadm-edit-title">
      <PageHeader id="roadm-edit-title" title={`Edit ${query.data.code}`} />
      <RoadmForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/optical/roadms/${id}`)}
      />
    </section>
  );
}
