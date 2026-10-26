import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { K8sClusterForm } from './K8sClusterForm';
import { useCreateK8sCluster, useUpdateK8sCluster, useK8sCluster } from './hooks';
import type { K8sClusterInput } from './types';

export function K8sClusterCreatePage() {
  const navigate = useNavigate();
  const create = useCreateK8sCluster();
  const [error, setError] = useState<string>();

  function onSubmit(input: K8sClusterInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/virtual/k8s-clusters/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="k8s-cluster-new-title">
      <PageHeader id="k8s-cluster-new-title" title="New K8s Cluster" />
      <K8sClusterForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/virtual/k8s-clusters')}
      />
    </section>
  );
}

export function K8sClusterEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useK8sCluster(id);
  const update = useUpdateK8sCluster(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading K8s Cluster" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this K8s Cluster." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: K8sClusterInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/virtual/k8s-clusters/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="k8s-cluster-edit-title">
      <PageHeader id="k8s-cluster-edit-title" title={`Edit ${query.data.code}`} />
      <K8sClusterForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/virtual/k8s-clusters/${id}`)}
      />
    </section>
  );
}
