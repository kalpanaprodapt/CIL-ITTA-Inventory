import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { LicenseKeyForm } from './LicenseKeyForm';
import { useCreateLicenseKey, useUpdateLicenseKey, useLicenseKey } from './hooks';
import type { LicenseKeyInput } from './types';

export function LicenseKeyCreatePage() {
  const navigate = useNavigate();
  const create = useCreateLicenseKey();
  const [error, setError] = useState<string>();

  function onSubmit(input: LicenseKeyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/software/license-keies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="license-key-new-title">
      <PageHeader id="license-key-new-title" title="New License Key" />
      <LicenseKeyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/software/license-keies')}
      />
    </section>
  );
}

export function LicenseKeyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useLicenseKey(id);
  const update = useUpdateLicenseKey(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading License Key" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this License Key." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: LicenseKeyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/software/license-keies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="license-key-edit-title">
      <PageHeader id="license-key-edit-title" title={`Edit ${query.data.code}`} />
      <LicenseKeyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/software/license-keies/${id}`)}
      />
    </section>
  );
}
