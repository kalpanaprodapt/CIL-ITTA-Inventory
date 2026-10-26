import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PathTraceForm } from './PathTraceForm';
import { useCreatePathTrace, useUpdatePathTrace, usePathTrace } from './hooks';
import type { PathTraceInput } from './types';

export function PathTraceCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePathTrace();
  const [error, setError] = useState<string>();

  function onSubmit(input: PathTraceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/topology/path-traces/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="path-trace-new-title">
      <PageHeader id="path-trace-new-title" title="New Path Trace" />
      <PathTraceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/topology/path-traces')}
      />
    </section>
  );
}

export function PathTraceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePathTrace(id);
  const update = useUpdatePathTrace(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Path Trace" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Path Trace." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PathTraceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/topology/path-traces/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="path-trace-edit-title">
      <PageHeader id="path-trace-edit-title" title={`Edit ${query.data.code}`} />
      <PathTraceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/topology/path-traces/${id}`)}
      />
    </section>
  );
}
