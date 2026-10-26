import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RadioUnitForm } from './RadioUnitForm';
import { useCreateRadioUnit, useUpdateRadioUnit, useRadioUnit } from './hooks';
import type { RadioUnitInput } from './types';

export function RadioUnitCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRadioUnit();
  const [error, setError] = useState<string>();

  function onSubmit(input: RadioUnitInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/radio/radio-units/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="radio-unit-new-title">
      <PageHeader id="radio-unit-new-title" title="New Radio Unit" />
      <RadioUnitForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/radio/radio-units')}
      />
    </section>
  );
}

export function RadioUnitEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRadioUnit(id);
  const update = useUpdateRadioUnit(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Radio Unit" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Radio Unit." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RadioUnitInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/radio/radio-units/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="radio-unit-edit-title">
      <PageHeader id="radio-unit-edit-title" title={`Edit ${query.data.code}`} />
      <RadioUnitForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/radio/radio-units/${id}`)}
      />
    </section>
  );
}
