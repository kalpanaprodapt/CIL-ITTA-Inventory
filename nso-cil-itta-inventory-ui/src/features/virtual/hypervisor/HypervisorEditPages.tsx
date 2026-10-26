import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { HypervisorForm } from './HypervisorForm';
import { useCreateHypervisor, useUpdateHypervisor, useHypervisor } from './hooks';
import type { HypervisorInput } from './types';

export function HypervisorCreatePage() {
  const navigate = useNavigate();
  const create = useCreateHypervisor();
  const [error, setError] = useState<string>();

  function onSubmit(input: HypervisorInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/virtual/hypervisors/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="hypervisor-new-title">
      <PageHeader id="hypervisor-new-title" title="New Hypervisor" />
      <HypervisorForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/virtual/hypervisors')}
      />
    </section>
  );
}

export function HypervisorEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useHypervisor(id);
  const update = useUpdateHypervisor(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Hypervisor" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Hypervisor." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: HypervisorInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/virtual/hypervisors/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="hypervisor-edit-title">
      <PageHeader id="hypervisor-edit-title" title={`Edit ${query.data.code}`} />
      <HypervisorForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/virtual/hypervisors/${id}`)}
      />
    </section>
  );
}
