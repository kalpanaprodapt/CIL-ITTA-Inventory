import type { LifecycleStatus } from '@/shared/types';
import type { WdmSystemFilter, WdmSystemBand, WdmSystemLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toWdmSystemQuery(filter: WdmSystemFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.systemName) query.systemName = filter.systemName;
  if (filter.channelCapacityMin !== undefined) query.channelCapacityMin = filter.channelCapacityMin;
  if (filter.channelCapacityMax !== undefined) query.channelCapacityMax = filter.channelCapacityMax;
  if (filter.band?.length) query.band = filter.band.join(',');
  if (filter.spanCountMin !== undefined) query.spanCountMin = filter.spanCountMin;
  if (filter.spanCountMax !== undefined) query.spanCountMax = filter.spanCountMax;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseWdmSystemFilter(params: URLSearchParams): WdmSystemFilter {
  const filter: WdmSystemFilter = {};
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
  const systemName = params.get('systemName');
  if (systemName) filter.systemName = systemName;
  const channelCapacityMin = params.get('channelCapacityMin');
  if (channelCapacityMin !== null && channelCapacityMin !== '') filter.channelCapacityMin = Number(channelCapacityMin);
  const channelCapacityMax = params.get('channelCapacityMax');
  if (channelCapacityMax !== null && channelCapacityMax !== '') filter.channelCapacityMax = Number(channelCapacityMax);
  const band = params.get('band');
  if (band) filter.band = band.split(',') as WdmSystemBand[];
  const spanCountMin = params.get('spanCountMin');
  if (spanCountMin !== null && spanCountMin !== '') filter.spanCountMin = Number(spanCountMin);
  const spanCountMax = params.get('spanCountMax');
  if (spanCountMax !== null && spanCountMax !== '') filter.spanCountMax = Number(spanCountMax);
  const vendorId = params.get('vendorId');
  if (vendorId) filter.vendorId = vendorId;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as WdmSystemLifecycleStage[];
  return filter;
}

export function serializeWdmSystemFilter(filter: WdmSystemFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.systemName) params.set('systemName', filter.systemName);
  if (filter.channelCapacityMin !== undefined) params.set('channelCapacityMin', String(filter.channelCapacityMin));
  if (filter.channelCapacityMax !== undefined) params.set('channelCapacityMax', String(filter.channelCapacityMax));
  if (filter.band?.length) params.set('band', filter.band.join(','));
  if (filter.spanCountMin !== undefined) params.set('spanCountMin', String(filter.spanCountMin));
  if (filter.spanCountMax !== undefined) params.set('spanCountMax', String(filter.spanCountMax));
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countWdmSystemActiveFilters(filter: WdmSystemFilter): number {
  return Array.from(serializeWdmSystemFilter(filter).keys()).length;
}
