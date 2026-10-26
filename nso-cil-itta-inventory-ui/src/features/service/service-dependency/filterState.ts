import type { LifecycleStatus } from '@/shared/types';
import type { ServiceDependencyFilter, ServiceDependencyDependencyType, ServiceDependencyCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toServiceDependencyQuery(filter: ServiceDependencyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.serviceId) query.serviceId = filter.serviceId;
  if (filter.dependsOnId) query.dependsOnId = filter.dependsOnId;
  if (filter.dependencyType?.length) query.dependencyType = filter.dependencyType.join(',');
  if (filter.impactScoreMin !== undefined) query.impactScoreMin = filter.impactScoreMin;
  if (filter.impactScoreMax !== undefined) query.impactScoreMax = filter.impactScoreMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseServiceDependencyFilter(params: URLSearchParams): ServiceDependencyFilter {
  const filter: ServiceDependencyFilter = {};
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
  const serviceId = params.get('serviceId');
  if (serviceId) filter.serviceId = serviceId;
  const dependsOnId = params.get('dependsOnId');
  if (dependsOnId) filter.dependsOnId = dependsOnId;
  const dependencyType = params.get('dependencyType');
  if (dependencyType) filter.dependencyType = dependencyType.split(',') as ServiceDependencyDependencyType[];
  const impactScoreMin = params.get('impactScoreMin');
  if (impactScoreMin !== null && impactScoreMin !== '') filter.impactScoreMin = Number(impactScoreMin);
  const impactScoreMax = params.get('impactScoreMax');
  if (impactScoreMax !== null && impactScoreMax !== '') filter.impactScoreMax = Number(impactScoreMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ServiceDependencyCriticality[];
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

export function serializeServiceDependencyFilter(filter: ServiceDependencyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.serviceId) params.set('serviceId', filter.serviceId);
  if (filter.dependsOnId) params.set('dependsOnId', filter.dependsOnId);
  if (filter.dependencyType?.length) params.set('dependencyType', filter.dependencyType.join(','));
  if (filter.impactScoreMin !== undefined) params.set('impactScoreMin', String(filter.impactScoreMin));
  if (filter.impactScoreMax !== undefined) params.set('impactScoreMax', String(filter.impactScoreMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countServiceDependencyActiveFilters(filter: ServiceDependencyFilter): number {
  return Array.from(serializeServiceDependencyFilter(filter).keys()).length;
}
