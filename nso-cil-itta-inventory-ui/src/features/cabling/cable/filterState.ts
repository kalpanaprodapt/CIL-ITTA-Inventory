import type { LifecycleStatus } from '@/shared/types';
import type { CableFilter, CableCableType, CableCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCableQuery(filter: CableFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.cableType?.length) query.cableType = filter.cableType.join(',');
  if (filter.lengthMMin !== undefined) query.lengthMMin = filter.lengthMMin;
  if (filter.lengthMMax !== undefined) query.lengthMMax = filter.lengthMMax;
  if (filter.aEndPortId) query.aEndPortId = filter.aEndPortId;
  if (filter.zEndPortId) query.zEndPortId = filter.zEndPortId;
  if (filter.color) query.color = filter.color;
  if (filter.label) query.label = filter.label;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseCableFilter(params: URLSearchParams): CableFilter {
  const filter: CableFilter = {};
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
  const cableType = params.get('cableType');
  if (cableType) filter.cableType = cableType.split(',') as CableCableType[];
  const lengthMMin = params.get('lengthMMin');
  if (lengthMMin !== null && lengthMMin !== '') filter.lengthMMin = Number(lengthMMin);
  const lengthMMax = params.get('lengthMMax');
  if (lengthMMax !== null && lengthMMax !== '') filter.lengthMMax = Number(lengthMMax);
  const aEndPortId = params.get('aEndPortId');
  if (aEndPortId) filter.aEndPortId = aEndPortId;
  const zEndPortId = params.get('zEndPortId');
  if (zEndPortId) filter.zEndPortId = zEndPortId;
  const color = params.get('color');
  if (color) filter.color = color;
  const label = params.get('label');
  if (label) filter.label = label;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as CableCriticality[];
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

export function serializeCableFilter(filter: CableFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.cableType?.length) params.set('cableType', filter.cableType.join(','));
  if (filter.lengthMMin !== undefined) params.set('lengthMMin', String(filter.lengthMMin));
  if (filter.lengthMMax !== undefined) params.set('lengthMMax', String(filter.lengthMMax));
  if (filter.aEndPortId) params.set('aEndPortId', filter.aEndPortId);
  if (filter.zEndPortId) params.set('zEndPortId', filter.zEndPortId);
  if (filter.color) params.set('color', filter.color);
  if (filter.label) params.set('label', filter.label);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countCableActiveFilters(filter: CableFilter): number {
  return Array.from(serializeCableFilter(filter).keys()).length;
}
