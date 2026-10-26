import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AlarmDefinitionForm } from './AlarmDefinitionForm';
import { useCreateAlarmDefinition, useUpdateAlarmDefinition, useAlarmDefinition } from './hooks';
import type { AlarmDefinitionInput } from './types';

export function AlarmDefinitionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAlarmDefinition();
  const [error, setError] = useState<string>();

  function onSubmit(input: AlarmDefinitionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/alarm/alarm-definitions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="alarm-definition-new-title">
      <PageHeader id="alarm-definition-new-title" title="New Alarm Definition" />
      <AlarmDefinitionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/alarm/alarm-definitions')}
      />
    </section>
  );
}

export function AlarmDefinitionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAlarmDefinition(id);
  const update = useUpdateAlarmDefinition(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Alarm Definition" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Alarm Definition." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AlarmDefinitionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/alarm/alarm-definitions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="alarm-definition-edit-title">
      <PageHeader id="alarm-definition-edit-title" title={`Edit ${query.data.code}`} />
      <AlarmDefinitionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/alarm/alarm-definitions/${id}`)}
      />
    </section>
  );
}
