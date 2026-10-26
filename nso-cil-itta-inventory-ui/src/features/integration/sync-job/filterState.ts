import type { LifecycleStatus } from '@/shared/types';
import type { SyncJobFilter, SyncJobDirection, SyncJobLastStatus, SyncJobCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSyncJobQuery(filter: SyncJobFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.endpointId) query.endpointId = filter.endpointId;
  if (filter.direction?.length) query.direction = filter.direction.join(',');
  if (filter.scheduleCron) query.scheduleCron = filter.scheduleCron;
  if (filter.lastRunAtFrom) query.lastRunAtFrom = filter.lastRunAtFrom;
  if (filter.lastRunAtTo) query.lastRunAtTo = filter.lastRunAtTo;
  if (filter.lastStatus?.length) query.lastStatus = filter.lastStatus.join(',');
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseSyncJobFilter(params: URLSearchParams): SyncJobFilter {
  const filter: SyncJobFilter = {};
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
  const endpointId = params.get('endpointId');
  if (endpointId) filter.endpointId = endpointId;
  const direction = params.get('direction');
  if (direction) filter.direction = direction.split(',') as SyncJobDirection[];
  const scheduleCron = params.get('scheduleCron');
  if (scheduleCron) filter.scheduleCron = scheduleCron;
  const lastRunAtFrom = params.get('lastRunAtFrom');
  if (lastRunAtFrom) filter.lastRunAtFrom = lastRunAtFrom;
  const lastRunAtTo = params.get('lastRunAtTo');
  if (lastRunAtTo) filter.lastRunAtTo = lastRunAtTo;
  const lastStatus = params.get('lastStatus');
  if (lastStatus) filter.lastStatus = lastStatus.split(',') as SyncJobLastStatus[];
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SyncJobCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeSyncJobFilter(filter: SyncJobFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.endpointId) params.set('endpointId', filter.endpointId);
  if (filter.direction?.length) params.set('direction', filter.direction.join(','));
  if (filter.scheduleCron) params.set('scheduleCron', filter.scheduleCron);
  if (filter.lastRunAtFrom) params.set('lastRunAtFrom', filter.lastRunAtFrom);
  if (filter.lastRunAtTo) params.set('lastRunAtTo', filter.lastRunAtTo);
  if (filter.lastStatus?.length) params.set('lastStatus', filter.lastStatus.join(','));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countSyncJobActiveFilters(filter: SyncJobFilter): number {
  return Array.from(serializeSyncJobFilter(filter).keys()).length;
}
