import type { LifecycleStatus } from '@/shared/types';
import type { CapacityPlanFilter, CapacityPlanLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCapacityPlanQuery(filter: CapacityPlanFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.planName) query.planName = filter.planName;
  if (filter.horizonMonthsMin !== undefined) query.horizonMonthsMin = filter.horizonMonthsMin;
  if (filter.horizonMonthsMax !== undefined) query.horizonMonthsMax = filter.horizonMonthsMax;
  if (filter.targetUtilizationPctMin !== undefined) query.targetUtilizationPctMin = filter.targetUtilizationPctMin;
  if (filter.targetUtilizationPctMax !== undefined) query.targetUtilizationPctMax = filter.targetUtilizationPctMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.approvedOnFrom) query.approvedOnFrom = filter.approvedOnFrom;
  if (filter.approvedOnTo) query.approvedOnTo = filter.approvedOnTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseCapacityPlanFilter(params: URLSearchParams): CapacityPlanFilter {
  const filter: CapacityPlanFilter = {};
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
  const planName = params.get('planName');
  if (planName) filter.planName = planName;
  const horizonMonthsMin = params.get('horizonMonthsMin');
  if (horizonMonthsMin !== null && horizonMonthsMin !== '') filter.horizonMonthsMin = Number(horizonMonthsMin);
  const horizonMonthsMax = params.get('horizonMonthsMax');
  if (horizonMonthsMax !== null && horizonMonthsMax !== '') filter.horizonMonthsMax = Number(horizonMonthsMax);
  const targetUtilizationPctMin = params.get('targetUtilizationPctMin');
  if (targetUtilizationPctMin !== null && targetUtilizationPctMin !== '') filter.targetUtilizationPctMin = Number(targetUtilizationPctMin);
  const targetUtilizationPctMax = params.get('targetUtilizationPctMax');
  if (targetUtilizationPctMax !== null && targetUtilizationPctMax !== '') filter.targetUtilizationPctMax = Number(targetUtilizationPctMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const approvedOnFrom = params.get('approvedOnFrom');
  if (approvedOnFrom) filter.approvedOnFrom = approvedOnFrom;
  const approvedOnTo = params.get('approvedOnTo');
  if (approvedOnTo) filter.approvedOnTo = approvedOnTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CapacityPlanLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeCapacityPlanFilter(filter: CapacityPlanFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.planName) params.set('planName', filter.planName);
  if (filter.horizonMonthsMin !== undefined) params.set('horizonMonthsMin', String(filter.horizonMonthsMin));
  if (filter.horizonMonthsMax !== undefined) params.set('horizonMonthsMax', String(filter.horizonMonthsMax));
  if (filter.targetUtilizationPctMin !== undefined) params.set('targetUtilizationPctMin', String(filter.targetUtilizationPctMin));
  if (filter.targetUtilizationPctMax !== undefined) params.set('targetUtilizationPctMax', String(filter.targetUtilizationPctMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.approvedOnFrom) params.set('approvedOnFrom', filter.approvedOnFrom);
  if (filter.approvedOnTo) params.set('approvedOnTo', filter.approvedOnTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countCapacityPlanActiveFilters(filter: CapacityPlanFilter): number {
  return Array.from(serializeCapacityPlanFilter(filter).keys()).length;
}
