import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AssetTagForm } from './AssetTagForm';
import { useCreateAssetTag, useUpdateAssetTag, useAssetTag } from './hooks';
import type { AssetTagInput } from './types';

export function AssetTagCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAssetTag();
  const [error, setError] = useState<string>();

  function onSubmit(input: AssetTagInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/assetmgmt/asset-tags/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="asset-tag-new-title">
      <PageHeader id="asset-tag-new-title" title="New Asset Tag" />
      <AssetTagForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/assetmgmt/asset-tags')}
      />
    </section>
  );
}

export function AssetTagEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAssetTag(id);
  const update = useUpdateAssetTag(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Asset Tag" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Asset Tag." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AssetTagInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/assetmgmt/asset-tags/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="asset-tag-edit-title">
      <PageHeader id="asset-tag-edit-title" title={`Edit ${query.data.code}`} />
      <AssetTagForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/assetmgmt/asset-tags/${id}`)}
      />
    </section>
  );
}
