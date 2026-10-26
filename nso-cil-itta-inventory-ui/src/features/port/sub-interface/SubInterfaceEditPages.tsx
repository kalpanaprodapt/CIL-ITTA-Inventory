import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SubInterfaceForm } from './SubInterfaceForm';
import { useCreateSubInterface, useUpdateSubInterface, useSubInterface } from './hooks';
import type { SubInterfaceInput } from './types';

export function SubInterfaceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSubInterface();
  const [error, setError] = useState<string>();

  function onSubmit(input: SubInterfaceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/port/sub-interfaces/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sub-interface-new-title">
      <PageHeader id="sub-interface-new-title" title="New Sub Interface" />
      <SubInterfaceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/port/sub-interfaces')}
      />
    </section>
  );
}

export function SubInterfaceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSubInterface(id);
  const update = useUpdateSubInterface(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sub Interface" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sub Interface." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SubInterfaceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/port/sub-interfaces/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sub-interface-edit-title">
      <PageHeader id="sub-interface-edit-title" title={`Edit ${query.data.code}`} />
      <SubInterfaceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/port/sub-interfaces/${id}`)}
      />
    </section>
  );
}
