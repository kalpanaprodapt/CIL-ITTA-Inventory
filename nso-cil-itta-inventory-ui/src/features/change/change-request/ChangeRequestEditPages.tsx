import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ChangeRequestForm } from './ChangeRequestForm';
import { useCreateChangeRequest, useUpdateChangeRequest, useChangeRequest } from './hooks';
import type { ChangeRequestInput } from './types';

export function ChangeRequestCreatePage() {
  const navigate = useNavigate();
  const create = useCreateChangeRequest();
  const [error, setError] = useState<string>();

  function onSubmit(input: ChangeRequestInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/change/change-requests/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="change-request-new-title">
      <PageHeader id="change-request-new-title" title="New Change Request" />
      <ChangeRequestForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/change/change-requests')}
      />
    </section>
  );
}

export function ChangeRequestEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useChangeRequest(id);
  const update = useUpdateChangeRequest(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Change Request" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Change Request." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ChangeRequestInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/change/change-requests/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="change-request-edit-title">
      <PageHeader id="change-request-edit-title" title={`Edit ${query.data.code}`} />
      <ChangeRequestForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/change/change-requests/${id}`)}
      />
    </section>
  );
}
