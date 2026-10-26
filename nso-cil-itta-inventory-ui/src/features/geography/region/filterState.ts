import type { LifecycleStatus } from '@/shared/types';
import type { RegionFilter, RegionRegionType, RegionCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRegionQuery(filter: RegionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.countryId) query.countryId = filter.countryId;
  if (filter.regionType?.length) query.regionType = filter.regionType.join(',');
  if (filter.populationMin !== undefined) query.populationMin = filter.populationMin;
  if (filter.populationMax !== undefined) query.populationMax = filter.populationMax;
  if (filter.latitudeMin !== undefined) query.latitudeMin = filter.latitudeMin;
  if (filter.latitudeMax !== undefined) query.latitudeMax = filter.latitudeMax;
  if (filter.longitudeMin !== undefined) query.longitudeMin = filter.longitudeMin;
  if (filter.longitudeMax !== undefined) query.longitudeMax = filter.longitudeMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseRegionFilter(params: URLSearchParams): RegionFilter {
  const filter: RegionFilter = {};
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
  const countryId = params.get('countryId');
  if (countryId) filter.countryId = countryId;
  const regionType = params.get('regionType');
  if (regionType) filter.regionType = regionType.split(',') as RegionRegionType[];
  const populationMin = params.get('populationMin');
  if (populationMin !== null && populationMin !== '') filter.populationMin = Number(populationMin);
  const populationMax = params.get('populationMax');
  if (populationMax !== null && populationMax !== '') filter.populationMax = Number(populationMax);
  const latitudeMin = params.get('latitudeMin');
  if (latitudeMin !== null && latitudeMin !== '') filter.latitudeMin = Number(latitudeMin);
  const latitudeMax = params.get('latitudeMax');
  if (latitudeMax !== null && latitudeMax !== '') filter.latitudeMax = Number(latitudeMax);
  const longitudeMin = params.get('longitudeMin');
  if (longitudeMin !== null && longitudeMin !== '') filter.longitudeMin = Number(longitudeMin);
  const longitudeMax = params.get('longitudeMax');
  if (longitudeMax !== null && longitudeMax !== '') filter.longitudeMax = Number(longitudeMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as RegionCriticality[];
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

export function serializeRegionFilter(filter: RegionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.countryId) params.set('countryId', filter.countryId);
  if (filter.regionType?.length) params.set('regionType', filter.regionType.join(','));
  if (filter.populationMin !== undefined) params.set('populationMin', String(filter.populationMin));
  if (filter.populationMax !== undefined) params.set('populationMax', String(filter.populationMax));
  if (filter.latitudeMin !== undefined) params.set('latitudeMin', String(filter.latitudeMin));
  if (filter.latitudeMax !== undefined) params.set('latitudeMax', String(filter.latitudeMax));
  if (filter.longitudeMin !== undefined) params.set('longitudeMin', String(filter.longitudeMin));
  if (filter.longitudeMax !== undefined) params.set('longitudeMax', String(filter.longitudeMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countRegionActiveFilters(filter: RegionFilter): number {
  return Array.from(serializeRegionFilter(filter).keys()).length;
}
