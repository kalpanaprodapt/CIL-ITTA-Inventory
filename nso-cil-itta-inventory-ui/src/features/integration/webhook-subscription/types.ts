import type { LifecycleStatus, SortState } from '@/shared/types';

export const WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type WebhookSubscriptionLifecycleStage = (typeof WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_VALUES)[number];
export const WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_LABELS: Record<WebhookSubscriptionLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_OPTIONS = WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_LABELS[value] }));

export interface WebhookSubscription {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  callbackUrl: string;
  eventTypes: string;
  secretRef?: string;
  active?: boolean;
  retryLimit?: number;
  labels?: string;
  lifecycleStage?: WebhookSubscriptionLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface WebhookSubscriptionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  callbackUrl: string;
  eventTypes: string;
  secretRef?: string;
  active?: boolean;
  retryLimit?: number;
  labels?: string;
  lifecycleStage?: WebhookSubscriptionLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type WebhookSubscriptionFormValues = Partial<WebhookSubscriptionInput>;

export interface WebhookSubscriptionUpdateInput extends Partial<WebhookSubscriptionInput> {
  expectedVersion?: number;
}

export interface WebhookSubscriptionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  callbackUrl?: string;
  eventTypes?: string;
  secretRef?: string;
  active?: boolean;
  retryLimitMin?: number;
  retryLimitMax?: number;
  labels?: string;
  lifecycleStage?: WebhookSubscriptionLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type WebhookSubscriptionSort = SortState<keyof WebhookSubscription & string>;

export interface WebhookSubscriptionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const WEBHOOKSUBSCRIPTION_PATH = '/api/v1/webhook-subscriptions';
export const WEBHOOKSUBSCRIPTION_TITLE = 'Webhook Subscription';
export const WEBHOOKSUBSCRIPTION_TITLE_PLURAL = 'Webhook Subscriptions';

export const WEBHOOKSUBSCRIPTION_DEFAULTS: WebhookSubscriptionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  callbackUrl: '',
  eventTypes: '',
  active: false,
};
