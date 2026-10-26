import type { WebhookSubscription, WebhookSubscriptionInput } from '../types';

export const webhookSubscriptionInput: WebhookSubscriptionInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  callbackUrl: 'callbackUrl-1',
  eventTypes: 'eventTypes-1',
  secretRef: 'secretRef-1',
  active: true,
  retryLimit: 11,
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'WEBHOO-0001',
  name: 'Webhook Subscription one',
  status: 'ACTIVE',
};

export const webhookSubscriptionFixture: WebhookSubscription = {
  ...webhookSubscriptionInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const webhookSubscriptionPage = {
  content: [webhookSubscriptionFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
