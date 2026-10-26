import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AlarmInstanceForm } from './AlarmInstanceForm';
import { useCreateAlarmInstance, useUpdateAlarmInstance, useAlarmInstance } from './hooks';
import type { AlarmInstanceInput } from './types';

export function AlarmInstanceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAlarmInstance();
  const [error, setError] = useState<string>();

  function onSubmit(input: AlarmInstanceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/alarm/alarm-instances/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="alarm-instance-new-title">
      <PageHeader id="alarm-instance-new-title" title="New Alarm Instance" />
      <AlarmInstanceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/alarm/alarm-instances')}
      />
    </section>
  );
}

export function AlarmInstanceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAlarmInstance(id);
  const update = useUpdateAlarmInstance(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Alarm Instance" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Alarm Instance." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AlarmInstanceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/alarm/alarm-instances/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="alarm-instance-edit-title">
      <PageHeader id="alarm-instance-edit-title" title={`Edit ${query.data.code}`} />
      <AlarmInstanceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/alarm/alarm-instances/${id}`)}
      />
    </section>
  );
}
