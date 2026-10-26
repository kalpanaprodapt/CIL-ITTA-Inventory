import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { BuildingForm } from './BuildingForm';
import { useCreateBuilding, useUpdateBuilding, useBuilding } from './hooks';
import type { BuildingInput } from './types';

export function BuildingCreatePage() {
  const navigate = useNavigate();
  const create = useCreateBuilding();
  const [error, setError] = useState<string>();

  function onSubmit(input: BuildingInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/geography/buildings/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="building-new-title">
      <PageHeader id="building-new-title" title="New Building" />
      <BuildingForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/geography/buildings')}
      />
    </section>
  );
}

export function BuildingEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBuilding(id);
  const update = useUpdateBuilding(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Building" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Building." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: BuildingInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/geography/buildings/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="building-edit-title">
      <PageHeader id="building-edit-title" title={`Edit ${query.data.code}`} />
      <BuildingForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/geography/buildings/${id}`)}
      />
    </section>
  );
}
