import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { EventSourceForm } from './EventSourceForm';
import { useCreateEventSource, useUpdateEventSource, useEventSource } from './hooks';
import type { EventSourceInput } from './types';

export function EventSourceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateEventSource();
  const [error, setError] = useState<string>();

  function onSubmit(input: EventSourceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/alarm/event-sources/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="event-source-new-title">
      <PageHeader id="event-source-new-title" title="New Event Source" />
      <EventSourceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/alarm/event-sources')}
      />
    </section>
  );
}

export function EventSourceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useEventSource(id);
  const update = useUpdateEventSource(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Event Source" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Event Source." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: EventSourceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/alarm/event-sources/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="event-source-edit-title">
      <PageHeader id="event-source-edit-title" title={`Edit ${query.data.code}`} />
      <EventSourceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/alarm/event-sources/${id}`)}
      />
    </section>
  );
}
