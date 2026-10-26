import type { LifecycleStatus } from '@/shared/types';
import type { RackFilter, RackLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRackQuery(filter: RackFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.roomId) query.roomId = filter.roomId;
  if (filter.rackUnitsMin !== undefined) query.rackUnitsMin = filter.rackUnitsMin;
  if (filter.rackUnitsMax !== undefined) query.rackUnitsMax = filter.rackUnitsMax;
  if (filter.widthMmMin !== undefined) query.widthMmMin = filter.widthMmMin;
  if (filter.widthMmMax !== undefined) query.widthMmMax = filter.widthMmMax;
  if (filter.depthMmMin !== undefined) query.depthMmMin = filter.depthMmMin;
  if (filter.depthMmMax !== undefined) query.depthMmMax = filter.depthMmMax;
  if (filter.maxWeightKgMin !== undefined) query.maxWeightKgMin = filter.maxWeightKgMin;
  if (filter.maxWeightKgMax !== undefined) query.maxWeightKgMax = filter.maxWeightKgMax;
  if (filter.rowLabel) query.rowLabel = filter.rowLabel;
  if (filter.position) query.position = filter.position;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseRackFilter(params: URLSearchParams): RackFilter {
  const filter: RackFilter = {};
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
  const roomId = params.get('roomId');
  if (roomId) filter.roomId = roomId;
  const rackUnitsMin = params.get('rackUnitsMin');
  if (rackUnitsMin !== null && rackUnitsMin !== '') filter.rackUnitsMin = Number(rackUnitsMin);
  const rackUnitsMax = params.get('rackUnitsMax');
  if (rackUnitsMax !== null && rackUnitsMax !== '') filter.rackUnitsMax = Number(rackUnitsMax);
  const widthMmMin = params.get('widthMmMin');
  if (widthMmMin !== null && widthMmMin !== '') filter.widthMmMin = Number(widthMmMin);
  const widthMmMax = params.get('widthMmMax');
  if (widthMmMax !== null && widthMmMax !== '') filter.widthMmMax = Number(widthMmMax);
  const depthMmMin = params.get('depthMmMin');
  if (depthMmMin !== null && depthMmMin !== '') filter.depthMmMin = Number(depthMmMin);
  const depthMmMax = params.get('depthMmMax');
  if (depthMmMax !== null && depthMmMax !== '') filter.depthMmMax = Number(depthMmMax);
  const maxWeightKgMin = params.get('maxWeightKgMin');
  if (maxWeightKgMin !== null && maxWeightKgMin !== '') filter.maxWeightKgMin = Number(maxWeightKgMin);
  const maxWeightKgMax = params.get('maxWeightKgMax');
  if (maxWeightKgMax !== null && maxWeightKgMax !== '') filter.maxWeightKgMax = Number(maxWeightKgMax);
  const rowLabel = params.get('rowLabel');
  if (rowLabel) filter.rowLabel = rowLabel;
  const position = params.get('position');
  if (position) filter.position = position;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as RackLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeRackFilter(filter: RackFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.roomId) params.set('roomId', filter.roomId);
  if (filter.rackUnitsMin !== undefined) params.set('rackUnitsMin', String(filter.rackUnitsMin));
  if (filter.rackUnitsMax !== undefined) params.set('rackUnitsMax', String(filter.rackUnitsMax));
  if (filter.widthMmMin !== undefined) params.set('widthMmMin', String(filter.widthMmMin));
  if (filter.widthMmMax !== undefined) params.set('widthMmMax', String(filter.widthMmMax));
  if (filter.depthMmMin !== undefined) params.set('depthMmMin', String(filter.depthMmMin));
  if (filter.depthMmMax !== undefined) params.set('depthMmMax', String(filter.depthMmMax));
  if (filter.maxWeightKgMin !== undefined) params.set('maxWeightKgMin', String(filter.maxWeightKgMin));
  if (filter.maxWeightKgMax !== undefined) params.set('maxWeightKgMax', String(filter.maxWeightKgMax));
  if (filter.rowLabel) params.set('rowLabel', filter.rowLabel);
  if (filter.position) params.set('position', filter.position);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countRackActiveFilters(filter: RackFilter): number {
  return Array.from(serializeRackFilter(filter).keys()).length;
}
