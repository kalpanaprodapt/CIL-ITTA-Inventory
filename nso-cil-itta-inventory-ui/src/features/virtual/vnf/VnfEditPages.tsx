import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VnfForm } from './VnfForm';
import { useCreateVnf, useUpdateVnf, useVnf } from './hooks';
import type { VnfInput } from './types';

export function VnfCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVnf();
  const [error, setError] = useState<string>();

  function onSubmit(input: VnfInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/virtual/vnfs/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vnf-new-title">
      <PageHeader id="vnf-new-title" title="New Vnf" />
      <VnfForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/virtual/vnfs')}
      />
    </section>
  );
}

export function VnfEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVnf(id);
  const update = useUpdateVnf(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vnf" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vnf." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VnfInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/virtual/vnfs/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vnf-edit-title">
      <PageHeader id="vnf-edit-title" title={`Edit ${query.data.code}`} />
      <VnfForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/virtual/vnfs/${id}`)}
      />
    </section>
  );
}
