import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ComplianceRuleForm } from './ComplianceRuleForm';
import { useCreateComplianceRule, useUpdateComplianceRule, useComplianceRule } from './hooks';
import type { ComplianceRuleInput } from './types';

export function ComplianceRuleCreatePage() {
  const navigate = useNavigate();
  const create = useCreateComplianceRule();
  const [error, setError] = useState<string>();

  function onSubmit(input: ComplianceRuleInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/compliance/compliance-rules/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="compliance-rule-new-title">
      <PageHeader id="compliance-rule-new-title" title="New Compliance Rule" />
      <ComplianceRuleForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/compliance/compliance-rules')}
      />
    </section>
  );
}

export function ComplianceRuleEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useComplianceRule(id);
  const update = useUpdateComplianceRule(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Compliance Rule" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Compliance Rule." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ComplianceRuleInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/compliance/compliance-rules/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="compliance-rule-edit-title">
      <PageHeader id="compliance-rule-edit-title" title={`Edit ${query.data.code}`} />
      <ComplianceRuleForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/compliance/compliance-rules/${id}`)}
      />
    </section>
  );
}
