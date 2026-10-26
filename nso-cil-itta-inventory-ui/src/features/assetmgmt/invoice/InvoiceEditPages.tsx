import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { InvoiceForm } from './InvoiceForm';
import { useCreateInvoice, useUpdateInvoice, useInvoice } from './hooks';
import type { InvoiceInput } from './types';

export function InvoiceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateInvoice();
  const [error, setError] = useState<string>();

  function onSubmit(input: InvoiceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/assetmgmt/invoices/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="invoice-new-title">
      <PageHeader id="invoice-new-title" title="New Invoice" />
      <InvoiceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/assetmgmt/invoices')}
      />
    </section>
  );
}

export function InvoiceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useInvoice(id);
  const update = useUpdateInvoice(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Invoice" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Invoice." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: InvoiceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/assetmgmt/invoices/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="invoice-edit-title">
      <PageHeader id="invoice-edit-title" title={`Edit ${query.data.code}`} />
      <InvoiceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/assetmgmt/invoices/${id}`)}
      />
    </section>
  );
}
