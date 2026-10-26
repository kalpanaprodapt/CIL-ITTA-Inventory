import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SyncJobForm } from './SyncJobForm';
import { useCreateSyncJob, useUpdateSyncJob, useSyncJob } from './hooks';
import type { SyncJobInput } from './types';

export function SyncJobCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSyncJob();
  const [error, setError] = useState<string>();

  function onSubmit(input: SyncJobInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/integration/sync-jobs/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sync-job-new-title">
      <PageHeader id="sync-job-new-title" title="New Sync Job" />
      <SyncJobForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/integration/sync-jobs')}
      />
    </section>
  );
}

export function SyncJobEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSyncJob(id);
  const update = useUpdateSyncJob(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sync Job" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sync Job." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SyncJobInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/integration/sync-jobs/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sync-job-edit-title">
      <PageHeader id="sync-job-edit-title" title={`Edit ${query.data.code}`} />
      <SyncJobForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/integration/sync-jobs/${id}`)}
      />
    </section>
  );
}
