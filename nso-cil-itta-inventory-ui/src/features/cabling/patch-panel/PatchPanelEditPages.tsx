import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PatchPanelForm } from './PatchPanelForm';
import { useCreatePatchPanel, useUpdatePatchPanel, usePatchPanel } from './hooks';
import type { PatchPanelInput } from './types';

export function PatchPanelCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePatchPanel();
  const [error, setError] = useState<string>();

  function onSubmit(input: PatchPanelInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/cabling/patch-panels/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="patch-panel-new-title">
      <PageHeader id="patch-panel-new-title" title="New Patch Panel" />
      <PatchPanelForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/cabling/patch-panels')}
      />
    </section>
  );
}

export function PatchPanelEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePatchPanel(id);
  const update = useUpdatePatchPanel(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Patch Panel" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Patch Panel." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PatchPanelInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/cabling/patch-panels/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="patch-panel-edit-title">
      <PageHeader id="patch-panel-edit-title" title={`Edit ${query.data.code}`} />
      <PatchPanelForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/cabling/patch-panels/${id}`)}
      />
    </section>
  );
}
