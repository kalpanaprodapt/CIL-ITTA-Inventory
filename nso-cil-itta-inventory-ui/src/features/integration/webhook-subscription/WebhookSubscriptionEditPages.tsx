import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { WebhookSubscriptionForm } from './WebhookSubscriptionForm';
import { useCreateWebhookSubscription, useUpdateWebhookSubscription, useWebhookSubscription } from './hooks';
import type { WebhookSubscriptionInput } from './types';

export function WebhookSubscriptionCreatePage() {
  const navigate = useNavigate();
  const create = useCreateWebhookSubscription();
  const [error, setError] = useState<string>();

  function onSubmit(input: WebhookSubscriptionInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/integration/webhook-subscriptions/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="webhook-subscription-new-title">
      <PageHeader id="webhook-subscription-new-title" title="New Webhook Subscription" />
      <WebhookSubscriptionForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/integration/webhook-subscriptions')}
      />
    </section>
  );
}

export function WebhookSubscriptionEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useWebhookSubscription(id);
  const update = useUpdateWebhookSubscription(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Webhook Subscription" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Webhook Subscription." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: WebhookSubscriptionInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/integration/webhook-subscriptions/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="webhook-subscription-edit-title">
      <PageHeader id="webhook-subscription-edit-title" title={`Edit ${query.data.code}`} />
      <WebhookSubscriptionForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/integration/webhook-subscriptions/${id}`)}
      />
    </section>
  );
}
