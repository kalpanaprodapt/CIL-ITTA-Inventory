import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PowerFeedForm } from './PowerFeedForm';
import { useCreatePowerFeed, useUpdatePowerFeed, usePowerFeed } from './hooks';
import type { PowerFeedInput } from './types';

export function PowerFeedCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePowerFeed();
  const [error, setError] = useState<string>();

  function onSubmit(input: PowerFeedInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/facility/power-feeds/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="power-feed-new-title">
      <PageHeader id="power-feed-new-title" title="New Power Feed" />
      <PowerFeedForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/facility/power-feeds')}
      />
    </section>
  );
}

export function PowerFeedEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePowerFeed(id);
  const update = useUpdatePowerFeed(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Power Feed" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Power Feed." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PowerFeedInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/facility/power-feeds/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="power-feed-edit-title">
      <PageHeader id="power-feed-edit-title" title={`Edit ${query.data.code}`} />
      <PowerFeedForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/facility/power-feeds/${id}`)}
      />
    </section>
  );
}
