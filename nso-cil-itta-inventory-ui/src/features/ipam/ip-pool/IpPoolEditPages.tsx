import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { IpPoolForm } from './IpPoolForm';
import { useCreateIpPool, useUpdateIpPool, useIpPool } from './hooks';
import type { IpPoolInput } from './types';

export function IpPoolCreatePage() {
  const navigate = useNavigate();
  const create = useCreateIpPool();
  const [error, setError] = useState<string>();

  function onSubmit(input: IpPoolInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/ipam/ip-pools/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="ip-pool-new-title">
      <PageHeader id="ip-pool-new-title" title="New Ip Pool" />
      <IpPoolForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/ipam/ip-pools')}
      />
    </section>
  );
}

export function IpPoolEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIpPool(id);
  const update = useUpdateIpPool(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Ip Pool" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ip Pool." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: IpPoolInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/ipam/ip-pools/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="ip-pool-edit-title">
      <PageHeader id="ip-pool-edit-title" title={`Edit ${query.data.code}`} />
      <IpPoolForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/ipam/ip-pools/${id}`)}
      />
    </section>
  );
}
