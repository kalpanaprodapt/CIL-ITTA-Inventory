import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ForecastModelForm } from './ForecastModelForm';
import { useCreateForecastModel, useUpdateForecastModel, useForecastModel } from './hooks';
import type { ForecastModelInput } from './types';

export function ForecastModelCreatePage() {
  const navigate = useNavigate();
  const create = useCreateForecastModel();
  const [error, setError] = useState<string>();

  function onSubmit(input: ForecastModelInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/capacity/forecast-models/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="forecast-model-new-title">
      <PageHeader id="forecast-model-new-title" title="New Forecast Model" />
      <ForecastModelForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/capacity/forecast-models')}
      />
    </section>
  );
}

export function ForecastModelEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useForecastModel(id);
  const update = useUpdateForecastModel(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Forecast Model" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Forecast Model." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ForecastModelInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/capacity/forecast-models/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="forecast-model-edit-title">
      <PageHeader id="forecast-model-edit-title" title={`Edit ${query.data.code}`} />
      <ForecastModelForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/capacity/forecast-models/${id}`)}
      />
    </section>
  );
}
