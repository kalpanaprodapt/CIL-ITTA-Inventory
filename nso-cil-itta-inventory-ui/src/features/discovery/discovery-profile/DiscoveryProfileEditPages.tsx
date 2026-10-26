import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { DiscoveryProfileForm } from './DiscoveryProfileForm';
import { useCreateDiscoveryProfile, useUpdateDiscoveryProfile, useDiscoveryProfile } from './hooks';
import type { DiscoveryProfileInput } from './types';

export function DiscoveryProfileCreatePage() {
  const navigate = useNavigate();
  const create = useCreateDiscoveryProfile();
  const [error, setError] = useState<string>();

  function onSubmit(input: DiscoveryProfileInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/discovery/discovery-profiles/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="discovery-profile-new-title">
      <PageHeader id="discovery-profile-new-title" title="New Discovery Profile" />
      <DiscoveryProfileForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/discovery/discovery-profiles')}
      />
    </section>
  );
}

export function DiscoveryProfileEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDiscoveryProfile(id);
  const update = useUpdateDiscoveryProfile(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Discovery Profile" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Discovery Profile." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: DiscoveryProfileInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/discovery/discovery-profiles/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="discovery-profile-edit-title">
      <PageHeader id="discovery-profile-edit-title" title={`Edit ${query.data.code}`} />
      <DiscoveryProfileForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/discovery/discovery-profiles/${id}`)}
      />
    </section>
  );
}
