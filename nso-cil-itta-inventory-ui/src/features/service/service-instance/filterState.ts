import type { LifecycleStatus } from '@/shared/types';
import type { ServiceInstanceFilter, ServiceInstanceServiceState, ServiceInstanceLifecycleStage, ServiceInstanceCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toServiceInstanceQuery(filter: ServiceInstanceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.catalogItemId) query.catalogItemId = filter.catalogItemId;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.activatedOnFrom) query.activatedOnFrom = filter.activatedOnFrom;
  if (filter.activatedOnTo) query.activatedOnTo = filter.activatedOnTo;
  if (filter.bandwidthMbpsMin !== undefined) query.bandwidthMbpsMin = filter.bandwidthMbpsMin;
  if (filter.bandwidthMbpsMax !== undefined) query.bandwidthMbpsMax = filter.bandwidthMbpsMax;
  if (filter.serviceState?.length) query.serviceState = filter.serviceState.join(',');
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseServiceInstanceFilter(params: URLSearchParams): ServiceInstanceFilter {
  const filter: ServiceInstanceFilter = {};
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
  const catalogItemId = params.get('catalogItemId');
  if (catalogItemId) filter.catalogItemId = catalogItemId;
  const customerId = params.get('customerId');
  if (customerId) filter.customerId = customerId;
  const activatedOnFrom = params.get('activatedOnFrom');
  if (activatedOnFrom) filter.activatedOnFrom = activatedOnFrom;
  const activatedOnTo = params.get('activatedOnTo');
  if (activatedOnTo) filter.activatedOnTo = activatedOnTo;
  const bandwidthMbpsMin = params.get('bandwidthMbpsMin');
  if (bandwidthMbpsMin !== null && bandwidthMbpsMin !== '') filter.bandwidthMbpsMin = Number(bandwidthMbpsMin);
  const bandwidthMbpsMax = params.get('bandwidthMbpsMax');
  if (bandwidthMbpsMax !== null && bandwidthMbpsMax !== '') filter.bandwidthMbpsMax = Number(bandwidthMbpsMax);
  const serviceState = params.get('serviceState');
  if (serviceState) filter.serviceState = serviceState.split(',') as ServiceInstanceServiceState[];
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ServiceInstanceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ServiceInstanceCriticality[];
  return filter;
}

export function serializeServiceInstanceFilter(filter: ServiceInstanceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.catalogItemId) params.set('catalogItemId', filter.catalogItemId);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.activatedOnFrom) params.set('activatedOnFrom', filter.activatedOnFrom);
  if (filter.activatedOnTo) params.set('activatedOnTo', filter.activatedOnTo);
  if (filter.bandwidthMbpsMin !== undefined) params.set('bandwidthMbpsMin', String(filter.bandwidthMbpsMin));
  if (filter.bandwidthMbpsMax !== undefined) params.set('bandwidthMbpsMax', String(filter.bandwidthMbpsMax));
  if (filter.serviceState?.length) params.set('serviceState', filter.serviceState.join(','));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countServiceInstanceActiveFilters(filter: ServiceInstanceFilter): number {
  return Array.from(serializeServiceInstanceFilter(filter).keys()).length;
}
