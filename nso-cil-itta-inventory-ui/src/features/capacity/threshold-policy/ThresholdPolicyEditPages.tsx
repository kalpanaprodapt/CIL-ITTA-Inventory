import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ThresholdPolicyForm } from './ThresholdPolicyForm';
import { useCreateThresholdPolicy, useUpdateThresholdPolicy, useThresholdPolicy } from './hooks';
import type { ThresholdPolicyInput } from './types';

export function ThresholdPolicyCreatePage() {
  const navigate = useNavigate();
  const create = useCreateThresholdPolicy();
  const [error, setError] = useState<string>();

  function onSubmit(input: ThresholdPolicyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/capacity/threshold-policies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="threshold-policy-new-title">
      <PageHeader id="threshold-policy-new-title" title="New Threshold Policy" />
      <ThresholdPolicyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/capacity/threshold-policies')}
      />
    </section>
  );
}

export function ThresholdPolicyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useThresholdPolicy(id);
  const update = useUpdateThresholdPolicy(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Threshold Policy" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Threshold Policy." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ThresholdPolicyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/capacity/threshold-policies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="threshold-policy-edit-title">
      <PageHeader id="threshold-policy-edit-title" title={`Edit ${query.data.code}`} />
      <ThresholdPolicyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/capacity/threshold-policies/${id}`)}
      />
    </section>
  );
}
