import type { LifecycleStatus } from '@/shared/types';
import type { WarrantyFilter, WarrantyWarrantyType } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toWarrantyQuery(filter: WarrantyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.assetId) query.assetId = filter.assetId;
  if (filter.startDateFrom) query.startDateFrom = filter.startDateFrom;
  if (filter.startDateTo) query.startDateTo = filter.startDateTo;
  if (filter.endDateFrom) query.endDateFrom = filter.endDateFrom;
  if (filter.endDateTo) query.endDateTo = filter.endDateTo;
  if (filter.warrantyType?.length) query.warrantyType = filter.warrantyType.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseWarrantyFilter(params: URLSearchParams): WarrantyFilter {
  const filter: WarrantyFilter = {};
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
  const assetId = params.get('assetId');
  if (assetId) filter.assetId = assetId;
  const startDateFrom = params.get('startDateFrom');
  if (startDateFrom) filter.startDateFrom = startDateFrom;
  const startDateTo = params.get('startDateTo');
  if (startDateTo) filter.startDateTo = startDateTo;
  const endDateFrom = params.get('endDateFrom');
  if (endDateFrom) filter.endDateFrom = endDateFrom;
  const endDateTo = params.get('endDateTo');
  if (endDateTo) filter.endDateTo = endDateTo;
  const warrantyType = params.get('warrantyType');
  if (warrantyType) filter.warrantyType = warrantyType.split(',') as WarrantyWarrantyType[];
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

export function serializeWarrantyFilter(filter: WarrantyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.assetId) params.set('assetId', filter.assetId);
  if (filter.startDateFrom) params.set('startDateFrom', filter.startDateFrom);
  if (filter.startDateTo) params.set('startDateTo', filter.startDateTo);
  if (filter.endDateFrom) params.set('endDateFrom', filter.endDateFrom);
  if (filter.endDateTo) params.set('endDateTo', filter.endDateTo);
  if (filter.warrantyType?.length) params.set('warrantyType', filter.warrantyType.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countWarrantyActiveFilters(filter: WarrantyFilter): number {
  return Array.from(serializeWarrantyFilter(filter).keys()).length;
}
