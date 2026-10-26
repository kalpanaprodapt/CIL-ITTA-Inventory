import type { LifecycleStatus } from '@/shared/types';
import type { DepreciationScheduleFilter, DepreciationScheduleMethod, DepreciationScheduleLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toDepreciationScheduleQuery(filter: DepreciationScheduleFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.assetId) query.assetId = filter.assetId;
  if (filter.method?.length) query.method = filter.method.join(',');
  if (filter.usefulLifeMonthsMin !== undefined) query.usefulLifeMonthsMin = filter.usefulLifeMonthsMin;
  if (filter.usefulLifeMonthsMax !== undefined) query.usefulLifeMonthsMax = filter.usefulLifeMonthsMax;
  if (filter.salvageValueMin !== undefined) query.salvageValueMin = filter.salvageValueMin;
  if (filter.salvageValueMax !== undefined) query.salvageValueMax = filter.salvageValueMax;
  if (filter.startDateFrom) query.startDateFrom = filter.startDateFrom;
  if (filter.startDateTo) query.startDateTo = filter.startDateTo;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseDepreciationScheduleFilter(params: URLSearchParams): DepreciationScheduleFilter {
  const filter: DepreciationScheduleFilter = {};
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
  const assetId = params.get('assetId');
  if (assetId) filter.assetId = assetId;
  const method = params.get('method');
  if (method) filter.method = method.split(',') as DepreciationScheduleMethod[];
  const usefulLifeMonthsMin = params.get('usefulLifeMonthsMin');
  if (usefulLifeMonthsMin !== null && usefulLifeMonthsMin !== '') filter.usefulLifeMonthsMin = Number(usefulLifeMonthsMin);
  const usefulLifeMonthsMax = params.get('usefulLifeMonthsMax');
  if (usefulLifeMonthsMax !== null && usefulLifeMonthsMax !== '') filter.usefulLifeMonthsMax = Number(usefulLifeMonthsMax);
  const salvageValueMin = params.get('salvageValueMin');
  if (salvageValueMin !== null && salvageValueMin !== '') filter.salvageValueMin = Number(salvageValueMin);
  const salvageValueMax = params.get('salvageValueMax');
  if (salvageValueMax !== null && salvageValueMax !== '') filter.salvageValueMax = Number(salvageValueMax);
  const startDateFrom = params.get('startDateFrom');
  if (startDateFrom) filter.startDateFrom = startDateFrom;
  const startDateTo = params.get('startDateTo');
  if (startDateTo) filter.startDateTo = startDateTo;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as DepreciationScheduleLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeDepreciationScheduleFilter(filter: DepreciationScheduleFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.assetId) params.set('assetId', filter.assetId);
  if (filter.method?.length) params.set('method', filter.method.join(','));
  if (filter.usefulLifeMonthsMin !== undefined) params.set('usefulLifeMonthsMin', String(filter.usefulLifeMonthsMin));
  if (filter.usefulLifeMonthsMax !== undefined) params.set('usefulLifeMonthsMax', String(filter.usefulLifeMonthsMax));
  if (filter.salvageValueMin !== undefined) params.set('salvageValueMin', String(filter.salvageValueMin));
  if (filter.salvageValueMax !== undefined) params.set('salvageValueMax', String(filter.salvageValueMax));
  if (filter.startDateFrom) params.set('startDateFrom', filter.startDateFrom);
  if (filter.startDateTo) params.set('startDateTo', filter.startDateTo);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countDepreciationScheduleActiveFilters(filter: DepreciationScheduleFilter): number {
  return Array.from(serializeDepreciationScheduleFilter(filter).keys()).length;
}
