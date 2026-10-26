import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { HardwareModelForm } from './HardwareModelForm';
import { useCreateHardwareModel, useUpdateHardwareModel, useHardwareModel } from './hooks';
import type { HardwareModelInput } from './types';

export function HardwareModelCreatePage() {
  const navigate = useNavigate();
  const create = useCreateHardwareModel();
  const [error, setError] = useState<string>();

  function onSubmit(input: HardwareModelInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/vendor/hardware-models/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="hardware-model-new-title">
      <PageHeader id="hardware-model-new-title" title="New Hardware Model" />
      <HardwareModelForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/vendor/hardware-models')}
      />
    </section>
  );
}

export function HardwareModelEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useHardwareModel(id);
  const update = useUpdateHardwareModel(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Hardware Model" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Hardware Model." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: HardwareModelInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/vendor/hardware-models/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="hardware-model-edit-title">
      <PageHeader id="hardware-model-edit-title" title={`Edit ${query.data.code}`} />
      <HardwareModelForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/vendor/hardware-models/${id}`)}
      />
    </section>
  );
}
