import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VlanForm } from './VlanForm';
import { useCreateVlan, useUpdateVlan, useVlan } from './hooks';
import type { VlanInput } from './types';

export function VlanCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVlan();
  const [error, setError] = useState<string>();

  function onSubmit(input: VlanInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/switching/vlans/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vlan-new-title">
      <PageHeader id="vlan-new-title" title="New Vlan" />
      <VlanForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/switching/vlans')}
      />
    </section>
  );
}

export function VlanEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVlan(id);
  const update = useUpdateVlan(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vlan" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vlan." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VlanInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/switching/vlans/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vlan-edit-title">
      <PageHeader id="vlan-edit-title" title={`Edit ${query.data.code}`} />
      <VlanForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/switching/vlans/${id}`)}
      />
    </section>
  );
}
