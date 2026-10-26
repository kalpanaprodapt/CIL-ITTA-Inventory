import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { CapacityPlanForm } from './CapacityPlanForm';
import { useCreateCapacityPlan, useUpdateCapacityPlan, useCapacityPlan } from './hooks';
import type { CapacityPlanInput } from './types';

export function CapacityPlanCreatePage() {
  const navigate = useNavigate();
  const create = useCreateCapacityPlan();
  const [error, setError] = useState<string>();

  function onSubmit(input: CapacityPlanInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/capacity/capacity-plans/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="capacity-plan-new-title">
      <PageHeader id="capacity-plan-new-title" title="New Capacity Plan" />
      <CapacityPlanForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/capacity/capacity-plans')}
      />
    </section>
  );
}

export function CapacityPlanEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useCapacityPlan(id);
  const update = useUpdateCapacityPlan(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Capacity Plan" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Capacity Plan." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: CapacityPlanInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/capacity/capacity-plans/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="capacity-plan-edit-title">
      <PageHeader id="capacity-plan-edit-title" title={`Edit ${query.data.code}`} />
      <CapacityPlanForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/capacity/capacity-plans/${id}`)}
      />
    </section>
  );
}
