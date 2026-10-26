import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { AmplifierForm } from './AmplifierForm';
import { useCreateAmplifier, useUpdateAmplifier, useAmplifier } from './hooks';
import type { AmplifierInput } from './types';

export function AmplifierCreatePage() {
  const navigate = useNavigate();
  const create = useCreateAmplifier();
  const [error, setError] = useState<string>();

  function onSubmit(input: AmplifierInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/optical/amplifiers/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="amplifier-new-title">
      <PageHeader id="amplifier-new-title" title="New Amplifier" />
      <AmplifierForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/optical/amplifiers')}
      />
    </section>
  );
}

export function AmplifierEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useAmplifier(id);
  const update = useUpdateAmplifier(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Amplifier" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Amplifier." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: AmplifierInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/optical/amplifiers/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="amplifier-edit-title">
      <PageHeader id="amplifier-edit-title" title={`Edit ${query.data.code}`} />
      <AmplifierForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/optical/amplifiers/${id}`)}
      />
    </section>
  );
}
