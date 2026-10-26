import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { QosPolicyForm } from './QosPolicyForm';
import { useCreateQosPolicy, useUpdateQosPolicy, useQosPolicy } from './hooks';
import type { QosPolicyInput } from './types';

export function QosPolicyCreatePage() {
  const navigate = useNavigate();
  const create = useCreateQosPolicy();
  const [error, setError] = useState<string>();

  function onSubmit(input: QosPolicyInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/mpls/qos-policies/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="qos-policy-new-title">
      <PageHeader id="qos-policy-new-title" title="New Qos Policy" />
      <QosPolicyForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/mpls/qos-policies')}
      />
    </section>
  );
}

export function QosPolicyEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useQosPolicy(id);
  const update = useUpdateQosPolicy(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Qos Policy" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Qos Policy." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: QosPolicyInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/mpls/qos-policies/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="qos-policy-edit-title">
      <PageHeader id="qos-policy-edit-title" title={`Edit ${query.data.code}`} />
      <QosPolicyForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/mpls/qos-policies/${id}`)}
      />
    </section>
  );
}
