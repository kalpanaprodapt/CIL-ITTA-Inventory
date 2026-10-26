import type { LifecycleStatus } from '@/shared/types';
import type { IpPoolFilter, IpPoolPoolType, IpPoolLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toIpPoolQuery(filter: IpPoolFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.subnetId) query.subnetId = filter.subnetId;
  if (filter.rangeStart) query.rangeStart = filter.rangeStart;
  if (filter.rangeEnd) query.rangeEnd = filter.rangeEnd;
  if (filter.poolType?.length) query.poolType = filter.poolType.join(',');
  if (filter.leaseSecondsMin !== undefined) query.leaseSecondsMin = filter.leaseSecondsMin;
  if (filter.leaseSecondsMax !== undefined) query.leaseSecondsMax = filter.leaseSecondsMax;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseIpPoolFilter(params: URLSearchParams): IpPoolFilter {
  const filter: IpPoolFilter = {};
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
  const subnetId = params.get('subnetId');
  if (subnetId) filter.subnetId = subnetId;
  const rangeStart = params.get('rangeStart');
  if (rangeStart) filter.rangeStart = rangeStart;
  const rangeEnd = params.get('rangeEnd');
  if (rangeEnd) filter.rangeEnd = rangeEnd;
  const poolType = params.get('poolType');
  if (poolType) filter.poolType = poolType.split(',') as IpPoolPoolType[];
  const leaseSecondsMin = params.get('leaseSecondsMin');
  if (leaseSecondsMin !== null && leaseSecondsMin !== '') filter.leaseSecondsMin = Number(leaseSecondsMin);
  const leaseSecondsMax = params.get('leaseSecondsMax');
  if (leaseSecondsMax !== null && leaseSecondsMax !== '') filter.leaseSecondsMax = Number(leaseSecondsMax);
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as IpPoolLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeIpPoolFilter(filter: IpPoolFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.subnetId) params.set('subnetId', filter.subnetId);
  if (filter.rangeStart) params.set('rangeStart', filter.rangeStart);
  if (filter.rangeEnd) params.set('rangeEnd', filter.rangeEnd);
  if (filter.poolType?.length) params.set('poolType', filter.poolType.join(','));
  if (filter.leaseSecondsMin !== undefined) params.set('leaseSecondsMin', String(filter.leaseSecondsMin));
  if (filter.leaseSecondsMax !== undefined) params.set('leaseSecondsMax', String(filter.leaseSecondsMax));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countIpPoolActiveFilters(filter: IpPoolFilter): number {
  return Array.from(serializeIpPoolFilter(filter).keys()).length;
}
