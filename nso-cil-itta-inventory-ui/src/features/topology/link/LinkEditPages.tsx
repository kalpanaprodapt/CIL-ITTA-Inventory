import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { LinkForm } from './LinkForm';
import { useCreateLink, useUpdateLink, useLink } from './hooks';
import type { LinkInput } from './types';

export function LinkCreatePage() {
  const navigate = useNavigate();
  const create = useCreateLink();
  const [error, setError] = useState<string>();

  function onSubmit(input: LinkInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/topology/links/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="link-new-title">
      <PageHeader id="link-new-title" title="New Link" />
      <LinkForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/topology/links')}
      />
    </section>
  );
}

export function LinkEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLink(id);
  const update = useUpdateLink(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Link" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Link." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: LinkInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/topology/links/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="link-edit-title">
      <PageHeader id="link-edit-title" title={`Edit ${query.data.code}`} />
      <LinkForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/topology/links/${id}`)}
      />
    </section>
  );
}
