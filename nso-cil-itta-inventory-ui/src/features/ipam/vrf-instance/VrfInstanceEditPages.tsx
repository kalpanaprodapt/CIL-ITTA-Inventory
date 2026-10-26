import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VrfInstanceForm } from './VrfInstanceForm';
import { useCreateVrfInstance, useUpdateVrfInstance, useVrfInstance } from './hooks';
import type { VrfInstanceInput } from './types';

export function VrfInstanceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVrfInstance();
  const [error, setError] = useState<string>();

  function onSubmit(input: VrfInstanceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/ipam/vrf-instances/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vrf-instance-new-title">
      <PageHeader id="vrf-instance-new-title" title="New Vrf Instance" />
      <VrfInstanceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/ipam/vrf-instances')}
      />
    </section>
  );
}

export function VrfInstanceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVrfInstance(id);
  const update = useUpdateVrfInstance(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vrf Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vrf Instance." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VrfInstanceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/ipam/vrf-instances/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vrf-instance-edit-title">
      <PageHeader id="vrf-instance-edit-title" title={`Edit ${query.data.code}`} />
      <VrfInstanceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/ipam/vrf-instances/${id}`)}
      />
    </section>
  );
}
