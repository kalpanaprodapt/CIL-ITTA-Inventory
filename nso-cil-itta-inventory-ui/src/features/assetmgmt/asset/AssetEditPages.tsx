import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AssetForm } from './AssetForm';
import { useCreateAsset, useUpdateAsset, useAsset } from './hooks';
import type { AssetInput } from './types';

export function AssetCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAsset();
  const [error, setError] = useState<string>();

  function onSubmit(input: AssetInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/assetmgmt/assets/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="asset-new-title">
      <PageHeader id="asset-new-title" title="New Asset" />
      <AssetForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/assetmgmt/assets')}
      />
    </section>
  );
}

export function AssetEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAsset(id);
  const update = useUpdateAsset(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Asset" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Asset." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AssetInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/assetmgmt/assets/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="asset-edit-title">
      <PageHeader id="asset-edit-title" title={`Edit ${query.data.code}`} />
      <AssetForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/assetmgmt/assets/${id}`)}
      />
    </section>
  );
}
