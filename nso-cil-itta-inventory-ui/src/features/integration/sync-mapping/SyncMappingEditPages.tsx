import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SyncMappingForm } from './SyncMappingForm';
import { useCreateSyncMapping, useUpdateSyncMapping, useSyncMapping } from './hooks';
import type { SyncMappingInput } from './types';

export function SyncMappingCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSyncMapping();
  const [error, setError] = useState<string>();

  function onSubmit(input: SyncMappingInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/integration/sync-mappings/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sync-mapping-new-title">
      <PageHeader id="sync-mapping-new-title" title="New Sync Mapping" />
      <SyncMappingForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/integration/sync-mappings')}
      />
    </section>
  );
}

export function SyncMappingEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSyncMapping(id);
  const update = useUpdateSyncMapping(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sync Mapping" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sync Mapping." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SyncMappingInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/integration/sync-mappings/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sync-mapping-edit-title">
      <PageHeader id="sync-mapping-edit-title" title={`Edit ${query.data.code}`} />
      <SyncMappingForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/integration/sync-mappings/${id}`)}
      />
    </section>
  );
}
