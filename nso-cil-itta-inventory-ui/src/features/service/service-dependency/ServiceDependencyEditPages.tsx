import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ServiceDependencyForm } from './ServiceDependencyForm';
import { useCreateServiceDependency, useUpdateServiceDependency, useServiceDependency } from './hooks';
import type { ServiceDependencyInput } from './types';

export function ServiceDependencyCreatePage() {
  const navigate = useNavigate();
  const create = useCreateServiceDependency();
  const [error, setError] = useState<string>();

  function onSubmit(input: ServiceDependencyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/service/service-dependencies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="service-dependency-new-title">
      <PageHeader id="service-dependency-new-title" title="New Service Dependency" />
      <ServiceDependencyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/service/service-dependencies')}
      />
    </section>
  );
}

export function ServiceDependencyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceDependency(id);
  const update = useUpdateServiceDependency(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Service Dependency" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Dependency." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ServiceDependencyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/service/service-dependencies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="service-dependency-edit-title">
      <PageHeader id="service-dependency-edit-title" title={`Edit ${query.data.code}`} />
      <ServiceDependencyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/service/service-dependencies/${id}`)}
      />
    </section>
  );
}
