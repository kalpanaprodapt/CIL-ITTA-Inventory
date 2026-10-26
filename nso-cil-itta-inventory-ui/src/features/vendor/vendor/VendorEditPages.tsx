import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VendorForm } from './VendorForm';
import { useCreateVendor, useUpdateVendor, useVendor } from './hooks';
import type { VendorInput } from './types';

export function VendorCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVendor();
  const [error, setError] = useState<string>();

  function onSubmit(input: VendorInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/vendor/vendors/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vendor-new-title">
      <PageHeader id="vendor-new-title" title="New Vendor" />
      <VendorForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/vendor/vendors')}
      />
    </section>
  );
}

export function VendorEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVendor(id);
  const update = useUpdateVendor(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vendor" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vendor." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VendorInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/vendor/vendors/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vendor-edit-title">
      <PageHeader id="vendor-edit-title" title={`Edit ${query.data.code}`} />
      <VendorForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/vendor/vendors/${id}`)}
      />
    </section>
  );
}
