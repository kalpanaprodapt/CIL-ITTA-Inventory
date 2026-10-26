import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PatchPortForm } from './PatchPortForm';
import { useCreatePatchPort, useUpdatePatchPort, usePatchPort } from './hooks';
import type { PatchPortInput } from './types';

export function PatchPortCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePatchPort();
  const [error, setError] = useState<string>();

  function onSubmit(input: PatchPortInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/cabling/patch-ports/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="patch-port-new-title">
      <PageHeader id="patch-port-new-title" title="New Patch Port" />
      <PatchPortForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/cabling/patch-ports')}
      />
    </section>
  );
}

export function PatchPortEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePatchPort(id);
  const update = useUpdatePatchPort(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Patch Port" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Patch Port." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PatchPortInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/cabling/patch-ports/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="patch-port-edit-title">
      <PageHeader id="patch-port-edit-title" title={`Edit ${query.data.code}`} />
      <PatchPortForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/cabling/patch-ports/${id}`)}
      />
    </section>
  );
}
