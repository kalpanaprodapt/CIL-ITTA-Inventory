import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { WarrantyForm } from './WarrantyForm';
import { useCreateWarranty, useUpdateWarranty, useWarranty } from './hooks';
import type { WarrantyInput } from './types';

export function WarrantyCreatePage() {
  const navigate = useNavigate();
  const create = useCreateWarranty();
  const [error, setError] = useState<string>();

  function onSubmit(input: WarrantyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/vendor/warranties/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="warranty-new-title">
      <PageHeader id="warranty-new-title" title="New Warranty" />
      <WarrantyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/vendor/warranties')}
      />
    </section>
  );
}

export function WarrantyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useWarranty(id);
  const update = useUpdateWarranty(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Warranty" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Warranty." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: WarrantyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/vendor/warranties/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="warranty-edit-title">
      <PageHeader id="warranty-edit-title" title={`Edit ${query.data.code}`} />
      <WarrantyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/vendor/warranties/${id}`)}
      />
    </section>
  );
}
