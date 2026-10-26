import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { LineCardForm } from './LineCardForm';
import { useCreateLineCard, useUpdateLineCard, useLineCard } from './hooks';
import type { LineCardInput } from './types';

export function LineCardCreatePage() {
  const navigate = useNavigate();
  const create = useCreateLineCard();
  const [error, setError] = useState<string>();

  function onSubmit(input: LineCardInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/device/line-cards/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="line-card-new-title">
      <PageHeader id="line-card-new-title" title="New Line Card" />
      <LineCardForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/device/line-cards')}
      />
    </section>
  );
}

export function LineCardEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLineCard(id);
  const update = useUpdateLineCard(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Line Card" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Line Card." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: LineCardInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/device/line-cards/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="line-card-edit-title">
      <PageHeader id="line-card-edit-title" title={`Edit ${query.data.code}`} />
      <LineCardForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/device/line-cards/${id}`)}
      />
    </section>
  );
}
