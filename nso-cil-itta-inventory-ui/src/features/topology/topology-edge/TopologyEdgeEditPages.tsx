import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TopologyEdgeForm } from './TopologyEdgeForm';
import { useCreateTopologyEdge, useUpdateTopologyEdge, useTopologyEdge } from './hooks';
import type { TopologyEdgeInput } from './types';

export function TopologyEdgeCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTopologyEdge();
  const [error, setError] = useState<string>();

  function onSubmit(input: TopologyEdgeInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/topology/topology-edges/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="topology-edge-new-title">
      <PageHeader id="topology-edge-new-title" title="New Topology Edge" />
      <TopologyEdgeForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/topology/topology-edges')}
      />
    </section>
  );
}

export function TopologyEdgeEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTopologyEdge(id);
  const update = useUpdateTopologyEdge(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Topology Edge" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Topology Edge." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TopologyEdgeInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/topology/topology-edges/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="topology-edge-edit-title">
      <PageHeader id="topology-edge-edit-title" title={`Edit ${query.data.code}`} />
      <TopologyEdgeForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/topology/topology-edges/${id}`)}
      />
    </section>
  );
}
