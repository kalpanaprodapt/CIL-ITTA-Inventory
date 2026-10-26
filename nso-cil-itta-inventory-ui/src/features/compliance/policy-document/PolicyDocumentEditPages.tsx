import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PolicyDocumentForm } from './PolicyDocumentForm';
import { useCreatePolicyDocument, useUpdatePolicyDocument, usePolicyDocument } from './hooks';
import type { PolicyDocumentInput } from './types';

export function PolicyDocumentCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePolicyDocument();
  const [error, setError] = useState<string>();

  function onSubmit(input: PolicyDocumentInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/compliance/policy-documents/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="policy-document-new-title">
      <PageHeader id="policy-document-new-title" title="New Policy Document" />
      <PolicyDocumentForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/compliance/policy-documents')}
      />
    </section>
  );
}

export function PolicyDocumentEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePolicyDocument(id);
  const update = useUpdatePolicyDocument(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Policy Document" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Policy Document." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PolicyDocumentInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/compliance/policy-documents/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="policy-document-edit-title">
      <PageHeader id="policy-document-edit-title" title={`Edit ${query.data.code}`} />
      <PolicyDocumentForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/compliance/policy-documents/${id}`)}
      />
    </section>
  );
}
