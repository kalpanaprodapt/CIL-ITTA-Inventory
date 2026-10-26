import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VlanPoolForm } from './VlanPoolForm';
import { useCreateVlanPool, useUpdateVlanPool, useVlanPool } from './hooks';
import type { VlanPoolInput } from './types';

export function VlanPoolCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVlanPool();
  const [error, setError] = useState<string>();

  function onSubmit(input: VlanPoolInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/switching/vlan-pools/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vlan-pool-new-title">
      <PageHeader id="vlan-pool-new-title" title="New Vlan Pool" />
      <VlanPoolForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/switching/vlan-pools')}
      />
    </section>
  );
}

export function VlanPoolEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVlanPool(id);
  const update = useUpdateVlanPool(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vlan Pool" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vlan Pool." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VlanPoolInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/switching/vlan-pools/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vlan-pool-edit-title">
      <PageHeader id="vlan-pool-edit-title" title={`Edit ${query.data.code}`} />
      <VlanPoolForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/switching/vlan-pools/${id}`)}
      />
    </section>
  );
}
