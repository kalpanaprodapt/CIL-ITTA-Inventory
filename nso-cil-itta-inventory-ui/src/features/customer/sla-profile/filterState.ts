import type { LifecycleStatus } from '@/shared/types';
import type { SlaProfileFilter, SlaProfileCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSlaProfileQuery(filter: SlaProfileFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.profileName) query.profileName = filter.profileName;
  if (filter.availabilityPctMin !== undefined) query.availabilityPctMin = filter.availabilityPctMin;
  if (filter.availabilityPctMax !== undefined) query.availabilityPctMax = filter.availabilityPctMax;
  if (filter.mttrMinutesMin !== undefined) query.mttrMinutesMin = filter.mttrMinutesMin;
  if (filter.mttrMinutesMax !== undefined) query.mttrMinutesMax = filter.mttrMinutesMax;
  if (filter.latencyMsMin !== undefined) query.latencyMsMin = filter.latencyMsMin;
  if (filter.latencyMsMax !== undefined) query.latencyMsMax = filter.latencyMsMax;
  if (filter.jitterMsMin !== undefined) query.jitterMsMin = filter.jitterMsMin;
  if (filter.jitterMsMax !== undefined) query.jitterMsMax = filter.jitterMsMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseSlaProfileFilter(params: URLSearchParams): SlaProfileFilter {
  const filter: SlaProfileFilter = {};
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
  const profileName = params.get('profileName');
  if (profileName) filter.profileName = profileName;
  const availabilityPctMin = params.get('availabilityPctMin');
  if (availabilityPctMin !== null && availabilityPctMin !== '') filter.availabilityPctMin = Number(availabilityPctMin);
  const availabilityPctMax = params.get('availabilityPctMax');
  if (availabilityPctMax !== null && availabilityPctMax !== '') filter.availabilityPctMax = Number(availabilityPctMax);
  const mttrMinutesMin = params.get('mttrMinutesMin');
  if (mttrMinutesMin !== null && mttrMinutesMin !== '') filter.mttrMinutesMin = Number(mttrMinutesMin);
  const mttrMinutesMax = params.get('mttrMinutesMax');
  if (mttrMinutesMax !== null && mttrMinutesMax !== '') filter.mttrMinutesMax = Number(mttrMinutesMax);
  const latencyMsMin = params.get('latencyMsMin');
  if (latencyMsMin !== null && latencyMsMin !== '') filter.latencyMsMin = Number(latencyMsMin);
  const latencyMsMax = params.get('latencyMsMax');
  if (latencyMsMax !== null && latencyMsMax !== '') filter.latencyMsMax = Number(latencyMsMax);
  const jitterMsMin = params.get('jitterMsMin');
  if (jitterMsMin !== null && jitterMsMin !== '') filter.jitterMsMin = Number(jitterMsMin);
  const jitterMsMax = params.get('jitterMsMax');
  if (jitterMsMax !== null && jitterMsMax !== '') filter.jitterMsMax = Number(jitterMsMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SlaProfileCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeSlaProfileFilter(filter: SlaProfileFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.profileName) params.set('profileName', filter.profileName);
  if (filter.availabilityPctMin !== undefined) params.set('availabilityPctMin', String(filter.availabilityPctMin));
  if (filter.availabilityPctMax !== undefined) params.set('availabilityPctMax', String(filter.availabilityPctMax));
  if (filter.mttrMinutesMin !== undefined) params.set('mttrMinutesMin', String(filter.mttrMinutesMin));
  if (filter.mttrMinutesMax !== undefined) params.set('mttrMinutesMax', String(filter.mttrMinutesMax));
  if (filter.latencyMsMin !== undefined) params.set('latencyMsMin', String(filter.latencyMsMin));
  if (filter.latencyMsMax !== undefined) params.set('latencyMsMax', String(filter.latencyMsMax));
  if (filter.jitterMsMin !== undefined) params.set('jitterMsMin', String(filter.jitterMsMin));
  if (filter.jitterMsMax !== undefined) params.set('jitterMsMax', String(filter.jitterMsMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countSlaProfileActiveFilters(filter: SlaProfileFilter): number {
  return Array.from(serializeSlaProfileFilter(filter).keys()).length;
}
