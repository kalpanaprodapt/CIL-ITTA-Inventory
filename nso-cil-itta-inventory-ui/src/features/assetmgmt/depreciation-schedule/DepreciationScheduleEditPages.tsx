import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { DepreciationScheduleForm } from './DepreciationScheduleForm';
import { useCreateDepreciationSchedule, useUpdateDepreciationSchedule, useDepreciationSchedule } from './hooks';
import type { DepreciationScheduleInput } from './types';

export function DepreciationScheduleCreatePage() {
  const navigate = useNavigate();
  const create = useCreateDepreciationSchedule();
  const [error, setError] = useState<string>();

  function onSubmit(input: DepreciationScheduleInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/assetmgmt/depreciation-schedules/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="depreciation-schedule-new-title">
      <PageHeader id="depreciation-schedule-new-title" title="New Depreciation Schedule" />
      <DepreciationScheduleForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/assetmgmt/depreciation-schedules')}
      />
    </section>
  );
}

export function DepreciationScheduleEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDepreciationSchedule(id);
  const update = useUpdateDepreciationSchedule(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Depreciation Schedule" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Depreciation Schedule." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: DepreciationScheduleInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/assetmgmt/depreciation-schedules/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="depreciation-schedule-edit-title">
      <PageHeader id="depreciation-schedule-edit-title" title={`Edit ${query.data.code}`} />
      <DepreciationScheduleForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/assetmgmt/depreciation-schedules/${id}`)}
      />
    </section>
  );
}
