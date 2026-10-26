import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { IntegrationEndpointForm } from './IntegrationEndpointForm';
import { useCreateIntegrationEndpoint, useUpdateIntegrationEndpoint, useIntegrationEndpoint } from './hooks';
import type { IntegrationEndpointInput } from './types';

export function IntegrationEndpointCreatePage() {
  const navigate = useNavigate();
  const create = useCreateIntegrationEndpoint();
  const [error, setError] = useState<string>();

  function onSubmit(input: IntegrationEndpointInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/integration/integration-endpoints/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="integration-endpoint-new-title">
      <PageHeader id="integration-endpoint-new-title" title="New Integration Endpoint" />
      <IntegrationEndpointForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/integration/integration-endpoints')}
      />
    </section>
  );
}

export function IntegrationEndpointEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIntegrationEndpoint(id);
  const update = useUpdateIntegrationEndpoint(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Integration Endpoint" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Integration Endpoint." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: IntegrationEndpointInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/integration/integration-endpoints/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="integration-endpoint-edit-title">
      <PageHeader id="integration-endpoint-edit-title" title={`Edit ${query.data.code}`} />
      <IntegrationEndpointForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/integration/integration-endpoints/${id}`)}
      />
    </section>
  );
}
