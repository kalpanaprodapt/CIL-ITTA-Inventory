import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SoftwareVersionForm } from './SoftwareVersionForm';
import { useCreateSoftwareVersion, useUpdateSoftwareVersion, useSoftwareVersion } from './hooks';
import type { SoftwareVersionInput } from './types';

export function SoftwareVersionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSoftwareVersion();
  const [error, setError] = useState<string>();

  function onSubmit(input: SoftwareVersionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/software/software-versions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="software-version-new-title">
      <PageHeader id="software-version-new-title" title="New Software Version" />
      <SoftwareVersionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/software/software-versions')}
      />
    </section>
  );
}

export function SoftwareVersionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSoftwareVersion(id);
  const update = useUpdateSoftwareVersion(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Software Version" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Software Version." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SoftwareVersionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/software/software-versions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="software-version-edit-title">
      <PageHeader id="software-version-edit-title" title={`Edit ${query.data.code}`} />
      <SoftwareVersionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/software/software-versions/${id}`)}
      />
    </section>
  );
}
