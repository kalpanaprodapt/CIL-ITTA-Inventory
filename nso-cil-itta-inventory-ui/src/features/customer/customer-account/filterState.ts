import type { LifecycleStatus } from '@/shared/types';
import type { CustomerAccountFilter, CustomerAccountBillingCycle, CustomerAccountLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCustomerAccountQuery(filter: CustomerAccountFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.accountNumber) query.accountNumber = filter.accountNumber;
  if (filter.billingCycle?.length) query.billingCycle = filter.billingCycle.join(',');
  if (filter.currency) query.currency = filter.currency;
  if (filter.creditLimitMin !== undefined) query.creditLimitMin = filter.creditLimitMin;
  if (filter.creditLimitMax !== undefined) query.creditLimitMax = filter.creditLimitMax;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseCustomerAccountFilter(params: URLSearchParams): CustomerAccountFilter {
  const filter: CustomerAccountFilter = {};
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
  const customerId = params.get('customerId');
  if (customerId) filter.customerId = customerId;
  const accountNumber = params.get('accountNumber');
  if (accountNumber) filter.accountNumber = accountNumber;
  const billingCycle = params.get('billingCycle');
  if (billingCycle) filter.billingCycle = billingCycle.split(',') as CustomerAccountBillingCycle[];
  const currency = params.get('currency');
  if (currency) filter.currency = currency;
  const creditLimitMin = params.get('creditLimitMin');
  if (creditLimitMin !== null && creditLimitMin !== '') filter.creditLimitMin = Number(creditLimitMin);
  const creditLimitMax = params.get('creditLimitMax');
  if (creditLimitMax !== null && creditLimitMax !== '') filter.creditLimitMax = Number(creditLimitMax);
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CustomerAccountLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeCustomerAccountFilter(filter: CustomerAccountFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.accountNumber) params.set('accountNumber', filter.accountNumber);
  if (filter.billingCycle?.length) params.set('billingCycle', filter.billingCycle.join(','));
  if (filter.currency) params.set('currency', filter.currency);
  if (filter.creditLimitMin !== undefined) params.set('creditLimitMin', String(filter.creditLimitMin));
  if (filter.creditLimitMax !== undefined) params.set('creditLimitMax', String(filter.creditLimitMax));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countCustomerAccountActiveFilters(filter: CustomerAccountFilter): number {
  return Array.from(serializeCustomerAccountFilter(filter).keys()).length;
}
