import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ComplianceExceptionForm } from './ComplianceExceptionForm';
import { useCreateComplianceException, useUpdateComplianceException, useComplianceException } from './hooks';
import type { ComplianceExceptionInput } from './types';

export function ComplianceExceptionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateComplianceException();
  const [error, setError] = useState<string>();

  function onSubmit(input: ComplianceExceptionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/compliance/compliance-exceptions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="compliance-exception-new-title">
      <PageHeader id="compliance-exception-new-title" title="New Compliance Exception" />
      <ComplianceExceptionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/compliance/compliance-exceptions')}
      />
    </section>
  );
}

export function ComplianceExceptionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useComplianceException(id);
  const update = useUpdateComplianceException(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Compliance Exception" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Compliance Exception." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ComplianceExceptionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/compliance/compliance-exceptions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="compliance-exception-edit-title">
      <PageHeader id="compliance-exception-edit-title" title={`Edit ${query.data.code}`} />
      <ComplianceExceptionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/compliance/compliance-exceptions/${id}`)}
      />
    </section>
  );
}
