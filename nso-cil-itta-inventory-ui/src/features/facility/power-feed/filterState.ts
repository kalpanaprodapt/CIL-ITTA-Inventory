import type { LifecycleStatus } from '@/shared/types';
import type { PowerFeedFilter, PowerFeedFeedType, PowerFeedCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPowerFeedQuery(filter: PowerFeedFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.roomId) query.roomId = filter.roomId;
  if (filter.feedType?.length) query.feedType = filter.feedType.join(',');
  if (filter.voltageMin !== undefined) query.voltageMin = filter.voltageMin;
  if (filter.voltageMax !== undefined) query.voltageMax = filter.voltageMax;
  if (filter.amperageMin !== undefined) query.amperageMin = filter.amperageMin;
  if (filter.amperageMax !== undefined) query.amperageMax = filter.amperageMax;
  if (filter.phase) query.phase = filter.phase;
  if (filter.breakerLabel) query.breakerLabel = filter.breakerLabel;
  if (filter.redundancyGroup) query.redundancyGroup = filter.redundancyGroup;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parsePowerFeedFilter(params: URLSearchParams): PowerFeedFilter {
  const filter: PowerFeedFilter = {};
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
  const roomId = params.get('roomId');
  if (roomId) filter.roomId = roomId;
  const feedType = params.get('feedType');
  if (feedType) filter.feedType = feedType.split(',') as PowerFeedFeedType[];
  const voltageMin = params.get('voltageMin');
  if (voltageMin !== null && voltageMin !== '') filter.voltageMin = Number(voltageMin);
  const voltageMax = params.get('voltageMax');
  if (voltageMax !== null && voltageMax !== '') filter.voltageMax = Number(voltageMax);
  const amperageMin = params.get('amperageMin');
  if (amperageMin !== null && amperageMin !== '') filter.amperageMin = Number(amperageMin);
  const amperageMax = params.get('amperageMax');
  if (amperageMax !== null && amperageMax !== '') filter.amperageMax = Number(amperageMax);
  const phase = params.get('phase');
  if (phase) filter.phase = phase;
  const breakerLabel = params.get('breakerLabel');
  if (breakerLabel) filter.breakerLabel = breakerLabel;
  const redundancyGroup = params.get('redundancyGroup');
  if (redundancyGroup) filter.redundancyGroup = redundancyGroup;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PowerFeedCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializePowerFeedFilter(filter: PowerFeedFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.roomId) params.set('roomId', filter.roomId);
  if (filter.feedType?.length) params.set('feedType', filter.feedType.join(','));
  if (filter.voltageMin !== undefined) params.set('voltageMin', String(filter.voltageMin));
  if (filter.voltageMax !== undefined) params.set('voltageMax', String(filter.voltageMax));
  if (filter.amperageMin !== undefined) params.set('amperageMin', String(filter.amperageMin));
  if (filter.amperageMax !== undefined) params.set('amperageMax', String(filter.amperageMax));
  if (filter.phase) params.set('phase', filter.phase);
  if (filter.breakerLabel) params.set('breakerLabel', filter.breakerLabel);
  if (filter.redundancyGroup) params.set('redundancyGroup', filter.redundancyGroup);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countPowerFeedActiveFilters(filter: PowerFeedFilter): number {
  return Array.from(serializePowerFeedFilter(filter).keys()).length;
}
