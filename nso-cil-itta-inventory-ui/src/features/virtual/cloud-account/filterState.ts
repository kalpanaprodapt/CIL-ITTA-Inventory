import type { LifecycleStatus } from '@/shared/types';
import type { CloudAccountFilter, CloudAccountProvider, CloudAccountLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCloudAccountQuery(filter: CloudAccountFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.provider?.length) query.provider = filter.provider.join(',');
  if (filter.accountRef) query.accountRef = filter.accountRef;
  if (filter.subscriptionName) query.subscriptionName = filter.subscriptionName;
  if (filter.monthlyBudgetMin !== undefined) query.monthlyBudgetMin = filter.monthlyBudgetMin;
  if (filter.monthlyBudgetMax !== undefined) query.monthlyBudgetMax = filter.monthlyBudgetMax;
  if (filter.environment) query.environment = filter.environment;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseCloudAccountFilter(params: URLSearchParams): CloudAccountFilter {
  const filter: CloudAccountFilter = {};
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
  const provider = params.get('provider');
  if (provider) filter.provider = provider.split(',') as CloudAccountProvider[];
  const accountRef = params.get('accountRef');
  if (accountRef) filter.accountRef = accountRef;
  const subscriptionName = params.get('subscriptionName');
  if (subscriptionName) filter.subscriptionName = subscriptionName;
  const monthlyBudgetMin = params.get('monthlyBudgetMin');
  if (monthlyBudgetMin !== null && monthlyBudgetMin !== '') filter.monthlyBudgetMin = Number(monthlyBudgetMin);
  const monthlyBudgetMax = params.get('monthlyBudgetMax');
  if (monthlyBudgetMax !== null && monthlyBudgetMax !== '') filter.monthlyBudgetMax = Number(monthlyBudgetMax);
  const environment = params.get('environment');
  if (environment) filter.environment = environment;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CloudAccountLifecycleStage[];
  return filter;
}

export function serializeCloudAccountFilter(filter: CloudAccountFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.provider?.length) params.set('provider', filter.provider.join(','));
  if (filter.accountRef) params.set('accountRef', filter.accountRef);
  if (filter.subscriptionName) params.set('subscriptionName', filter.subscriptionName);
  if (filter.monthlyBudgetMin !== undefined) params.set('monthlyBudgetMin', String(filter.monthlyBudgetMin));
  if (filter.monthlyBudgetMax !== undefined) params.set('monthlyBudgetMax', String(filter.monthlyBudgetMax));
  if (filter.environment) params.set('environment', filter.environment);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countCloudAccountActiveFilters(filter: CloudAccountFilter): number {
  return Array.from(serializeCloudAccountFilter(filter).keys()).length;
}
