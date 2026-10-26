import type { LifecycleStatus } from '@/shared/types';
import type { BuildingFilter, BuildingOwnership, BuildingLifecycleStage, BuildingCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toBuildingQuery(filter: BuildingFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.siteId) query.siteId = filter.siteId;
  if (filter.floorsMin !== undefined) query.floorsMin = filter.floorsMin;
  if (filter.floorsMax !== undefined) query.floorsMax = filter.floorsMax;
  if (filter.yearBuiltMin !== undefined) query.yearBuiltMin = filter.yearBuiltMin;
  if (filter.yearBuiltMax !== undefined) query.yearBuiltMax = filter.yearBuiltMax;
  if (filter.ownership?.length) query.ownership = filter.ownership.join(',');
  if (filter.leaseExpiryFrom) query.leaseExpiryFrom = filter.leaseExpiryFrom;
  if (filter.leaseExpiryTo) query.leaseExpiryTo = filter.leaseExpiryTo;
  if (filter.securityLevelMin !== undefined) query.securityLevelMin = filter.securityLevelMin;
  if (filter.securityLevelMax !== undefined) query.securityLevelMax = filter.securityLevelMax;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseBuildingFilter(params: URLSearchParams): BuildingFilter {
  const filter: BuildingFilter = {};
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
  const siteId = params.get('siteId');
  if (siteId) filter.siteId = siteId;
  const floorsMin = params.get('floorsMin');
  if (floorsMin !== null && floorsMin !== '') filter.floorsMin = Number(floorsMin);
  const floorsMax = params.get('floorsMax');
  if (floorsMax !== null && floorsMax !== '') filter.floorsMax = Number(floorsMax);
  const yearBuiltMin = params.get('yearBuiltMin');
  if (yearBuiltMin !== null && yearBuiltMin !== '') filter.yearBuiltMin = Number(yearBuiltMin);
  const yearBuiltMax = params.get('yearBuiltMax');
  if (yearBuiltMax !== null && yearBuiltMax !== '') filter.yearBuiltMax = Number(yearBuiltMax);
  const ownership = params.get('ownership');
  if (ownership) filter.ownership = ownership.split(',') as BuildingOwnership[];
  const leaseExpiryFrom = params.get('leaseExpiryFrom');
  if (leaseExpiryFrom) filter.leaseExpiryFrom = leaseExpiryFrom;
  const leaseExpiryTo = params.get('leaseExpiryTo');
  if (leaseExpiryTo) filter.leaseExpiryTo = leaseExpiryTo;
  const securityLevelMin = params.get('securityLevelMin');
  if (securityLevelMin !== null && securityLevelMin !== '') filter.securityLevelMin = Number(securityLevelMin);
  const securityLevelMax = params.get('securityLevelMax');
  if (securityLevelMax !== null && securityLevelMax !== '') filter.securityLevelMax = Number(securityLevelMax);
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as BuildingLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as BuildingCriticality[];
  return filter;
}

export function serializeBuildingFilter(filter: BuildingFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.siteId) params.set('siteId', filter.siteId);
  if (filter.floorsMin !== undefined) params.set('floorsMin', String(filter.floorsMin));
  if (filter.floorsMax !== undefined) params.set('floorsMax', String(filter.floorsMax));
  if (filter.yearBuiltMin !== undefined) params.set('yearBuiltMin', String(filter.yearBuiltMin));
  if (filter.yearBuiltMax !== undefined) params.set('yearBuiltMax', String(filter.yearBuiltMax));
  if (filter.ownership?.length) params.set('ownership', filter.ownership.join(','));
  if (filter.leaseExpiryFrom) params.set('leaseExpiryFrom', filter.leaseExpiryFrom);
  if (filter.leaseExpiryTo) params.set('leaseExpiryTo', filter.leaseExpiryTo);
  if (filter.securityLevelMin !== undefined) params.set('securityLevelMin', String(filter.securityLevelMin));
  if (filter.securityLevelMax !== undefined) params.set('securityLevelMax', String(filter.securityLevelMax));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countBuildingActiveFilters(filter: BuildingFilter): number {
  return Array.from(serializeBuildingFilter(filter).keys()).length;
}
