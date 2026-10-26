import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { TunnelEndpointForm } from './TunnelEndpointForm';
import { useCreateTunnelEndpoint, useUpdateTunnelEndpoint, useTunnelEndpoint } from './hooks';
import type { TunnelEndpointInput } from './types';

export function TunnelEndpointCreatePage() {
  const navigate = useNavigate();
  const create = useCreateTunnelEndpoint();
  const [error, setError] = useState<string>();

  function onSubmit(input: TunnelEndpointInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/mpls/tunnel-endpoints/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="tunnel-endpoint-new-title">
      <PageHeader id="tunnel-endpoint-new-title" title="New Tunnel Endpoint" />
      <TunnelEndpointForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/mpls/tunnel-endpoints')}
      />
    </section>
  );
}

export function TunnelEndpointEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useTunnelEndpoint(id);
  const update = useUpdateTunnelEndpoint(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Tunnel Endpoint" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Tunnel Endpoint." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: TunnelEndpointInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/mpls/tunnel-endpoints/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="tunnel-endpoint-edit-title">
      <PageHeader id="tunnel-endpoint-edit-title" title={`Edit ${query.data.code}`} />
      <TunnelEndpointForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/mpls/tunnel-endpoints/${id}`)}
      />
    </section>
  );
}
