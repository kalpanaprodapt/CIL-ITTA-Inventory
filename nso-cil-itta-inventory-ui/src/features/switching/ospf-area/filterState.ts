import type { LifecycleStatus } from '@/shared/types';
import type { OspfAreaFilter, OspfAreaAreaType, OspfAreaCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toOspfAreaQuery(filter: OspfAreaFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.areaId) query.areaId = filter.areaId;
  if (filter.areaType?.length) query.areaType = filter.areaType.join(',');
  if (filter.deviceCountMin !== undefined) query.deviceCountMin = filter.deviceCountMin;
  if (filter.deviceCountMax !== undefined) query.deviceCountMax = filter.deviceCountMax;
  if (filter.authType) query.authType = filter.authType;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseOspfAreaFilter(params: URLSearchParams): OspfAreaFilter {
  const filter: OspfAreaFilter = {};
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
  const areaId = params.get('areaId');
  if (areaId) filter.areaId = areaId;
  const areaType = params.get('areaType');
  if (areaType) filter.areaType = areaType.split(',') as OspfAreaAreaType[];
  const deviceCountMin = params.get('deviceCountMin');
  if (deviceCountMin !== null && deviceCountMin !== '') filter.deviceCountMin = Number(deviceCountMin);
  const deviceCountMax = params.get('deviceCountMax');
  if (deviceCountMax !== null && deviceCountMax !== '') filter.deviceCountMax = Number(deviceCountMax);
  const authType = params.get('authType');
  if (authType) filter.authType = authType;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as OspfAreaCriticality[];
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
  return filter;
}

export function serializeOspfAreaFilter(filter: OspfAreaFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.areaId) params.set('areaId', filter.areaId);
  if (filter.areaType?.length) params.set('areaType', filter.areaType.join(','));
  if (filter.deviceCountMin !== undefined) params.set('deviceCountMin', String(filter.deviceCountMin));
  if (filter.deviceCountMax !== undefined) params.set('deviceCountMax', String(filter.deviceCountMax));
  if (filter.authType) params.set('authType', filter.authType);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countOspfAreaActiveFilters(filter: OspfAreaFilter): number {
  return Array.from(serializeOspfAreaFilter(filter).keys()).length;
}
