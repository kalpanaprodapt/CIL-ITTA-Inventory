import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { DiscoveredAssetForm } from './DiscoveredAssetForm';
import { useCreateDiscoveredAsset, useUpdateDiscoveredAsset, useDiscoveredAsset } from './hooks';
import type { DiscoveredAssetInput } from './types';

export function DiscoveredAssetCreatePage() {
  const navigate = useNavigate();
  const create = useCreateDiscoveredAsset();
  const [error, setError] = useState<string>();

  function onSubmit(input: DiscoveredAssetInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/discovery/discovered-assets/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="discovered-asset-new-title">
      <PageHeader id="discovered-asset-new-title" title="New Discovered Asset" />
      <DiscoveredAssetForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/discovery/discovered-assets')}
      />
    </section>
  );
}

export function DiscoveredAssetEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDiscoveredAsset(id);
  const update = useUpdateDiscoveredAsset(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Discovered Asset" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Discovered Asset." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: DiscoveredAssetInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/discovery/discovered-assets/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="discovered-asset-edit-title">
      <PageHeader id="discovered-asset-edit-title" title={`Edit ${query.data.code}`} />
      <DiscoveredAssetForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/discovery/discovered-assets/${id}`)}
      />
    </section>
  );
}
