import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { MaintenanceWindowForm } from './MaintenanceWindowForm';
import { useCreateMaintenanceWindow, useUpdateMaintenanceWindow, useMaintenanceWindow } from './hooks';
import type { MaintenanceWindowInput } from './types';

export function MaintenanceWindowCreatePage() {
  const navigate = useNavigate();
  const create = useCreateMaintenanceWindow();
  const [error, setError] = useState<string>();

  function onSubmit(input: MaintenanceWindowInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/change/maintenance-windows/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="maintenance-window-new-title">
      <PageHeader id="maintenance-window-new-title" title="New Maintenance Window" />
      <MaintenanceWindowForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/change/maintenance-windows')}
      />
    </section>
  );
}

export function MaintenanceWindowEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useMaintenanceWindow(id);
  const update = useUpdateMaintenanceWindow(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Maintenance Window" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Maintenance Window." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: MaintenanceWindowInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/change/maintenance-windows/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="maintenance-window-edit-title">
      <PageHeader id="maintenance-window-edit-title" title={`Edit ${query.data.code}`} />
      <MaintenanceWindowForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/change/maintenance-windows/${id}`)}
      />
    </section>
  );
}
