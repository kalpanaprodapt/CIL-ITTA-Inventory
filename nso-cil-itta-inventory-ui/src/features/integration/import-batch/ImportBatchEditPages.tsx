import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ImportBatchForm } from './ImportBatchForm';
import { useCreateImportBatch, useUpdateImportBatch, useImportBatch } from './hooks';
import type { ImportBatchInput } from './types';

export function ImportBatchCreatePage() {
  const navigate = useNavigate();
  const create = useCreateImportBatch();
  const [error, setError] = useState<string>();

  function onSubmit(input: ImportBatchInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/integration/import-batchs/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="import-batch-new-title">
      <PageHeader id="import-batch-new-title" title="New Import Batch" />
      <ImportBatchForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/integration/import-batchs')}
      />
    </section>
  );
}

export function ImportBatchEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useImportBatch(id);
  const update = useUpdateImportBatch(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Import Batch" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Import Batch." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ImportBatchInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/integration/import-batchs/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="import-batch-edit-title">
      <PageHeader id="import-batch-edit-title" title={`Edit ${query.data.code}`} />
      <ImportBatchForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/integration/import-batchs/${id}`)}
      />
    </section>
  );
}
