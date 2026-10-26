import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SpliceForm } from './SpliceForm';
import { useCreateSplice, useUpdateSplice, useSplice } from './hooks';
import type { SpliceInput } from './types';

export function SpliceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSplice();
  const [error, setError] = useState<string>();

  function onSubmit(input: SpliceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/cabling/splices/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="splice-new-title">
      <PageHeader id="splice-new-title" title="New Splice" />
      <SpliceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/cabling/splices')}
      />
    </section>
  );
}

export function SpliceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSplice(id);
  const update = useUpdateSplice(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Splice" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Splice." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SpliceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/cabling/splices/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="splice-edit-title">
      <PageHeader id="splice-edit-title" title={`Edit ${query.data.code}`} />
      <SpliceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/cabling/splices/${id}`)}
      />
    </section>
  );
}
