import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SuppressionRuleForm } from './SuppressionRuleForm';
import { useCreateSuppressionRule, useUpdateSuppressionRule, useSuppressionRule } from './hooks';
import type { SuppressionRuleInput } from './types';

export function SuppressionRuleCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSuppressionRule();
  const [error, setError] = useState<string>();

  function onSubmit(input: SuppressionRuleInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/alarm/suppression-rules/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="suppression-rule-new-title">
      <PageHeader id="suppression-rule-new-title" title="New Suppression Rule" />
      <SuppressionRuleForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/alarm/suppression-rules')}
      />
    </section>
  );
}

export function SuppressionRuleEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSuppressionRule(id);
  const update = useUpdateSuppressionRule(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Suppression Rule" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Suppression Rule." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SuppressionRuleInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/alarm/suppression-rules/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="suppression-rule-edit-title">
      <PageHeader id="suppression-rule-edit-title" title={`Edit ${query.data.code}`} />
      <SuppressionRuleForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/alarm/suppression-rules/${id}`)}
      />
    </section>
  );
}
