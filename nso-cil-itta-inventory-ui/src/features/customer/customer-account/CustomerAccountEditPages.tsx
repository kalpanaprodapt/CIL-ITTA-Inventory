import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CustomerAccountForm } from './CustomerAccountForm';
import { useCreateCustomerAccount, useUpdateCustomerAccount, useCustomerAccount } from './hooks';
import type { CustomerAccountInput } from './types';

export function CustomerAccountCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCustomerAccount();
  const [error, setError] = useState<string>();

  function onSubmit(input: CustomerAccountInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/customer/customer-accounts/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="customer-account-new-title">
      <PageHeader id="customer-account-new-title" title="New Customer Account" />
      <CustomerAccountForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/customer/customer-accounts')}
      />
    </section>
  );
}

export function CustomerAccountEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCustomerAccount(id);
  const update = useUpdateCustomerAccount(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Customer Account" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Customer Account." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CustomerAccountInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/customer/customer-accounts/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="customer-account-edit-title">
      <PageHeader id="customer-account-edit-title" title={`Edit ${query.data.code}`} />
      <CustomerAccountForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/customer/customer-accounts/${id}`)}
      />
    </section>
  );
}
