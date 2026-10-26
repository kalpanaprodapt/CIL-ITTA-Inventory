import type { LifecycleStatus } from '@/shared/types';
import type { CustomerFilter, CustomerSegment } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCustomerQuery(filter: CustomerFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.legalName) query.legalName = filter.legalName;
  if (filter.segment?.length) query.segment = filter.segment.join(',');
  if (filter.taxId) query.taxId = filter.taxId;
  if (filter.industry) query.industry = filter.industry;
  if (filter.accountManager) query.accountManager = filter.accountManager;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseCustomerFilter(params: URLSearchParams): CustomerFilter {
  const filter: CustomerFilter = {};
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
  const legalName = params.get('legalName');
  if (legalName) filter.legalName = legalName;
  const segment = params.get('segment');
  if (segment) filter.segment = segment.split(',') as CustomerSegment[];
  const taxId = params.get('taxId');
  if (taxId) filter.taxId = taxId;
  const industry = params.get('industry');
  if (industry) filter.industry = industry;
  const accountManager = params.get('accountManager');
  if (accountManager) filter.accountManager = accountManager;
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
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
  return filter;
}

export function serializeCustomerFilter(filter: CustomerFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.legalName) params.set('legalName', filter.legalName);
  if (filter.segment?.length) params.set('segment', filter.segment.join(','));
  if (filter.taxId) params.set('taxId', filter.taxId);
  if (filter.industry) params.set('industry', filter.industry);
  if (filter.accountManager) params.set('accountManager', filter.accountManager);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countCustomerActiveFilters(filter: CustomerFilter): number {
  return Array.from(serializeCustomerFilter(filter).keys()).length;
}
