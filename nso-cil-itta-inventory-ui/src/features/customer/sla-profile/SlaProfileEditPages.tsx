import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SlaProfileForm } from './SlaProfileForm';
import { useCreateSlaProfile, useUpdateSlaProfile, useSlaProfile } from './hooks';
import type { SlaProfileInput } from './types';

export function SlaProfileCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSlaProfile();
  const [error, setError] = useState<string>();

  function onSubmit(input: SlaProfileInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/customer/sla-profiles/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="sla-profile-new-title">
      <PageHeader id="sla-profile-new-title" title="New Sla Profile" />
      <SlaProfileForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/customer/sla-profiles')}
      />
    </section>
  );
}

export function SlaProfileEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSlaProfile(id);
  const update = useUpdateSlaProfile(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Sla Profile" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Sla Profile." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SlaProfileInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/customer/sla-profiles/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="sla-profile-edit-title">
      <PageHeader id="sla-profile-edit-title" title={`Edit ${query.data.code}`} />
      <SlaProfileForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/customer/sla-profiles/${id}`)}
      />
    </section>
  );
}
