import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { BaseStationForm } from './BaseStationForm';
import { useCreateBaseStation, useUpdateBaseStation, useBaseStation } from './hooks';
import type { BaseStationInput } from './types';

export function BaseStationCreatePage() {
  const navigate = useNavigate();
  const create = useCreateBaseStation();
  const [error, setError] = useState<string>();

  function onSubmit(input: BaseStationInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/radio/base-stations/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="base-station-new-title">
      <PageHeader id="base-station-new-title" title="New Base Station" />
      <BaseStationForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/radio/base-stations')}
      />
    </section>
  );
}

export function BaseStationEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBaseStation(id);
  const update = useUpdateBaseStation(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Base Station" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Base Station." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: BaseStationInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/radio/base-stations/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="base-station-edit-title">
      <PageHeader id="base-station-edit-title" title={`Edit ${query.data.code}`} />
      <BaseStationForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/radio/base-stations/${id}`)}
      />
    </section>
  );
}
