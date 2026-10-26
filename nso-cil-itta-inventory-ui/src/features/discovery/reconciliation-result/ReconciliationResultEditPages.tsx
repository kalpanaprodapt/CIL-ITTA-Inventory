import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ReconciliationResultForm } from './ReconciliationResultForm';
import { useCreateReconciliationResult, useUpdateReconciliationResult, useReconciliationResult } from './hooks';
import type { ReconciliationResultInput } from './types';

export function ReconciliationResultCreatePage() {
  const navigate = useNavigate();
  const create = useCreateReconciliationResult();
  const [error, setError] = useState<string>();

  function onSubmit(input: ReconciliationResultInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/discovery/reconciliation-results/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="reconciliation-result-new-title">
      <PageHeader id="reconciliation-result-new-title" title="New Reconciliation Result" />
      <ReconciliationResultForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/discovery/reconciliation-results')}
      />
    </section>
  );
}

export function ReconciliationResultEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useReconciliationResult(id);
  const update = useUpdateReconciliationResult(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Reconciliation Result" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Reconciliation Result." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ReconciliationResultInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/discovery/reconciliation-results/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="reconciliation-result-edit-title">
      <PageHeader id="reconciliation-result-edit-title" title={`Edit ${query.data.code}`} />
      <ReconciliationResultForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/discovery/reconciliation-results/${id}`)}
      />
    </section>
  );
}
