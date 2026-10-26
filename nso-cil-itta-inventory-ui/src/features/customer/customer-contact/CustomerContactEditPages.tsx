import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CustomerContactForm } from './CustomerContactForm';
import { useCreateCustomerContact, useUpdateCustomerContact, useCustomerContact } from './hooks';
import type { CustomerContactInput } from './types';

export function CustomerContactCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCustomerContact();
  const [error, setError] = useState<string>();

  function onSubmit(input: CustomerContactInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/customer/customer-contacts/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="customer-contact-new-title">
      <PageHeader id="customer-contact-new-title" title="New Customer Contact" />
      <CustomerContactForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/customer/customer-contacts')}
      />
    </section>
  );
}

export function CustomerContactEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCustomerContact(id);
  const update = useUpdateCustomerContact(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Customer Contact" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Customer Contact." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CustomerContactInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/customer/customer-contacts/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="customer-contact-edit-title">
      <PageHeader id="customer-contact-edit-title" title={`Edit ${query.data.code}`} />
      <CustomerContactForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/customer/customer-contacts/${id}`)}
      />
    </section>
  );
}
