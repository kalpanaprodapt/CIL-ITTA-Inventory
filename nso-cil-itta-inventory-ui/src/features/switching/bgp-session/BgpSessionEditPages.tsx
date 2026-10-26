import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { BgpSessionForm } from './BgpSessionForm';
import { useCreateBgpSession, useUpdateBgpSession, useBgpSession } from './hooks';
import type { BgpSessionInput } from './types';

export function BgpSessionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateBgpSession();
  const [error, setError] = useState<string>();

  function onSubmit(input: BgpSessionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/switching/bgp-sessions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="bgp-session-new-title">
      <PageHeader id="bgp-session-new-title" title="New Bgp Session" />
      <BgpSessionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/switching/bgp-sessions')}
      />
    </section>
  );
}

export function BgpSessionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBgpSession(id);
  const update = useUpdateBgpSession(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Bgp Session" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Bgp Session." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: BgpSessionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/switching/bgp-sessions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="bgp-session-edit-title">
      <PageHeader id="bgp-session-edit-title" title={`Edit ${query.data.code}`} />
      <BgpSessionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/switching/bgp-sessions/${id}`)}
      />
    </section>
  );
}
