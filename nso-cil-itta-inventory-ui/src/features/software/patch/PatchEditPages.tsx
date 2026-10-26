import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PatchForm } from './PatchForm';
import { useCreatePatch, useUpdatePatch, usePatch } from './hooks';
import type { PatchInput } from './types';

export function PatchCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePatch();
  const [error, setError] = useState<string>();

  function onSubmit(input: PatchInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/software/patchs/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="patch-new-title">
      <PageHeader id="patch-new-title" title="New Patch" />
      <PatchForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/software/patchs')}
      />
    </section>
  );
}

export function PatchEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePatch(id);
  const update = useUpdatePatch(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Patch" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Patch." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PatchInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/software/patchs/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="patch-edit-title">
      <PageHeader id="patch-edit-title" title={`Edit ${query.data.code}`} />
      <PatchForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/software/patchs/${id}`)}
      />
    </section>
  );
}
