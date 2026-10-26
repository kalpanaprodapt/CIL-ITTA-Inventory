import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CityForm } from './CityForm';
import { useCreateCity, useUpdateCity, useCity } from './hooks';
import type { CityInput } from './types';

export function CityCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCity();
  const [error, setError] = useState<string>();

  function onSubmit(input: CityInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/geography/cities/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="city-new-title">
      <PageHeader id="city-new-title" title="New City" />
      <CityForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/geography/cities')}
      />
    </section>
  );
}

export function CityEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCity(id);
  const update = useUpdateCity(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading City" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this City." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CityInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/geography/cities/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="city-edit-title">
      <PageHeader id="city-edit-title" title={`Edit ${query.data.code}`} />
      <CityForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/geography/cities/${id}`)}
      />
    </section>
  );
}
