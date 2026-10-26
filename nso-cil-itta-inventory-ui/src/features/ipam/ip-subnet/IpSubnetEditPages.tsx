import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { IpSubnetForm } from './IpSubnetForm';
import { useCreateIpSubnet, useUpdateIpSubnet, useIpSubnet } from './hooks';
import type { IpSubnetInput } from './types';

export function IpSubnetCreatePage() {
  const navigate = useNavigate();
  const create = useCreateIpSubnet();
  const [error, setError] = useState<string>();

  function onSubmit(input: IpSubnetInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/ipam/ip-subnets/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="ip-subnet-new-title">
      <PageHeader id="ip-subnet-new-title" title="New Ip Subnet" />
      <IpSubnetForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/ipam/ip-subnets')}
      />
    </section>
  );
}

export function IpSubnetEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useIpSubnet(id);
  const update = useUpdateIpSubnet(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Ip Subnet" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ip Subnet." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: IpSubnetInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/ipam/ip-subnets/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="ip-subnet-edit-title">
      <PageHeader id="ip-subnet-edit-title" title={`Edit ${query.data.code}`} />
      <IpSubnetForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/ipam/ip-subnets/${id}`)}
      />
    </section>
  );
}
