import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ContractForm } from './ContractForm';
import { useCreateContract, useUpdateContract, useContract } from './hooks';
import type { ContractInput } from './types';

export function ContractCreatePage() {
  const navigate = useNavigate();
  const create = useCreateContract();
  const [error, setError] = useState<string>();

  function onSubmit(input: ContractInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/customer/contracts/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="contract-new-title">
      <PageHeader id="contract-new-title" title="New Contract" />
      <ContractForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/customer/contracts')}
      />
    </section>
  );
}

export function ContractEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useContract(id);
  const update = useUpdateContract(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Contract" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Contract." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ContractInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/customer/contracts/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="contract-edit-title">
      <PageHeader id="contract-edit-title" title={`Edit ${query.data.code}`} />
      <ContractForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/customer/contracts/${id}`)}
      />
    </section>
  );
}
