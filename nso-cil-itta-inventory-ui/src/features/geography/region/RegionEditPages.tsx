import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RegionForm } from './RegionForm';
import { useCreateRegion, useUpdateRegion, useRegion } from './hooks';
import type { RegionInput } from './types';

export function RegionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRegion();
  const [error, setError] = useState<string>();

  function onSubmit(input: RegionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/geography/regions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="region-new-title">
      <PageHeader id="region-new-title" title="New Region" />
      <RegionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/geography/regions')}
      />
    </section>
  );
}

export function RegionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRegion(id);
  const update = useUpdateRegion(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Region" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Region." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RegionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/geography/regions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="region-edit-title">
      <PageHeader id="region-edit-title" title={`Edit ${query.data.code}`} />
      <RegionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/geography/regions/${id}`)}
      />
    </section>
  );
}
