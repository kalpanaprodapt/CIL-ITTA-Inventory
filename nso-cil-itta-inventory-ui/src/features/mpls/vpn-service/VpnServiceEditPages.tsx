import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { VpnServiceForm } from './VpnServiceForm';
import { useCreateVpnService, useUpdateVpnService, useVpnService } from './hooks';
import type { VpnServiceInput } from './types';

export function VpnServiceCreatePage() {
  const navigate = useNavigate();
  const create = useCreateVpnService();
  const [error, setError] = useState<string>();

  function onSubmit(input: VpnServiceInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/mpls/vpn-services/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="vpn-service-new-title">
      <PageHeader id="vpn-service-new-title" title="New Vpn Service" />
      <VpnServiceForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/mpls/vpn-services')}
      />
    </section>
  );
}

export function VpnServiceEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useVpnService(id);
  const update = useUpdateVpnService(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Vpn Service" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Vpn Service." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: VpnServiceInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/mpls/vpn-services/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="vpn-service-edit-title">
      <PageHeader id="vpn-service-edit-title" title={`Edit ${query.data.code}`} />
      <VpnServiceForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/mpls/vpn-services/${id}`)}
      />
    </section>
  );
}
