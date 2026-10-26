import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CustomerForm } from './CustomerForm';
import { useCreateCustomer, useUpdateCustomer, useCustomer } from './hooks';
import type { CustomerInput } from './types';

export function CustomerCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCustomer();
  const [error, setError] = useState<string>();

  function onSubmit(input: CustomerInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/customer/customers/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="customer-new-title">
      <PageHeader id="customer-new-title" title="New Customer" />
      <CustomerForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/customer/customers')}
      />
    </section>
  );
}

export function CustomerEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCustomer(id);
  const update = useUpdateCustomer(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Customer" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Customer." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CustomerInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/customer/customers/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="customer-edit-title">
      <PageHeader id="customer-edit-title" title={`Edit ${query.data.code}`} />
      <CustomerForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/customer/customers/${id}`)}
      />
    </section>
  );
}
