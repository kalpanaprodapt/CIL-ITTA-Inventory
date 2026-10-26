import type { LifecycleStatus } from '@/shared/types';
import type { BackhaulLinkFilter, BackhaulLinkLinkMedium } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toBackhaulLinkQuery(filter: BackhaulLinkFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.baseStationId) query.baseStationId = filter.baseStationId;
  if (filter.linkMedium?.length) query.linkMedium = filter.linkMedium.join(',');
  if (filter.capacityMbpsMin !== undefined) query.capacityMbpsMin = filter.capacityMbpsMin;
  if (filter.capacityMbpsMax !== undefined) query.capacityMbpsMax = filter.capacityMbpsMax;
  if (filter.latencyMsMin !== undefined) query.latencyMsMin = filter.latencyMsMin;
  if (filter.latencyMsMax !== undefined) query.latencyMsMax = filter.latencyMsMax;
  if (filter.circuitRef) query.circuitRef = filter.circuitRef;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseBackhaulLinkFilter(params: URLSearchParams): BackhaulLinkFilter {
  const filter: BackhaulLinkFilter = {};
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
  const baseStationId = params.get('baseStationId');
  if (baseStationId) filter.baseStationId = baseStationId;
  const linkMedium = params.get('linkMedium');
  if (linkMedium) filter.linkMedium = linkMedium.split(',') as BackhaulLinkLinkMedium[];
  const capacityMbpsMin = params.get('capacityMbpsMin');
  if (capacityMbpsMin !== null && capacityMbpsMin !== '') filter.capacityMbpsMin = Number(capacityMbpsMin);
  const capacityMbpsMax = params.get('capacityMbpsMax');
  if (capacityMbpsMax !== null && capacityMbpsMax !== '') filter.capacityMbpsMax = Number(capacityMbpsMax);
  const latencyMsMin = params.get('latencyMsMin');
  if (latencyMsMin !== null && latencyMsMin !== '') filter.latencyMsMin = Number(latencyMsMin);
  const latencyMsMax = params.get('latencyMsMax');
  if (latencyMsMax !== null && latencyMsMax !== '') filter.latencyMsMax = Number(latencyMsMax);
  const circuitRef = params.get('circuitRef');
  if (circuitRef) filter.circuitRef = circuitRef;
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
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  return filter;
}

export function serializeBackhaulLinkFilter(filter: BackhaulLinkFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.baseStationId) params.set('baseStationId', filter.baseStationId);
  if (filter.linkMedium?.length) params.set('linkMedium', filter.linkMedium.join(','));
  if (filter.capacityMbpsMin !== undefined) params.set('capacityMbpsMin', String(filter.capacityMbpsMin));
  if (filter.capacityMbpsMax !== undefined) params.set('capacityMbpsMax', String(filter.capacityMbpsMax));
  if (filter.latencyMsMin !== undefined) params.set('latencyMsMin', String(filter.latencyMsMin));
  if (filter.latencyMsMax !== undefined) params.set('latencyMsMax', String(filter.latencyMsMax));
  if (filter.circuitRef) params.set('circuitRef', filter.circuitRef);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countBackhaulLinkActiveFilters(filter: BackhaulLinkFilter): number {
  return Array.from(serializeBackhaulLinkFilter(filter).keys()).length;
}
