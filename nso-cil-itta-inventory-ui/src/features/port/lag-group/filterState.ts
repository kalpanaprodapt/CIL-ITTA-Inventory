import type { LifecycleStatus } from '@/shared/types';
import type { LagGroupFilter, LagGroupLacpMode, LagGroupLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toLagGroupQuery(filter: LagGroupFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.deviceId) query.deviceId = filter.deviceId;
  if (filter.lagName) query.lagName = filter.lagName;
  if (filter.memberCountMin !== undefined) query.memberCountMin = filter.memberCountMin;
  if (filter.memberCountMax !== undefined) query.memberCountMax = filter.memberCountMax;
  if (filter.lacpMode?.length) query.lacpMode = filter.lacpMode.join(',');
  if (filter.minLinksMin !== undefined) query.minLinksMin = filter.minLinksMin;
  if (filter.minLinksMax !== undefined) query.minLinksMax = filter.minLinksMax;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseLagGroupFilter(params: URLSearchParams): LagGroupFilter {
  const filter: LagGroupFilter = {};
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
  const deviceId = params.get('deviceId');
  if (deviceId) filter.deviceId = deviceId;
  const lagName = params.get('lagName');
  if (lagName) filter.lagName = lagName;
  const memberCountMin = params.get('memberCountMin');
  if (memberCountMin !== null && memberCountMin !== '') filter.memberCountMin = Number(memberCountMin);
  const memberCountMax = params.get('memberCountMax');
  if (memberCountMax !== null && memberCountMax !== '') filter.memberCountMax = Number(memberCountMax);
  const lacpMode = params.get('lacpMode');
  if (lacpMode) filter.lacpMode = lacpMode.split(',') as LagGroupLacpMode[];
  const minLinksMin = params.get('minLinksMin');
  if (minLinksMin !== null && minLinksMin !== '') filter.minLinksMin = Number(minLinksMin);
  const minLinksMax = params.get('minLinksMax');
  if (minLinksMax !== null && minLinksMax !== '') filter.minLinksMax = Number(minLinksMax);
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as LagGroupLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeLagGroupFilter(filter: LagGroupFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.deviceId) params.set('deviceId', filter.deviceId);
  if (filter.lagName) params.set('lagName', filter.lagName);
  if (filter.memberCountMin !== undefined) params.set('memberCountMin', String(filter.memberCountMin));
  if (filter.memberCountMax !== undefined) params.set('memberCountMax', String(filter.memberCountMax));
  if (filter.lacpMode?.length) params.set('lacpMode', filter.lacpMode.join(','));
  if (filter.minLinksMin !== undefined) params.set('minLinksMin', String(filter.minLinksMin));
  if (filter.minLinksMax !== undefined) params.set('minLinksMax', String(filter.minLinksMax));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countLagGroupActiveFilters(filter: LagGroupFilter): number {
  return Array.from(serializeLagGroupFilter(filter).keys()).length;
}
