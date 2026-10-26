import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ServiceEndpointForm } from './ServiceEndpointForm';
import { useCreateServiceEndpoint, useUpdateServiceEndpoint, useServiceEndpoint } from './hooks';
import type { ServiceEndpointInput } from './types';

export function ServiceEndpointCreatePage() {
  const navigate = useNavigate();
  const create = useCreateServiceEndpoint();
  const [error, setError] = useState<string>();

  function onSubmit(input: ServiceEndpointInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/service/service-endpoints/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="service-endpoint-new-title">
      <PageHeader id="service-endpoint-new-title" title="New Service Endpoint" />
      <ServiceEndpointForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/service/service-endpoints')}
      />
    </section>
  );
}

export function ServiceEndpointEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceEndpoint(id);
  const update = useUpdateServiceEndpoint(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Service Endpoint" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Endpoint." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ServiceEndpointInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/service/service-endpoints/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="service-endpoint-edit-title">
      <PageHeader id="service-endpoint-edit-title" title={`Edit ${query.data.code}`} />
      <ServiceEndpointForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/service/service-endpoints/${id}`)}
      />
    </section>
  );
}
