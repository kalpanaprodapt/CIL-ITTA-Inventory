import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { FiberStrandForm } from './FiberStrandForm';
import { useCreateFiberStrand, useUpdateFiberStrand, useFiberStrand } from './hooks';
import type { FiberStrandInput } from './types';

export function FiberStrandCreatePage() {
  const navigate = useNavigate();
  const create = useCreateFiberStrand();
  const [error, setError] = useState<string>();

  function onSubmit(input: FiberStrandInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/cabling/fiber-strands/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="fiber-strand-new-title">
      <PageHeader id="fiber-strand-new-title" title="New Fiber Strand" />
      <FiberStrandForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/cabling/fiber-strands')}
      />
    </section>
  );
}

export function FiberStrandEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useFiberStrand(id);
  const update = useUpdateFiberStrand(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Fiber Strand" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Fiber Strand." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: FiberStrandInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/cabling/fiber-strands/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="fiber-strand-edit-title">
      <PageHeader id="fiber-strand-edit-title" title={`Edit ${query.data.code}`} />
      <FiberStrandForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/cabling/fiber-strands/${id}`)}
      />
    </section>
  );
}
