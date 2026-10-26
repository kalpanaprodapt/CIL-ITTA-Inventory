import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { DiscoveryJobForm } from './DiscoveryJobForm';
import { useCreateDiscoveryJob, useUpdateDiscoveryJob, useDiscoveryJob } from './hooks';
import type { DiscoveryJobInput } from './types';

export function DiscoveryJobCreatePage() {
  const navigate = useNavigate();
  const create = useCreateDiscoveryJob();
  const [error, setError] = useState<string>();

  function onSubmit(input: DiscoveryJobInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/discovery/discovery-jobs/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="discovery-job-new-title">
      <PageHeader id="discovery-job-new-title" title="New Discovery Job" />
      <DiscoveryJobForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/discovery/discovery-jobs')}
      />
    </section>
  );
}

export function DiscoveryJobEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDiscoveryJob(id);
  const update = useUpdateDiscoveryJob(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Discovery Job" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Discovery Job." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: DiscoveryJobInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/discovery/discovery-jobs/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="discovery-job-edit-title">
      <PageHeader id="discovery-job-edit-title" title={`Edit ${query.data.code}`} />
      <DiscoveryJobForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/discovery/discovery-jobs/${id}`)}
      />
    </section>
  );
}
