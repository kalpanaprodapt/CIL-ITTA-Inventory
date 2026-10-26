import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { OpticalChannelForm } from './OpticalChannelForm';
import { useCreateOpticalChannel, useUpdateOpticalChannel, useOpticalChannel } from './hooks';
import type { OpticalChannelInput } from './types';

export function OpticalChannelCreatePage() {
  const navigate = useNavigate();
  const create = useCreateOpticalChannel();
  const [error, setError] = useState<string>();

  function onSubmit(input: OpticalChannelInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/optical/optical-channels/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="optical-channel-new-title">
      <PageHeader id="optical-channel-new-title" title="New Optical Channel" />
      <OpticalChannelForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/optical/optical-channels')}
      />
    </section>
  );
}

export function OpticalChannelEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useOpticalChannel(id);
  const update = useUpdateOpticalChannel(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Optical Channel" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Optical Channel." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: OpticalChannelInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/optical/optical-channels/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="optical-channel-edit-title">
      <PageHeader id="optical-channel-edit-title" title={`Edit ${query.data.code}`} />
      <OpticalChannelForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/optical/optical-channels/${id}`)}
      />
    </section>
  );
}
