import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ChassisForm } from './ChassisForm';
import { useCreateChassis, useUpdateChassis, useChassis } from './hooks';
import type { ChassisInput } from './types';

export function ChassisCreatePage() {
  const navigate = useNavigate();
  const create = useCreateChassis();
  const [error, setError] = useState<string>();

  function onSubmit(input: ChassisInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/device/chassises/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="chassis-new-title">
      <PageHeader id="chassis-new-title" title="New Chassis" />
      <ChassisForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/device/chassises')}
      />
    </section>
  );
}

export function ChassisEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useChassis(id);
  const update = useUpdateChassis(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Chassis" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Chassis." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ChassisInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/device/chassises/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="chassis-edit-title">
      <PageHeader id="chassis-edit-title" title={`Edit ${query.data.code}`} />
      <ChassisForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/device/chassises/${id}`)}
      />
    </section>
  );
}
