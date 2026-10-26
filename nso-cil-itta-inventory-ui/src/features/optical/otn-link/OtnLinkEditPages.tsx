import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { OtnLinkForm } from './OtnLinkForm';
import { useCreateOtnLink, useUpdateOtnLink, useOtnLink } from './hooks';
import type { OtnLinkInput } from './types';

export function OtnLinkCreatePage() {
  const navigate = useNavigate();
  const create = useCreateOtnLink();
  const [error, setError] = useState<string>();

  function onSubmit(input: OtnLinkInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/optical/otn-links/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="otn-link-new-title">
      <PageHeader id="otn-link-new-title" title="New Otn Link" />
      <OtnLinkForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/optical/otn-links')}
      />
    </section>
  );
}

export function OtnLinkEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useOtnLink(id);
  const update = useUpdateOtnLink(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Otn Link" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Otn Link." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: OtnLinkInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/optical/otn-links/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="otn-link-edit-title">
      <PageHeader id="otn-link-edit-title" title={`Edit ${query.data.code}`} />
      <OtnLinkForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/optical/otn-links/${id}`)}
      />
    </section>
  );
}
