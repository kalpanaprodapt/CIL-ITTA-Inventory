import type { LifecycleStatus } from '@/shared/types';
import type { VlanPoolFilter, VlanPoolLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVlanPoolQuery(filter: VlanPoolFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.poolName) query.poolName = filter.poolName;
  if (filter.rangeStartMin !== undefined) query.rangeStartMin = filter.rangeStartMin;
  if (filter.rangeStartMax !== undefined) query.rangeStartMax = filter.rangeStartMax;
  if (filter.rangeEndMin !== undefined) query.rangeEndMin = filter.rangeEndMin;
  if (filter.rangeEndMax !== undefined) query.rangeEndMax = filter.rangeEndMax;
  if (filter.allocatedMin !== undefined) query.allocatedMin = filter.allocatedMin;
  if (filter.allocatedMax !== undefined) query.allocatedMax = filter.allocatedMax;
  if (filter.scope) query.scope = filter.scope;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseVlanPoolFilter(params: URLSearchParams): VlanPoolFilter {
  const filter: VlanPoolFilter = {};
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
  const poolName = params.get('poolName');
  if (poolName) filter.poolName = poolName;
  const rangeStartMin = params.get('rangeStartMin');
  if (rangeStartMin !== null && rangeStartMin !== '') filter.rangeStartMin = Number(rangeStartMin);
  const rangeStartMax = params.get('rangeStartMax');
  if (rangeStartMax !== null && rangeStartMax !== '') filter.rangeStartMax = Number(rangeStartMax);
  const rangeEndMin = params.get('rangeEndMin');
  if (rangeEndMin !== null && rangeEndMin !== '') filter.rangeEndMin = Number(rangeEndMin);
  const rangeEndMax = params.get('rangeEndMax');
  if (rangeEndMax !== null && rangeEndMax !== '') filter.rangeEndMax = Number(rangeEndMax);
  const allocatedMin = params.get('allocatedMin');
  if (allocatedMin !== null && allocatedMin !== '') filter.allocatedMin = Number(allocatedMin);
  const allocatedMax = params.get('allocatedMax');
  if (allocatedMax !== null && allocatedMax !== '') filter.allocatedMax = Number(allocatedMax);
  const scope = params.get('scope');
  if (scope) filter.scope = scope;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as VlanPoolLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeVlanPoolFilter(filter: VlanPoolFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.poolName) params.set('poolName', filter.poolName);
  if (filter.rangeStartMin !== undefined) params.set('rangeStartMin', String(filter.rangeStartMin));
  if (filter.rangeStartMax !== undefined) params.set('rangeStartMax', String(filter.rangeStartMax));
  if (filter.rangeEndMin !== undefined) params.set('rangeEndMin', String(filter.rangeEndMin));
  if (filter.rangeEndMax !== undefined) params.set('rangeEndMax', String(filter.rangeEndMax));
  if (filter.allocatedMin !== undefined) params.set('allocatedMin', String(filter.allocatedMin));
  if (filter.allocatedMax !== undefined) params.set('allocatedMax', String(filter.allocatedMax));
  if (filter.scope) params.set('scope', filter.scope);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countVlanPoolActiveFilters(filter: VlanPoolFilter): number {
  return Array.from(serializeVlanPoolFilter(filter).keys()).length;
}
