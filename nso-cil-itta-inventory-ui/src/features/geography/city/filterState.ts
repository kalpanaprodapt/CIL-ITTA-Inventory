import type { LifecycleStatus } from '@/shared/types';
import type { CityFilter, CityLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCityQuery(filter: CityFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.regionId) query.regionId = filter.regionId;
  if (filter.postalPrefix) query.postalPrefix = filter.postalPrefix;
  if (filter.populationMin !== undefined) query.populationMin = filter.populationMin;
  if (filter.populationMax !== undefined) query.populationMax = filter.populationMax;
  if (filter.latitudeMin !== undefined) query.latitudeMin = filter.latitudeMin;
  if (filter.latitudeMax !== undefined) query.latitudeMax = filter.latitudeMax;
  if (filter.longitudeMin !== undefined) query.longitudeMin = filter.longitudeMin;
  if (filter.longitudeMax !== undefined) query.longitudeMax = filter.longitudeMax;
  if (filter.metroArea) query.metroArea = filter.metroArea;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseCityFilter(params: URLSearchParams): CityFilter {
  const filter: CityFilter = {};
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
  const regionId = params.get('regionId');
  if (regionId) filter.regionId = regionId;
  const postalPrefix = params.get('postalPrefix');
  if (postalPrefix) filter.postalPrefix = postalPrefix;
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
  const metroArea = params.get('metroArea');
  if (metroArea) filter.metroArea = metroArea;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CityLifecycleStage[];
  return filter;
}

export function serializeCityFilter(filter: CityFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.regionId) params.set('regionId', filter.regionId);
  if (filter.postalPrefix) params.set('postalPrefix', filter.postalPrefix);
  if (filter.populationMin !== undefined) params.set('populationMin', String(filter.populationMin));
  if (filter.populationMax !== undefined) params.set('populationMax', String(filter.populationMax));
  if (filter.latitudeMin !== undefined) params.set('latitudeMin', String(filter.latitudeMin));
  if (filter.latitudeMax !== undefined) params.set('latitudeMax', String(filter.latitudeMax));
  if (filter.longitudeMin !== undefined) params.set('longitudeMin', String(filter.longitudeMin));
  if (filter.longitudeMax !== undefined) params.set('longitudeMax', String(filter.longitudeMax));
  if (filter.metroArea) params.set('metroArea', filter.metroArea);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countCityActiveFilters(filter: CityFilter): number {
  return Array.from(serializeCityFilter(filter).keys()).length;
}
