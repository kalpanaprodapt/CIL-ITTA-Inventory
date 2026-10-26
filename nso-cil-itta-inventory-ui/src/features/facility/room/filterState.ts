import type { LifecycleStatus } from '@/shared/types';
import type { RoomFilter, RoomRoomType, RoomLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRoomQuery(filter: RoomFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.floorId) query.floorId = filter.floorId;
  if (filter.roomType?.length) query.roomType = filter.roomType.join(',');
  if (filter.areaSqmMin !== undefined) query.areaSqmMin = filter.areaSqmMin;
  if (filter.areaSqmMax !== undefined) query.areaSqmMax = filter.areaSqmMax;
  if (filter.coolingTonsMin !== undefined) query.coolingTonsMin = filter.coolingTonsMin;
  if (filter.coolingTonsMax !== undefined) query.coolingTonsMax = filter.coolingTonsMax;
  if (filter.fireZone) query.fireZone = filter.fireZone;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseRoomFilter(params: URLSearchParams): RoomFilter {
  const filter: RoomFilter = {};
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
  const floorId = params.get('floorId');
  if (floorId) filter.floorId = floorId;
  const roomType = params.get('roomType');
  if (roomType) filter.roomType = roomType.split(',') as RoomRoomType[];
  const areaSqmMin = params.get('areaSqmMin');
  if (areaSqmMin !== null && areaSqmMin !== '') filter.areaSqmMin = Number(areaSqmMin);
  const areaSqmMax = params.get('areaSqmMax');
  if (areaSqmMax !== null && areaSqmMax !== '') filter.areaSqmMax = Number(areaSqmMax);
  const coolingTonsMin = params.get('coolingTonsMin');
  if (coolingTonsMin !== null && coolingTonsMin !== '') filter.coolingTonsMin = Number(coolingTonsMin);
  const coolingTonsMax = params.get('coolingTonsMax');
  if (coolingTonsMax !== null && coolingTonsMax !== '') filter.coolingTonsMax = Number(coolingTonsMax);
  const fireZone = params.get('fireZone');
  if (fireZone) filter.fireZone = fireZone;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as RoomLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeRoomFilter(filter: RoomFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.floorId) params.set('floorId', filter.floorId);
  if (filter.roomType?.length) params.set('roomType', filter.roomType.join(','));
  if (filter.areaSqmMin !== undefined) params.set('areaSqmMin', String(filter.areaSqmMin));
  if (filter.areaSqmMax !== undefined) params.set('areaSqmMax', String(filter.areaSqmMax));
  if (filter.coolingTonsMin !== undefined) params.set('coolingTonsMin', String(filter.coolingTonsMin));
  if (filter.coolingTonsMax !== undefined) params.set('coolingTonsMax', String(filter.coolingTonsMax));
  if (filter.fireZone) params.set('fireZone', filter.fireZone);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countRoomActiveFilters(filter: RoomFilter): number {
  return Array.from(serializeRoomFilter(filter).keys()).length;
}
