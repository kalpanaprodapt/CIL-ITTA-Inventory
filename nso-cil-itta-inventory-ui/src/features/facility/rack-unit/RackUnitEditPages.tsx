import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RackUnitForm } from './RackUnitForm';
import { useCreateRackUnit, useUpdateRackUnit, useRackUnit } from './hooks';
import type { RackUnitInput } from './types';

export function RackUnitCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRackUnit();
  const [error, setError] = useState<string>();

  function onSubmit(input: RackUnitInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/facility/rack-units/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="rack-unit-new-title">
      <PageHeader id="rack-unit-new-title" title="New Rack Unit" />
      <RackUnitForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/facility/rack-units')}
      />
    </section>
  );
}

export function RackUnitEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRackUnit(id);
  const update = useUpdateRackUnit(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Rack Unit" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Rack Unit." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RackUnitInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/facility/rack-units/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="rack-unit-edit-title">
      <PageHeader id="rack-unit-edit-title" title={`Edit ${query.data.code}`} />
      <RackUnitForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/facility/rack-units/${id}`)}
      />
    </section>
  );
}
