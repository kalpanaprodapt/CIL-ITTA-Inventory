import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TicketLinkForm } from './TicketLinkForm';
import { useCreateTicketLink, useUpdateTicketLink, useTicketLink } from './hooks';
import type { TicketLinkInput } from './types';

export function TicketLinkCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTicketLink();
  const [error, setError] = useState<string>();

  function onSubmit(input: TicketLinkInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/alarm/ticket-links/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="ticket-link-new-title">
      <PageHeader id="ticket-link-new-title" title="New Ticket Link" />
      <TicketLinkForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/alarm/ticket-links')}
      />
    </section>
  );
}

export function TicketLinkEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTicketLink(id);
  const update = useUpdateTicketLink(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Ticket Link" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ticket Link." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TicketLinkInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/alarm/ticket-links/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="ticket-link-edit-title">
      <PageHeader id="ticket-link-edit-title" title={`Edit ${query.data.code}`} />
      <TicketLinkForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/alarm/ticket-links/${id}`)}
      />
    </section>
  );
}
