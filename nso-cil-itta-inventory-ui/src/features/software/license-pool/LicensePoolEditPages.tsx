import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { LicensePoolForm } from './LicensePoolForm';
import { useCreateLicensePool, useUpdateLicensePool, useLicensePool } from './hooks';
import type { LicensePoolInput } from './types';

export function LicensePoolCreatePage() {
  const navigate = useNavigate();
  const create = useCreateLicensePool();
  const [error, setError] = useState<string>();

  function onSubmit(input: LicensePoolInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/software/license-pools/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="license-pool-new-title">
      <PageHeader id="license-pool-new-title" title="New License Pool" />
      <LicensePoolForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/software/license-pools')}
      />
    </section>
  );
}

export function LicensePoolEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLicensePool(id);
  const update = useUpdateLicensePool(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading License Pool" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this License Pool." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: LicensePoolInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/software/license-pools/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="license-pool-edit-title">
      <PageHeader id="license-pool-edit-title" title={`Edit ${query.data.code}`} />
      <LicensePoolForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/software/license-pools/${id}`)}
      />
    </section>
  );
}
