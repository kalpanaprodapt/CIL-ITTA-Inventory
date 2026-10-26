import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { FanTrayForm } from './FanTrayForm';
import { useCreateFanTray, useUpdateFanTray, useFanTray } from './hooks';
import type { FanTrayInput } from './types';

export function FanTrayCreatePage() {
  const navigate = useNavigate();
  const create = useCreateFanTray();
  const [error, setError] = useState<string>();

  function onSubmit(input: FanTrayInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/device/fan-traies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="fan-tray-new-title">
      <PageHeader id="fan-tray-new-title" title="New Fan Tray" />
      <FanTrayForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/device/fan-traies')}
      />
    </section>
  );
}

export function FanTrayEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useFanTray(id);
  const update = useUpdateFanTray(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Fan Tray" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Fan Tray." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: FanTrayInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/device/fan-traies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="fan-tray-edit-title">
      <PageHeader id="fan-tray-edit-title" title={`Edit ${query.data.code}`} />
      <FanTrayForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/device/fan-traies/${id}`)}
      />
    </section>
  );
}
