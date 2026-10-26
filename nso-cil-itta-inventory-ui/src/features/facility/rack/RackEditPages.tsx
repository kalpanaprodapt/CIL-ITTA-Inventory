import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RackForm } from './RackForm';
import { useCreateRack, useUpdateRack, useRack } from './hooks';
import type { RackInput } from './types';

export function RackCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRack();
  const [error, setError] = useState<string>();

  function onSubmit(input: RackInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/facility/racks/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="rack-new-title">
      <PageHeader id="rack-new-title" title="New Rack" />
      <RackForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/facility/racks')}
      />
    </section>
  );
}

export function RackEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRack(id);
  const update = useUpdateRack(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Rack" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Rack." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RackInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/facility/racks/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="rack-edit-title">
      <PageHeader id="rack-edit-title" title={`Edit ${query.data.code}`} />
      <RackForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/facility/racks/${id}`)}
      />
    </section>
  );
}
