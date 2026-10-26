import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SectorForm } from './SectorForm';
import { useCreateSector, useUpdateSector, useSector } from './hooks';
import type { SectorInput } from './types';

export function SectorCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSector();
  const [error, setError] = useState<string>();

  function onSubmit(input: SectorInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/radio/sectors/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sector-new-title">
      <PageHeader id="sector-new-title" title="New Sector" />
      <SectorForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/radio/sectors')}
      />
    </section>
  );
}

export function SectorEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSector(id);
  const update = useUpdateSector(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sector" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sector." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SectorInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/radio/sectors/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sector-edit-title">
      <PageHeader id="sector-edit-title" title={`Edit ${query.data.code}`} />
      <SectorForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/radio/sectors/${id}`)}
      />
    </section>
  );
}
