import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { NetworkDeviceForm } from './NetworkDeviceForm';
import { useCreateNetworkDevice, useUpdateNetworkDevice, useNetworkDevice } from './hooks';
import type { NetworkDeviceInput } from './types';

export function NetworkDeviceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateNetworkDevice();
  const [error, setError] = useState<string>();

  function onSubmit(input: NetworkDeviceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/device/network-devices/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="network-device-new-title">
      <PageHeader id="network-device-new-title" title="New Network Device" />
      <NetworkDeviceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/device/network-devices')}
      />
    </section>
  );
}

export function NetworkDeviceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useNetworkDevice(id);
  const update = useUpdateNetworkDevice(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Network Device" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Network Device." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: NetworkDeviceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/device/network-devices/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="network-device-edit-title">
      <PageHeader id="network-device-edit-title" title={`Edit ${query.data.code}`} />
      <NetworkDeviceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/device/network-devices/${id}`)}
      />
    </section>
  );
}
