import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ServiceInstanceForm } from './ServiceInstanceForm';
import { useCreateServiceInstance, useUpdateServiceInstance, useServiceInstance } from './hooks';
import type { ServiceInstanceInput } from './types';

export function ServiceInstanceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateServiceInstance();
  const [error, setError] = useState<string>();

  function onSubmit(input: ServiceInstanceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/service/service-instances/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="service-instance-new-title">
      <PageHeader id="service-instance-new-title" title="New Service Instance" />
      <ServiceInstanceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/service/service-instances')}
      />
    </section>
  );
}

export function ServiceInstanceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceInstance(id);
  const update = useUpdateServiceInstance(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Service Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Instance." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ServiceInstanceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/service/service-instances/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="service-instance-edit-title">
      <PageHeader id="service-instance-edit-title" title={`Edit ${query.data.code}`} />
      <ServiceInstanceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/service/service-instances/${id}`)}
      />
    </section>
  );
}
