import type { LifecycleStatus } from '@/shared/types';
import type { FloorFilter } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toFloorQuery(filter: FloorFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.buildingId) query.buildingId = filter.buildingId;
  if (filter.levelMin !== undefined) query.levelMin = filter.levelMin;
  if (filter.levelMax !== undefined) query.levelMax = filter.levelMax;
  if (filter.areaSqmMin !== undefined) query.areaSqmMin = filter.areaSqmMin;
  if (filter.areaSqmMax !== undefined) query.areaSqmMax = filter.areaSqmMax;
  if (filter.loadCapacityKgMin !== undefined) query.loadCapacityKgMin = filter.loadCapacityKgMin;
  if (filter.loadCapacityKgMax !== undefined) query.loadCapacityKgMax = filter.loadCapacityKgMax;
  if (filter.hasRaisedFloor !== undefined) query.hasRaisedFloor = filter.hasRaisedFloor;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseFloorFilter(params: URLSearchParams): FloorFilter {
  const filter: FloorFilter = {};
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
  const buildingId = params.get('buildingId');
  if (buildingId) filter.buildingId = buildingId;
  const levelMin = params.get('levelMin');
  if (levelMin !== null && levelMin !== '') filter.levelMin = Number(levelMin);
  const levelMax = params.get('levelMax');
  if (levelMax !== null && levelMax !== '') filter.levelMax = Number(levelMax);
  const areaSqmMin = params.get('areaSqmMin');
  if (areaSqmMin !== null && areaSqmMin !== '') filter.areaSqmMin = Number(areaSqmMin);
  const areaSqmMax = params.get('areaSqmMax');
  if (areaSqmMax !== null && areaSqmMax !== '') filter.areaSqmMax = Number(areaSqmMax);
  const loadCapacityKgMin = params.get('loadCapacityKgMin');
  if (loadCapacityKgMin !== null && loadCapacityKgMin !== '') filter.loadCapacityKgMin = Number(loadCapacityKgMin);
  const loadCapacityKgMax = params.get('loadCapacityKgMax');
  if (loadCapacityKgMax !== null && loadCapacityKgMax !== '') filter.loadCapacityKgMax = Number(loadCapacityKgMax);
  const hasRaisedFloor = params.get('hasRaisedFloor');
  if (hasRaisedFloor !== null) filter.hasRaisedFloor = hasRaisedFloor === 'true';
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

export function serializeFloorFilter(filter: FloorFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.buildingId) params.set('buildingId', filter.buildingId);
  if (filter.levelMin !== undefined) params.set('levelMin', String(filter.levelMin));
  if (filter.levelMax !== undefined) params.set('levelMax', String(filter.levelMax));
  if (filter.areaSqmMin !== undefined) params.set('areaSqmMin', String(filter.areaSqmMin));
  if (filter.areaSqmMax !== undefined) params.set('areaSqmMax', String(filter.areaSqmMax));
  if (filter.loadCapacityKgMin !== undefined) params.set('loadCapacityKgMin', String(filter.loadCapacityKgMin));
  if (filter.loadCapacityKgMax !== undefined) params.set('loadCapacityKgMax', String(filter.loadCapacityKgMax));
  if (filter.hasRaisedFloor !== undefined) params.set('hasRaisedFloor', String(filter.hasRaisedFloor));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countFloorActiveFilters(filter: FloorFilter): number {
  return Array.from(serializeFloorFilter(filter).keys()).length;
}
