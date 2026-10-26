import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CableForm } from './CableForm';
import { useCreateCable, useUpdateCable, useCable } from './hooks';
import type { CableInput } from './types';

export function CableCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCable();
  const [error, setError] = useState<string>();

  function onSubmit(input: CableInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/cabling/cables/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="cable-new-title">
      <PageHeader id="cable-new-title" title="New Cable" />
      <CableForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/cabling/cables')}
      />
    </section>
  );
}

export function CableEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCable(id);
  const update = useUpdateCable(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Cable" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Cable." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CableInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/cabling/cables/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="cable-edit-title">
      <PageHeader id="cable-edit-title" title={`Edit ${query.data.code}`} />
      <CableForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/cabling/cables/${id}`)}
      />
    </section>
  );
}
