import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ReconciliationRuleForm } from './ReconciliationRuleForm';
import { useCreateReconciliationRule, useUpdateReconciliationRule, useReconciliationRule } from './hooks';
import type { ReconciliationRuleInput } from './types';

export function ReconciliationRuleCreatePage() {
  const navigate = useNavigate();
  const create = useCreateReconciliationRule();
  const [error, setError] = useState<string>();

  function onSubmit(input: ReconciliationRuleInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/discovery/reconciliation-rules/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="reconciliation-rule-new-title">
      <PageHeader id="reconciliation-rule-new-title" title="New Reconciliation Rule" />
      <ReconciliationRuleForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/discovery/reconciliation-rules')}
      />
    </section>
  );
}

export function ReconciliationRuleEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useReconciliationRule(id);
  const update = useUpdateReconciliationRule(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Reconciliation Rule" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Reconciliation Rule." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ReconciliationRuleInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/discovery/reconciliation-rules/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="reconciliation-rule-edit-title">
      <PageHeader id="reconciliation-rule-edit-title" title={`Edit ${query.data.code}`} />
      <ReconciliationRuleForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/discovery/reconciliation-rules/${id}`)}
      />
    </section>
  );
}
