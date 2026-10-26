import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VirtualMachineForm } from './VirtualMachineForm';
import { useCreateVirtualMachine, useUpdateVirtualMachine, useVirtualMachine } from './hooks';
import type { VirtualMachineInput } from './types';

export function VirtualMachineCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVirtualMachine();
  const [error, setError] = useState<string>();

  function onSubmit(input: VirtualMachineInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/virtual/virtual-machines/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="virtual-machine-new-title">
      <PageHeader id="virtual-machine-new-title" title="New Virtual Machine" />
      <VirtualMachineForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/virtual/virtual-machines')}
      />
    </section>
  );
}

export function VirtualMachineEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVirtualMachine(id);
  const update = useUpdateVirtualMachine(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Virtual Machine" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Virtual Machine." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VirtualMachineInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/virtual/virtual-machines/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="virtual-machine-edit-title">
      <PageHeader id="virtual-machine-edit-title" title={`Edit ${query.data.code}`} />
      <VirtualMachineForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/virtual/virtual-machines/${id}`)}
      />
    </section>
  );
}
