import type { LifecycleStatus } from '@/shared/types';
import type { TechnicianFilter, TechnicianCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTechnicianQuery(filter: TechnicianFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.employeeId) query.employeeId = filter.employeeId;
  if (filter.fullName) query.fullName = filter.fullName;
  if (filter.skillSet) query.skillSet = filter.skillSet;
  if (filter.homeSiteId) query.homeSiteId = filter.homeSiteId;
  if (filter.certificationLevelMin !== undefined) query.certificationLevelMin = filter.certificationLevelMin;
  if (filter.certificationLevelMax !== undefined) query.certificationLevelMax = filter.certificationLevelMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseTechnicianFilter(params: URLSearchParams): TechnicianFilter {
  const filter: TechnicianFilter = {};
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
  const employeeId = params.get('employeeId');
  if (employeeId) filter.employeeId = employeeId;
  const fullName = params.get('fullName');
  if (fullName) filter.fullName = fullName;
  const skillSet = params.get('skillSet');
  if (skillSet) filter.skillSet = skillSet;
  const homeSiteId = params.get('homeSiteId');
  if (homeSiteId) filter.homeSiteId = homeSiteId;
  const certificationLevelMin = params.get('certificationLevelMin');
  if (certificationLevelMin !== null && certificationLevelMin !== '') filter.certificationLevelMin = Number(certificationLevelMin);
  const certificationLevelMax = params.get('certificationLevelMax');
  if (certificationLevelMax !== null && certificationLevelMax !== '') filter.certificationLevelMax = Number(certificationLevelMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as TechnicianCriticality[];
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

export function serializeTechnicianFilter(filter: TechnicianFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.employeeId) params.set('employeeId', filter.employeeId);
  if (filter.fullName) params.set('fullName', filter.fullName);
  if (filter.skillSet) params.set('skillSet', filter.skillSet);
  if (filter.homeSiteId) params.set('homeSiteId', filter.homeSiteId);
  if (filter.certificationLevelMin !== undefined) params.set('certificationLevelMin', String(filter.certificationLevelMin));
  if (filter.certificationLevelMax !== undefined) params.set('certificationLevelMax', String(filter.certificationLevelMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countTechnicianActiveFilters(filter: TechnicianFilter): number {
  return Array.from(serializeTechnicianFilter(filter).keys()).length;
}
