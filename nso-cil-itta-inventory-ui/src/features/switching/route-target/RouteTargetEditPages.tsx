import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { RouteTargetForm } from './RouteTargetForm';
import { useCreateRouteTarget, useUpdateRouteTarget, useRouteTarget } from './hooks';
import type { RouteTargetInput } from './types';

export function RouteTargetCreatePage() {
  const navigate = useNavigate();
  const create = useCreateRouteTarget();
  const [error, setError] = useState<string>();

  function onSubmit(input: RouteTargetInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/switching/route-targets/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="route-target-new-title">
      <PageHeader id="route-target-new-title" title="New Route Target" />
      <RouteTargetForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/switching/route-targets')}
      />
    </section>
  );
}

export function RouteTargetEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useRouteTarget(id);
  const update = useUpdateRouteTarget(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Route Target" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Route Target." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: RouteTargetInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/switching/route-targets/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="route-target-edit-title">
      <PageHeader id="route-target-edit-title" title={`Edit ${query.data.code}`} />
      <RouteTargetForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/switching/route-targets/${id}`)}
      />
    </section>
  );
}
