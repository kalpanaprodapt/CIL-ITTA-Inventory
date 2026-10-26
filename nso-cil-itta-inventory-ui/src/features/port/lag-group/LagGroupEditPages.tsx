import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { LagGroupForm } from './LagGroupForm';
import { useCreateLagGroup, useUpdateLagGroup, useLagGroup } from './hooks';
import type { LagGroupInput } from './types';

export function LagGroupCreatePage() {
  const navigate = useNavigate();
  const create = useCreateLagGroup();
  const [error, setError] = useState<string>();

  function onSubmit(input: LagGroupInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/port/lag-groups/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="lag-group-new-title">
      <PageHeader id="lag-group-new-title" title="New Lag Group" />
      <LagGroupForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/port/lag-groups')}
      />
    </section>
  );
}

export function LagGroupEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLagGroup(id);
  const update = useUpdateLagGroup(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Lag Group" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Lag Group." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: LagGroupInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/port/lag-groups/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="lag-group-edit-title">
      <PageHeader id="lag-group-edit-title" title={`Edit ${query.data.code}`} />
      <LagGroupForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/port/lag-groups/${id}`)}
      />
    </section>
  );
}
