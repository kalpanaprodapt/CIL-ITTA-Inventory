import type { LifecycleStatus } from '@/shared/types';
import type { VlanFilter, VlanVlanType, VlanLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVlanQuery(filter: VlanFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vlanIdMin !== undefined) query.vlanIdMin = filter.vlanIdMin;
  if (filter.vlanIdMax !== undefined) query.vlanIdMax = filter.vlanIdMax;
  if (filter.vlanName) query.vlanName = filter.vlanName;
  if (filter.vlanType?.length) query.vlanType = filter.vlanType.join(',');
  if (filter.poolId) query.poolId = filter.poolId;
  if (filter.stpMode) query.stpMode = filter.stpMode;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseVlanFilter(params: URLSearchParams): VlanFilter {
  const filter: VlanFilter = {};
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
  const vlanIdMin = params.get('vlanIdMin');
  if (vlanIdMin !== null && vlanIdMin !== '') filter.vlanIdMin = Number(vlanIdMin);
  const vlanIdMax = params.get('vlanIdMax');
  if (vlanIdMax !== null && vlanIdMax !== '') filter.vlanIdMax = Number(vlanIdMax);
  const vlanName = params.get('vlanName');
  if (vlanName) filter.vlanName = vlanName;
  const vlanType = params.get('vlanType');
  if (vlanType) filter.vlanType = vlanType.split(',') as VlanVlanType[];
  const poolId = params.get('poolId');
  if (poolId) filter.poolId = poolId;
  const stpMode = params.get('stpMode');
  if (stpMode) filter.stpMode = stpMode;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as VlanLifecycleStage[];
  return filter;
}

export function serializeVlanFilter(filter: VlanFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vlanIdMin !== undefined) params.set('vlanIdMin', String(filter.vlanIdMin));
  if (filter.vlanIdMax !== undefined) params.set('vlanIdMax', String(filter.vlanIdMax));
  if (filter.vlanName) params.set('vlanName', filter.vlanName);
  if (filter.vlanType?.length) params.set('vlanType', filter.vlanType.join(','));
  if (filter.poolId) params.set('poolId', filter.poolId);
  if (filter.stpMode) params.set('stpMode', filter.stpMode);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countVlanActiveFilters(filter: VlanFilter): number {
  return Array.from(serializeVlanFilter(filter).keys()).length;
}
