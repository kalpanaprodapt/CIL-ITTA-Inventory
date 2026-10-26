import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AntennaForm } from './AntennaForm';
import { useCreateAntenna, useUpdateAntenna, useAntenna } from './hooks';
import type { AntennaInput } from './types';

export function AntennaCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAntenna();
  const [error, setError] = useState<string>();

  function onSubmit(input: AntennaInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/radio/antennas/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="antenna-new-title">
      <PageHeader id="antenna-new-title" title="New Antenna" />
      <AntennaForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/radio/antennas')}
      />
    </section>
  );
}

export function AntennaEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAntenna(id);
  const update = useUpdateAntenna(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Antenna" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Antenna." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AntennaInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/radio/antennas/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="antenna-edit-title">
      <PageHeader id="antenna-edit-title" title={`Edit ${query.data.code}`} />
      <AntennaForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/radio/antennas/${id}`)}
      />
    </section>
  );
}
