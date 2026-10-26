import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { FloorForm } from './FloorForm';
import { useCreateFloor, useUpdateFloor, useFloor } from './hooks';
import type { FloorInput } from './types';

export function FloorCreatePage() {
  const navigate = useNavigate();
  const create = useCreateFloor();
  const [error, setError] = useState<string>();

  function onSubmit(input: FloorInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/facility/floors/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="floor-new-title">
      <PageHeader id="floor-new-title" title="New Floor" />
      <FloorForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/facility/floors')}
      />
    </section>
  );
}

export function FloorEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useFloor(id);
  const update = useUpdateFloor(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Floor" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Floor." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: FloorInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/facility/floors/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="floor-edit-title">
      <PageHeader id="floor-edit-title" title={`Edit ${query.data.code}`} />
      <FloorForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/facility/floors/${id}`)}
      />
    </section>
  );
}
