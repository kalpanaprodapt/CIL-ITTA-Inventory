import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { IpAddressForm } from './IpAddressForm';
import { useCreateIpAddress, useUpdateIpAddress, useIpAddress } from './hooks';
import type { IpAddressInput } from './types';

export function IpAddressCreatePage() {
  const navigate = useNavigate();
  const create = useCreateIpAddress();
  const [error, setError] = useState<string>();

  function onSubmit(input: IpAddressInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/ipam/ip-addresses/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="ip-address-new-title">
      <PageHeader id="ip-address-new-title" title="New Ip Address" />
      <IpAddressForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/ipam/ip-addresses')}
      />
    </section>
  );
}

export function IpAddressEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIpAddress(id);
  const update = useUpdateIpAddress(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Ip Address" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ip Address." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: IpAddressInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/ipam/ip-addresses/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="ip-address-edit-title">
      <PageHeader id="ip-address-edit-title" title={`Edit ${query.data.code}`} />
      <IpAddressForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/ipam/ip-addresses/${id}`)}
      />
    </section>
  );
}
