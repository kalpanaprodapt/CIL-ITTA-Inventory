import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TopologyNodeForm } from './TopologyNodeForm';
import { useCreateTopologyNode, useUpdateTopologyNode, useTopologyNode } from './hooks';
import type { TopologyNodeInput } from './types';

export function TopologyNodeCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTopologyNode();
  const [error, setError] = useState<string>();

  function onSubmit(input: TopologyNodeInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/topology/topology-nodes/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="topology-node-new-title">
      <PageHeader id="topology-node-new-title" title="New Topology Node" />
      <TopologyNodeForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/topology/topology-nodes')}
      />
    </section>
  );
}

export function TopologyNodeEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTopologyNode(id);
  const update = useUpdateTopologyNode(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Topology Node" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Topology Node." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TopologyNodeInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/topology/topology-nodes/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="topology-node-edit-title">
      <PageHeader id="topology-node-edit-title" title={`Edit ${query.data.code}`} />
      <TopologyNodeForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/topology/topology-nodes/${id}`)}
      />
    </section>
  );
}
