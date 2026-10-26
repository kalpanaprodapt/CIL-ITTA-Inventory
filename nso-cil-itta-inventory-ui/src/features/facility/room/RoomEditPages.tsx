import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RoomForm } from './RoomForm';
import { useCreateRoom, useUpdateRoom, useRoom } from './hooks';
import type { RoomInput } from './types';

export function RoomCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRoom();
  const [error, setError] = useState<string>();

  function onSubmit(input: RoomInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/facility/rooms/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="room-new-title">
      <PageHeader id="room-new-title" title="New Room" />
      <RoomForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/facility/rooms')}
      />
    </section>
  );
}

export function RoomEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRoom(id);
  const update = useUpdateRoom(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Room" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Room." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RoomInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/facility/rooms/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="room-edit-title">
      <PageHeader id="room-edit-title" title={`Edit ${query.data.code}`} />
      <RoomForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/facility/rooms/${id}`)}
      />
    </section>
  );
}
