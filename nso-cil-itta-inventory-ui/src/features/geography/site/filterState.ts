import type { LifecycleStatus } from '@/shared/types';
import type { SiteFilter, SiteSiteType, SiteLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSiteQuery(filter: SiteFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.cityId) query.cityId = filter.cityId;
  if (filter.siteType?.length) query.siteType = filter.siteType.join(',');
  if (filter.address) query.address = filter.address;
  if (filter.latitudeMin !== undefined) query.latitudeMin = filter.latitudeMin;
  if (filter.latitudeMax !== undefined) query.latitudeMax = filter.latitudeMax;
  if (filter.longitudeMin !== undefined) query.longitudeMin = filter.longitudeMin;
  if (filter.longitudeMax !== undefined) query.longitudeMax = filter.longitudeMax;
  if (filter.powerKwMin !== undefined) query.powerKwMin = filter.powerKwMin;
  if (filter.powerKwMax !== undefined) query.powerKwMax = filter.powerKwMax;
  if (filter.accessHours) query.accessHours = filter.accessHours;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseSiteFilter(params: URLSearchParams): SiteFilter {
  const filter: SiteFilter = {};
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
  const cityId = params.get('cityId');
  if (cityId) filter.cityId = cityId;
  const siteType = params.get('siteType');
  if (siteType) filter.siteType = siteType.split(',') as SiteSiteType[];
  const address = params.get('address');
  if (address) filter.address = address;
  const latitudeMin = params.get('latitudeMin');
  if (latitudeMin !== null && latitudeMin !== '') filter.latitudeMin = Number(latitudeMin);
  const latitudeMax = params.get('latitudeMax');
  if (latitudeMax !== null && latitudeMax !== '') filter.latitudeMax = Number(latitudeMax);
  const longitudeMin = params.get('longitudeMin');
  if (longitudeMin !== null && longitudeMin !== '') filter.longitudeMin = Number(longitudeMin);
  const longitudeMax = params.get('longitudeMax');
  if (longitudeMax !== null && longitudeMax !== '') filter.longitudeMax = Number(longitudeMax);
  const powerKwMin = params.get('powerKwMin');
  if (powerKwMin !== null && powerKwMin !== '') filter.powerKwMin = Number(powerKwMin);
  const powerKwMax = params.get('powerKwMax');
  if (powerKwMax !== null && powerKwMax !== '') filter.powerKwMax = Number(powerKwMax);
  const accessHours = params.get('accessHours');
  if (accessHours) filter.accessHours = accessHours;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as SiteLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeSiteFilter(filter: SiteFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.cityId) params.set('cityId', filter.cityId);
  if (filter.siteType?.length) params.set('siteType', filter.siteType.join(','));
  if (filter.address) params.set('address', filter.address);
  if (filter.latitudeMin !== undefined) params.set('latitudeMin', String(filter.latitudeMin));
  if (filter.latitudeMax !== undefined) params.set('latitudeMax', String(filter.latitudeMax));
  if (filter.longitudeMin !== undefined) params.set('longitudeMin', String(filter.longitudeMin));
  if (filter.longitudeMax !== undefined) params.set('longitudeMax', String(filter.longitudeMax));
  if (filter.powerKwMin !== undefined) params.set('powerKwMin', String(filter.powerKwMin));
  if (filter.powerKwMax !== undefined) params.set('powerKwMax', String(filter.powerKwMax));
  if (filter.accessHours) params.set('accessHours', filter.accessHours);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countSiteActiveFilters(filter: SiteFilter): number {
  return Array.from(serializeSiteFilter(filter).keys()).length;
}
