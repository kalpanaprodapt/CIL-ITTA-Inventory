import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TransceiverForm } from './TransceiverForm';
import { useCreateTransceiver, useUpdateTransceiver, useTransceiver } from './hooks';
import type { TransceiverInput } from './types';

export function TransceiverCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTransceiver();
  const [error, setError] = useState<string>();

  function onSubmit(input: TransceiverInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/port/transceivers/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="transceiver-new-title">
      <PageHeader id="transceiver-new-title" title="New Transceiver" />
      <TransceiverForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/port/transceivers')}
      />
    </section>
  );
}

export function TransceiverEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTransceiver(id);
  const update = useUpdateTransceiver(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Transceiver" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Transceiver." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TransceiverInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/port/transceivers/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="transceiver-edit-title">
      <PageHeader id="transceiver-edit-title" title={`Edit ${query.data.code}`} />
      <TransceiverForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/port/transceivers/${id}`)}
      />
    </section>
  );
}
