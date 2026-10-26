import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CloudAccountForm } from './CloudAccountForm';
import { useCreateCloudAccount, useUpdateCloudAccount, useCloudAccount } from './hooks';
import type { CloudAccountInput } from './types';

export function CloudAccountCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCloudAccount();
  const [error, setError] = useState<string>();

  function onSubmit(input: CloudAccountInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/virtual/cloud-accounts/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="cloud-account-new-title">
      <PageHeader id="cloud-account-new-title" title="New Cloud Account" />
      <CloudAccountForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/virtual/cloud-accounts')}
      />
    </section>
  );
}

export function CloudAccountEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCloudAccount(id);
  const update = useUpdateCloudAccount(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Cloud Account" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Cloud Account." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CloudAccountInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/virtual/cloud-accounts/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="cloud-account-edit-title">
      <PageHeader id="cloud-account-edit-title" title={`Edit ${query.data.code}`} />
      <CloudAccountForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/virtual/cloud-accounts/${id}`)}
      />
    </section>
  );
}
