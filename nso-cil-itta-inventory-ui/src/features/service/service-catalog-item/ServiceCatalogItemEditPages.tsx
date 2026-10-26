import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ServiceCatalogItemForm } from './ServiceCatalogItemForm';
import { useCreateServiceCatalogItem, useUpdateServiceCatalogItem, useServiceCatalogItem } from './hooks';
import type { ServiceCatalogItemInput } from './types';

export function ServiceCatalogItemCreatePage() {
  const navigate = useNavigate();
  const create = useCreateServiceCatalogItem();
  const [error, setError] = useState<string>();

  function onSubmit(input: ServiceCatalogItemInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/service/service-catalog-items/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="service-catalog-item-new-title">
      <PageHeader id="service-catalog-item-new-title" title="New Service Catalog Item" />
      <ServiceCatalogItemForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/service/service-catalog-items')}
      />
    </section>
  );
}

export function ServiceCatalogItemEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceCatalogItem(id);
  const update = useUpdateServiceCatalogItem(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Service Catalog Item" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Catalog Item." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ServiceCatalogItemInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/service/service-catalog-items/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="service-catalog-item-edit-title">
      <PageHeader id="service-catalog-item-edit-title" title={`Edit ${query.data.code}`} />
      <ServiceCatalogItemForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/service/service-catalog-items/${id}`)}
      />
    </section>
  );
}
