import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { InterfaceForm } from './InterfaceForm';
import { useCreateInterface, useUpdateInterface, useInterface } from './hooks';
import type { InterfaceInput } from './types';

export function InterfaceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateInterface();
  const [error, setError] = useState<string>();

  function onSubmit(input: InterfaceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/port/interfaces/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="interface-new-title">
      <PageHeader id="interface-new-title" title="New Interface" />
      <InterfaceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/port/interfaces')}
      />
    </section>
  );
}

export function InterfaceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useInterface(id);
  const update = useUpdateInterface(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Interface" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Interface." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: InterfaceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/port/interfaces/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="interface-edit-title">
      <PageHeader id="interface-edit-title" title={`Edit ${query.data.code}`} />
      <InterfaceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/port/interfaces/${id}`)}
      />
    </section>
  );
}
