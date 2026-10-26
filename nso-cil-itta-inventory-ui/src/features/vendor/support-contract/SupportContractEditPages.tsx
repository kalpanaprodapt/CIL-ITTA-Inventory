import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SupportContractForm } from './SupportContractForm';
import { useCreateSupportContract, useUpdateSupportContract, useSupportContract } from './hooks';
import type { SupportContractInput } from './types';

export function SupportContractCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSupportContract();
  const [error, setError] = useState<string>();

  function onSubmit(input: SupportContractInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/vendor/support-contracts/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="support-contract-new-title">
      <PageHeader id="support-contract-new-title" title="New Support Contract" />
      <SupportContractForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/vendor/support-contracts')}
      />
    </section>
  );
}

export function SupportContractEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSupportContract(id);
  const update = useUpdateSupportContract(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Support Contract" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Support Contract." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SupportContractInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/vendor/support-contracts/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="support-contract-edit-title">
      <PageHeader id="support-contract-edit-title" title={`Edit ${query.data.code}`} />
      <SupportContractForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/vendor/support-contracts/${id}`)}
      />
    </section>
  );
}
