import type { LifecycleStatus } from '@/shared/types';
import type { DiscoveryJobFilter, DiscoveryJobJobState, DiscoveryJobCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toDiscoveryJobQuery(filter: DiscoveryJobFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.profileId) query.profileId = filter.profileId;
  if (filter.startedAtFrom) query.startedAtFrom = filter.startedAtFrom;
  if (filter.startedAtTo) query.startedAtTo = filter.startedAtTo;
  if (filter.finishedAtFrom) query.finishedAtFrom = filter.finishedAtFrom;
  if (filter.finishedAtTo) query.finishedAtTo = filter.finishedAtTo;
  if (filter.jobState?.length) query.jobState = filter.jobState.join(',');
  if (filter.assetsFoundMin !== undefined) query.assetsFoundMin = filter.assetsFoundMin;
  if (filter.assetsFoundMax !== undefined) query.assetsFoundMax = filter.assetsFoundMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseDiscoveryJobFilter(params: URLSearchParams): DiscoveryJobFilter {
  const filter: DiscoveryJobFilter = {};
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
  const profileId = params.get('profileId');
  if (profileId) filter.profileId = profileId;
  const startedAtFrom = params.get('startedAtFrom');
  if (startedAtFrom) filter.startedAtFrom = startedAtFrom;
  const startedAtTo = params.get('startedAtTo');
  if (startedAtTo) filter.startedAtTo = startedAtTo;
  const finishedAtFrom = params.get('finishedAtFrom');
  if (finishedAtFrom) filter.finishedAtFrom = finishedAtFrom;
  const finishedAtTo = params.get('finishedAtTo');
  if (finishedAtTo) filter.finishedAtTo = finishedAtTo;
  const jobState = params.get('jobState');
  if (jobState) filter.jobState = jobState.split(',') as DiscoveryJobJobState[];
  const assetsFoundMin = params.get('assetsFoundMin');
  if (assetsFoundMin !== null && assetsFoundMin !== '') filter.assetsFoundMin = Number(assetsFoundMin);
  const assetsFoundMax = params.get('assetsFoundMax');
  if (assetsFoundMax !== null && assetsFoundMax !== '') filter.assetsFoundMax = Number(assetsFoundMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as DiscoveryJobCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeDiscoveryJobFilter(filter: DiscoveryJobFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.profileId) params.set('profileId', filter.profileId);
  if (filter.startedAtFrom) params.set('startedAtFrom', filter.startedAtFrom);
  if (filter.startedAtTo) params.set('startedAtTo', filter.startedAtTo);
  if (filter.finishedAtFrom) params.set('finishedAtFrom', filter.finishedAtFrom);
  if (filter.finishedAtTo) params.set('finishedAtTo', filter.finishedAtTo);
  if (filter.jobState?.length) params.set('jobState', filter.jobState.join(','));
  if (filter.assetsFoundMin !== undefined) params.set('assetsFoundMin', String(filter.assetsFoundMin));
  if (filter.assetsFoundMax !== undefined) params.set('assetsFoundMax', String(filter.assetsFoundMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countDiscoveryJobActiveFilters(filter: DiscoveryJobFilter): number {
  return Array.from(serializeDiscoveryJobFilter(filter).keys()).length;
}
