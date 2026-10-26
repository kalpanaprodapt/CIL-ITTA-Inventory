import type { LifecycleStatus } from '@/shared/types';
import type { RouteTargetFilter, RouteTargetRtType, RouteTargetLifecycleStage, RouteTargetCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRouteTargetQuery(filter: RouteTargetFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.asnMin !== undefined) query.asnMin = filter.asnMin;
  if (filter.asnMax !== undefined) query.asnMax = filter.asnMax;
  if (filter.assignedNumberMin !== undefined) query.assignedNumberMin = filter.assignedNumberMin;
  if (filter.assignedNumberMax !== undefined) query.assignedNumberMax = filter.assignedNumberMax;
  if (filter.rtType?.length) query.rtType = filter.rtType.join(',');
  if (filter.vrfId) query.vrfId = filter.vrfId;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseRouteTargetFilter(params: URLSearchParams): RouteTargetFilter {
  const filter: RouteTargetFilter = {};
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
  const asnMin = params.get('asnMin');
  if (asnMin !== null && asnMin !== '') filter.asnMin = Number(asnMin);
  const asnMax = params.get('asnMax');
  if (asnMax !== null && asnMax !== '') filter.asnMax = Number(asnMax);
  const assignedNumberMin = params.get('assignedNumberMin');
  if (assignedNumberMin !== null && assignedNumberMin !== '') filter.assignedNumberMin = Number(assignedNumberMin);
  const assignedNumberMax = params.get('assignedNumberMax');
  if (assignedNumberMax !== null && assignedNumberMax !== '') filter.assignedNumberMax = Number(assignedNumberMax);
  const rtType = params.get('rtType');
  if (rtType) filter.rtType = rtType.split(',') as RouteTargetRtType[];
  const vrfId = params.get('vrfId');
  if (vrfId) filter.vrfId = vrfId;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as RouteTargetLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as RouteTargetCriticality[];
  return filter;
}

export function serializeRouteTargetFilter(filter: RouteTargetFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.asnMin !== undefined) params.set('asnMin', String(filter.asnMin));
  if (filter.asnMax !== undefined) params.set('asnMax', String(filter.asnMax));
  if (filter.assignedNumberMin !== undefined) params.set('assignedNumberMin', String(filter.assignedNumberMin));
  if (filter.assignedNumberMax !== undefined) params.set('assignedNumberMax', String(filter.assignedNumberMax));
  if (filter.rtType?.length) params.set('rtType', filter.rtType.join(','));
  if (filter.vrfId) params.set('vrfId', filter.vrfId);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countRouteTargetActiveFilters(filter: RouteTargetFilter): number {
  return Array.from(serializeRouteTargetFilter(filter).keys()).length;
}
