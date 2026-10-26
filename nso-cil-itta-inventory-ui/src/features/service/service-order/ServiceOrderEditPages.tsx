import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ServiceOrderForm } from './ServiceOrderForm';
import { useCreateServiceOrder, useUpdateServiceOrder, useServiceOrder } from './hooks';
import type { ServiceOrderInput } from './types';

export function ServiceOrderCreatePage() {
  const navigate = useNavigate();
  const create = useCreateServiceOrder();
  const [error, setError] = useState<string>();

  function onSubmit(input: ServiceOrderInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/service/service-orders/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="service-order-new-title">
      <PageHeader id="service-order-new-title" title="New Service Order" />
      <ServiceOrderForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/service/service-orders')}
      />
    </section>
  );
}

export function ServiceOrderEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useServiceOrder(id);
  const update = useUpdateServiceOrder(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Service Order" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Service Order." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ServiceOrderInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/service/service-orders/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="service-order-edit-title">
      <PageHeader id="service-order-edit-title" title={`Edit ${query.data.code}`} />
      <ServiceOrderForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/service/service-orders/${id}`)}
      />
    </section>
  );
}
