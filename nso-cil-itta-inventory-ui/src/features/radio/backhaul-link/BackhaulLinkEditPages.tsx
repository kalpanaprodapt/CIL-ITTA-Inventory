import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { BackhaulLinkForm } from './BackhaulLinkForm';
import { useCreateBackhaulLink, useUpdateBackhaulLink, useBackhaulLink } from './hooks';
import type { BackhaulLinkInput } from './types';

export function BackhaulLinkCreatePage() {
  const navigate = useNavigate();
  const create = useCreateBackhaulLink();
  const [error, setError] = useState<string>();

  function onSubmit(input: BackhaulLinkInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/radio/backhaul-links/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="backhaul-link-new-title">
      <PageHeader id="backhaul-link-new-title" title="New Backhaul Link" />
      <BackhaulLinkForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/radio/backhaul-links')}
      />
    </section>
  );
}

export function BackhaulLinkEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useBackhaulLink(id);
  const update = useUpdateBackhaulLink(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Backhaul Link" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Backhaul Link." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: BackhaulLinkInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/radio/backhaul-links/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="backhaul-link-edit-title">
      <PageHeader id="backhaul-link-edit-title" title={`Edit ${query.data.code}`} />
      <BackhaulLinkForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/radio/backhaul-links/${id}`)}
      />
    </section>
  );
}
