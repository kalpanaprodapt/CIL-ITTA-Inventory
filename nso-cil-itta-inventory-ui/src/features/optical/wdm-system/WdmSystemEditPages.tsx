import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { WdmSystemForm } from './WdmSystemForm';
import { useCreateWdmSystem, useUpdateWdmSystem, useWdmSystem } from './hooks';
import type { WdmSystemInput } from './types';

export function WdmSystemCreatePage() {
  const navigate = useNavigate();
  const create = useCreateWdmSystem();
  const [error, setError] = useState<string>();

  function onSubmit(input: WdmSystemInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/optical/wdm-systems/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="wdm-system-new-title">
      <PageHeader id="wdm-system-new-title" title="New Wdm System" />
      <WdmSystemForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/optical/wdm-systems')}
      />
    </section>
  );
}

export function WdmSystemEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useWdmSystem(id);
  const update = useUpdateWdmSystem(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Wdm System" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Wdm System." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: WdmSystemInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/optical/wdm-systems/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="wdm-system-edit-title">
      <PageHeader id="wdm-system-edit-title" title={`Edit ${query.data.code}`} />
      <WdmSystemForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/optical/wdm-systems/${id}`)}
      />
    </section>
  );
}
