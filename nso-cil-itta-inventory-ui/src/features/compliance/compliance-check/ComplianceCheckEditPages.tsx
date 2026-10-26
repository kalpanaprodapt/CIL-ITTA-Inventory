import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ComplianceCheckForm } from './ComplianceCheckForm';
import { useCreateComplianceCheck, useUpdateComplianceCheck, useComplianceCheck } from './hooks';
import type { ComplianceCheckInput } from './types';

export function ComplianceCheckCreatePage() {
  const navigate = useNavigate();
  const create = useCreateComplianceCheck();
  const [error, setError] = useState<string>();

  function onSubmit(input: ComplianceCheckInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/compliance/compliance-checks/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="compliance-check-new-title">
      <PageHeader id="compliance-check-new-title" title="New Compliance Check" />
      <ComplianceCheckForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/compliance/compliance-checks')}
      />
    </section>
  );
}

export function ComplianceCheckEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useComplianceCheck(id);
  const update = useUpdateComplianceCheck(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Compliance Check" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Compliance Check." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ComplianceCheckInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/compliance/compliance-checks/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="compliance-check-edit-title">
      <PageHeader id="compliance-check-edit-title" title={`Edit ${query.data.code}`} />
      <ComplianceCheckForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/compliance/compliance-checks/${id}`)}
      />
    </section>
  );
}
