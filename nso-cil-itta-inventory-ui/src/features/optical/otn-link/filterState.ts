import type { LifecycleStatus } from '@/shared/types';
import type { OtnLinkFilter, OtnLinkOdu, OtnLinkLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toOtnLinkQuery(filter: OtnLinkFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.aNodeId) query.aNodeId = filter.aNodeId;
  if (filter.zNodeId) query.zNodeId = filter.zNodeId;
  if (filter.odu?.length) query.odu = filter.odu.join(',');
  if (filter.latencyUsMin !== undefined) query.latencyUsMin = filter.latencyUsMin;
  if (filter.latencyUsMax !== undefined) query.latencyUsMax = filter.latencyUsMax;
  if (filter.protection) query.protection = filter.protection;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseOtnLinkFilter(params: URLSearchParams): OtnLinkFilter {
  const filter: OtnLinkFilter = {};
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
  const aNodeId = params.get('aNodeId');
  if (aNodeId) filter.aNodeId = aNodeId;
  const zNodeId = params.get('zNodeId');
  if (zNodeId) filter.zNodeId = zNodeId;
  const odu = params.get('odu');
  if (odu) filter.odu = odu.split(',') as OtnLinkOdu[];
  const latencyUsMin = params.get('latencyUsMin');
  if (latencyUsMin !== null && latencyUsMin !== '') filter.latencyUsMin = Number(latencyUsMin);
  const latencyUsMax = params.get('latencyUsMax');
  if (latencyUsMax !== null && latencyUsMax !== '') filter.latencyUsMax = Number(latencyUsMax);
  const protection = params.get('protection');
  if (protection) filter.protection = protection;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as OtnLinkLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeOtnLinkFilter(filter: OtnLinkFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.aNodeId) params.set('aNodeId', filter.aNodeId);
  if (filter.zNodeId) params.set('zNodeId', filter.zNodeId);
  if (filter.odu?.length) params.set('odu', filter.odu.join(','));
  if (filter.latencyUsMin !== undefined) params.set('latencyUsMin', String(filter.latencyUsMin));
  if (filter.latencyUsMax !== undefined) params.set('latencyUsMax', String(filter.latencyUsMax));
  if (filter.protection) params.set('protection', filter.protection);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countOtnLinkActiveFilters(filter: OtnLinkFilter): number {
  return Array.from(serializeOtnLinkFilter(filter).keys()).length;
}
