import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CountryForm } from './CountryForm';
import { useCreateCountry, useUpdateCountry, useCountry } from './hooks';
import type { CountryInput } from './types';

export function CountryCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCountry();
  const [error, setError] = useState<string>();

  function onSubmit(input: CountryInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/geography/countries/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="country-new-title">
      <PageHeader id="country-new-title" title="New Country" />
      <CountryForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/geography/countries')}
      />
    </section>
  );
}

export function CountryEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCountry(id);
  const update = useUpdateCountry(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Country" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Country." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CountryInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/geography/countries/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="country-edit-title">
      <PageHeader id="country-edit-title" title={`Edit ${query.data.code}`} />
      <CountryForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/geography/countries/${id}`)}
      />
    </section>
  );
}
