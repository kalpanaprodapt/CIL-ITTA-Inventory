import type { LifecycleStatus } from '@/shared/types';
import type { ServiceOrderFilter, ServiceOrderOrderType, ServiceOrderCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toServiceOrderQuery(filter: ServiceOrderFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.orderType?.length) query.orderType = filter.orderType.join(',');
  if (filter.requestedOnFrom) query.requestedOnFrom = filter.requestedOnFrom;
  if (filter.requestedOnTo) query.requestedOnTo = filter.requestedOnTo;
  if (filter.targetDateFrom) query.targetDateFrom = filter.targetDateFrom;
  if (filter.targetDateTo) query.targetDateTo = filter.targetDateTo;
  if (filter.priorityMin !== undefined) query.priorityMin = filter.priorityMin;
  if (filter.priorityMax !== undefined) query.priorityMax = filter.priorityMax;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseServiceOrderFilter(params: URLSearchParams): ServiceOrderFilter {
  const filter: ServiceOrderFilter = {};
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
  const orderType = params.get('orderType');
  if (orderType) filter.orderType = orderType.split(',') as ServiceOrderOrderType[];
  const requestedOnFrom = params.get('requestedOnFrom');
  if (requestedOnFrom) filter.requestedOnFrom = requestedOnFrom;
  const requestedOnTo = params.get('requestedOnTo');
  if (requestedOnTo) filter.requestedOnTo = requestedOnTo;
  const targetDateFrom = params.get('targetDateFrom');
  if (targetDateFrom) filter.targetDateFrom = targetDateFrom;
  const targetDateTo = params.get('targetDateTo');
  if (targetDateTo) filter.targetDateTo = targetDateTo;
  const priorityMin = params.get('priorityMin');
  if (priorityMin !== null && priorityMin !== '') filter.priorityMin = Number(priorityMin);
  const priorityMax = params.get('priorityMax');
  if (priorityMax !== null && priorityMax !== '') filter.priorityMax = Number(priorityMax);
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ServiceOrderCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeServiceOrderFilter(filter: ServiceOrderFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.orderType?.length) params.set('orderType', filter.orderType.join(','));
  if (filter.requestedOnFrom) params.set('requestedOnFrom', filter.requestedOnFrom);
  if (filter.requestedOnTo) params.set('requestedOnTo', filter.requestedOnTo);
  if (filter.targetDateFrom) params.set('targetDateFrom', filter.targetDateFrom);
  if (filter.targetDateTo) params.set('targetDateTo', filter.targetDateTo);
  if (filter.priorityMin !== undefined) params.set('priorityMin', String(filter.priorityMin));
  if (filter.priorityMax !== undefined) params.set('priorityMax', String(filter.priorityMax));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countServiceOrderActiveFilters(filter: ServiceOrderFilter): number {
  return Array.from(serializeServiceOrderFilter(filter).keys()).length;
}
