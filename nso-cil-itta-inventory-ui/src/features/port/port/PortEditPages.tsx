import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PortForm } from './PortForm';
import { useCreatePort, useUpdatePort, usePort } from './hooks';
import type { PortInput } from './types';

export function PortCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePort();
  const [error, setError] = useState<string>();

  function onSubmit(input: PortInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/port/ports/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="port-new-title">
      <PageHeader id="port-new-title" title="New Port" />
      <PortForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/port/ports')}
      />
    </section>
  );
}

export function PortEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePort(id);
  const update = useUpdatePort(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Port" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Port." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PortInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/port/ports/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="port-edit-title">
      <PageHeader id="port-edit-title" title={`Edit ${query.data.code}`} />
      <PortForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/port/ports/${id}`)}
      />
    </section>
  );
}
