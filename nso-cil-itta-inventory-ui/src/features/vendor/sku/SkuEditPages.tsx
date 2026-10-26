import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SkuForm } from './SkuForm';
import { useCreateSku, useUpdateSku, useSku } from './hooks';
import type { SkuInput } from './types';

export function SkuCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSku();
  const [error, setError] = useState<string>();

  function onSubmit(input: SkuInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/vendor/skus/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sku-new-title">
      <PageHeader id="sku-new-title" title="New Sku" />
      <SkuForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/vendor/skus')}
      />
    </section>
  );
}

export function SkuEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSku(id);
  const update = useUpdateSku(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sku" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sku." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SkuInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/vendor/skus/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sku-edit-title">
      <PageHeader id="sku-edit-title" title={`Edit ${query.data.code}`} />
      <SkuForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/vendor/skus/${id}`)}
      />
    </section>
  );
}
