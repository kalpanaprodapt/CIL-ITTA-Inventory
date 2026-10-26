import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TopologyViewForm } from './TopologyViewForm';
import { useCreateTopologyView, useUpdateTopologyView, useTopologyView } from './hooks';
import type { TopologyViewInput } from './types';

export function TopologyViewCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTopologyView();
  const [error, setError] = useState<string>();

  function onSubmit(input: TopologyViewInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/topology/topology-views/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="topology-view-new-title">
      <PageHeader id="topology-view-new-title" title="New Topology View" />
      <TopologyViewForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/topology/topology-views')}
      />
    </section>
  );
}

export function TopologyViewEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTopologyView(id);
  const update = useUpdateTopologyView(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Topology View" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Topology View." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TopologyViewInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/topology/topology-views/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="topology-view-edit-title">
      <PageHeader id="topology-view-edit-title" title={`Edit ${query.data.code}`} />
      <TopologyViewForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/topology/topology-views/${id}`)}
      />
    </section>
  );
}
