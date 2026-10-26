import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PowerSupplyForm } from './PowerSupplyForm';
import { useCreatePowerSupply, useUpdatePowerSupply, usePowerSupply } from './hooks';
import type { PowerSupplyInput } from './types';

export function PowerSupplyCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePowerSupply();
  const [error, setError] = useState<string>();

  function onSubmit(input: PowerSupplyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/device/power-supplies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="power-supply-new-title">
      <PageHeader id="power-supply-new-title" title="New Power Supply" />
      <PowerSupplyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/device/power-supplies')}
      />
    </section>
  );
}

export function PowerSupplyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePowerSupply(id);
  const update = useUpdatePowerSupply(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Power Supply" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Power Supply." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PowerSupplyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/device/power-supplies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="power-supply-edit-title">
      <PageHeader id="power-supply-edit-title" title={`Edit ${query.data.code}`} />
      <PowerSupplyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/device/power-supplies/${id}`)}
      />
    </section>
  );
}
