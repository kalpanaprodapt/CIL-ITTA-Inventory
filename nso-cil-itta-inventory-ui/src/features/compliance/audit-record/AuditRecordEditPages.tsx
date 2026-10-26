import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AuditRecordForm } from './AuditRecordForm';
import { useCreateAuditRecord, useUpdateAuditRecord, useAuditRecord } from './hooks';
import type { AuditRecordInput } from './types';

export function AuditRecordCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAuditRecord();
  const [error, setError] = useState<string>();

  function onSubmit(input: AuditRecordInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/compliance/audit-records/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="audit-record-new-title">
      <PageHeader id="audit-record-new-title" title="New Audit Record" />
      <AuditRecordForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/compliance/audit-records')}
      />
    </section>
  );
}

export function AuditRecordEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAuditRecord(id);
  const update = useUpdateAuditRecord(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Audit Record" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Audit Record." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AuditRecordInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/compliance/audit-records/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="audit-record-edit-title">
      <PageHeader id="audit-record-edit-title" title={`Edit ${query.data.code}`} />
      <AuditRecordForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/compliance/audit-records/${id}`)}
      />
    </section>
  );
}
