import type { LifecycleStatus } from '@/shared/types';
import type { FieldTaskFilter, FieldTaskCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toFieldTaskQuery(filter: FieldTaskFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.workOrderId) query.workOrderId = filter.workOrderId;
  if (filter.taskName) query.taskName = filter.taskName;
  if (filter.sequenceMin !== undefined) query.sequenceMin = filter.sequenceMin;
  if (filter.sequenceMax !== undefined) query.sequenceMax = filter.sequenceMax;
  if (filter.durationMinutesMin !== undefined) query.durationMinutesMin = filter.durationMinutesMin;
  if (filter.durationMinutesMax !== undefined) query.durationMinutesMax = filter.durationMinutesMax;
  if (filter.completedOnFrom) query.completedOnFrom = filter.completedOnFrom;
  if (filter.completedOnTo) query.completedOnTo = filter.completedOnTo;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseFieldTaskFilter(params: URLSearchParams): FieldTaskFilter {
  const filter: FieldTaskFilter = {};
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
  const workOrderId = params.get('workOrderId');
  if (workOrderId) filter.workOrderId = workOrderId;
  const taskName = params.get('taskName');
  if (taskName) filter.taskName = taskName;
  const sequenceMin = params.get('sequenceMin');
  if (sequenceMin !== null && sequenceMin !== '') filter.sequenceMin = Number(sequenceMin);
  const sequenceMax = params.get('sequenceMax');
  if (sequenceMax !== null && sequenceMax !== '') filter.sequenceMax = Number(sequenceMax);
  const durationMinutesMin = params.get('durationMinutesMin');
  if (durationMinutesMin !== null && durationMinutesMin !== '') filter.durationMinutesMin = Number(durationMinutesMin);
  const durationMinutesMax = params.get('durationMinutesMax');
  if (durationMinutesMax !== null && durationMinutesMax !== '') filter.durationMinutesMax = Number(durationMinutesMax);
  const completedOnFrom = params.get('completedOnFrom');
  if (completedOnFrom) filter.completedOnFrom = completedOnFrom;
  const completedOnTo = params.get('completedOnTo');
  if (completedOnTo) filter.completedOnTo = completedOnTo;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as FieldTaskCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeFieldTaskFilter(filter: FieldTaskFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.workOrderId) params.set('workOrderId', filter.workOrderId);
  if (filter.taskName) params.set('taskName', filter.taskName);
  if (filter.sequenceMin !== undefined) params.set('sequenceMin', String(filter.sequenceMin));
  if (filter.sequenceMax !== undefined) params.set('sequenceMax', String(filter.sequenceMax));
  if (filter.durationMinutesMin !== undefined) params.set('durationMinutesMin', String(filter.durationMinutesMin));
  if (filter.durationMinutesMax !== undefined) params.set('durationMinutesMax', String(filter.durationMinutesMax));
  if (filter.completedOnFrom) params.set('completedOnFrom', filter.completedOnFrom);
  if (filter.completedOnTo) params.set('completedOnTo', filter.completedOnTo);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countFieldTaskActiveFilters(filter: FieldTaskFilter): number {
  return Array.from(serializeFieldTaskFilter(filter).keys()).length;
}
