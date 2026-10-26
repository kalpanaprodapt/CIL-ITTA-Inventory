import type { LifecycleStatus } from '@/shared/types';
import type { WebhookSubscriptionFilter, WebhookSubscriptionLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toWebhookSubscriptionQuery(filter: WebhookSubscriptionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.callbackUrl) query.callbackUrl = filter.callbackUrl;
  if (filter.eventTypes) query.eventTypes = filter.eventTypes;
  if (filter.secretRef) query.secretRef = filter.secretRef;
  if (filter.active !== undefined) query.active = filter.active;
  if (filter.retryLimitMin !== undefined) query.retryLimitMin = filter.retryLimitMin;
  if (filter.retryLimitMax !== undefined) query.retryLimitMax = filter.retryLimitMax;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseWebhookSubscriptionFilter(params: URLSearchParams): WebhookSubscriptionFilter {
  const filter: WebhookSubscriptionFilter = {};
  const q = params.get('q');
  if (q) filter.q = q;
  const status = params.get('status');
  if (status) filter.status = status.split(',') as LifecycleStatus[];
  const code = params.get('code');
  if (code) filter.code = code;
  const name = params.get('name');
  if (name) filter.name = name;
  const externalRef = params.get('externalRef');
  if (externalRef) filter.externalRef = externalRef;
  const callbackUrl = params.get('callbackUrl');
  if (callbackUrl) filter.callbackUrl = callbackUrl;
  const eventTypes = params.get('eventTypes');
  if (eventTypes) filter.eventTypes = eventTypes;
  const secretRef = params.get('secretRef');
  if (secretRef) filter.secretRef = secretRef;
  const active = params.get('active');
  if (active !== null) filter.active = active === 'true';
  const retryLimitMin = params.get('retryLimitMin');
  if (retryLimitMin !== null && retryLimitMin !== '') filter.retryLimitMin = Number(retryLimitMin);
  const retryLimitMax = params.get('retryLimitMax');
  if (retryLimitMax !== null && retryLimitMax !== '') filter.retryLimitMax = Number(retryLimitMax);
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as WebhookSubscriptionLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeWebhookSubscriptionFilter(filter: WebhookSubscriptionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.callbackUrl) params.set('callbackUrl', filter.callbackUrl);
  if (filter.eventTypes) params.set('eventTypes', filter.eventTypes);
  if (filter.secretRef) params.set('secretRef', filter.secretRef);
  if (filter.active !== undefined) params.set('active', String(filter.active));
  if (filter.retryLimitMin !== undefined) params.set('retryLimitMin', String(filter.retryLimitMin));
  if (filter.retryLimitMax !== undefined) params.set('retryLimitMax', String(filter.retryLimitMax));
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countWebhookSubscriptionActiveFilters(filter: WebhookSubscriptionFilter): number {
  return Array.from(serializeWebhookSubscriptionFilter(filter).keys()).length;
}
