import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { FieldTaskForm } from './FieldTaskForm';
import { useCreateFieldTask, useUpdateFieldTask, useFieldTask } from './hooks';
import type { FieldTaskInput } from './types';

export function FieldTaskCreatePage() {
  const navigate = useNavigate();
  const create = useCreateFieldTask();
  const [error, setError] = useState<string>();

  function onSubmit(input: FieldTaskInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/change/field-tasks/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="field-task-new-title">
      <PageHeader id="field-task-new-title" title="New Field Task" />
      <FieldTaskForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/change/field-tasks')}
      />
    </section>
  );
}

export function FieldTaskEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useFieldTask(id);
  const update = useUpdateFieldTask(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Field Task" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Field Task." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: FieldTaskInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/change/field-tasks/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="field-task-edit-title">
      <PageHeader id="field-task-edit-title" title={`Edit ${query.data.code}`} />
      <FieldTaskForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/change/field-tasks/${id}`)}
      />
    </section>
  );
}
