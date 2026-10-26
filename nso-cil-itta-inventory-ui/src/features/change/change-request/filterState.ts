import type { LifecycleStatus } from '@/shared/types';
import type { ChangeRequestFilter, ChangeRequestChangeType, ChangeRequestRiskLevel, ChangeRequestLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toChangeRequestQuery(filter: ChangeRequestFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.changeNumber) query.changeNumber = filter.changeNumber;
  if (filter.changeType?.length) query.changeType = filter.changeType.join(',');
  if (filter.riskLevel?.length) query.riskLevel = filter.riskLevel.join(',');
  if (filter.plannedStartFrom) query.plannedStartFrom = filter.plannedStartFrom;
  if (filter.plannedStartTo) query.plannedStartTo = filter.plannedStartTo;
  if (filter.plannedEndFrom) query.plannedEndFrom = filter.plannedEndFrom;
  if (filter.plannedEndTo) query.plannedEndTo = filter.plannedEndTo;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseChangeRequestFilter(params: URLSearchParams): ChangeRequestFilter {
  const filter: ChangeRequestFilter = {};
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
  const changeNumber = params.get('changeNumber');
  if (changeNumber) filter.changeNumber = changeNumber;
  const changeType = params.get('changeType');
  if (changeType) filter.changeType = changeType.split(',') as ChangeRequestChangeType[];
  const riskLevel = params.get('riskLevel');
  if (riskLevel) filter.riskLevel = riskLevel.split(',') as ChangeRequestRiskLevel[];
  const plannedStartFrom = params.get('plannedStartFrom');
  if (plannedStartFrom) filter.plannedStartFrom = plannedStartFrom;
  const plannedStartTo = params.get('plannedStartTo');
  if (plannedStartTo) filter.plannedStartTo = plannedStartTo;
  const plannedEndFrom = params.get('plannedEndFrom');
  if (plannedEndFrom) filter.plannedEndFrom = plannedEndFrom;
  const plannedEndTo = params.get('plannedEndTo');
  if (plannedEndTo) filter.plannedEndTo = plannedEndTo;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ChangeRequestLifecycleStage[];
  return filter;
}

export function serializeChangeRequestFilter(filter: ChangeRequestFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.changeNumber) params.set('changeNumber', filter.changeNumber);
  if (filter.changeType?.length) params.set('changeType', filter.changeType.join(','));
  if (filter.riskLevel?.length) params.set('riskLevel', filter.riskLevel.join(','));
  if (filter.plannedStartFrom) params.set('plannedStartFrom', filter.plannedStartFrom);
  if (filter.plannedStartTo) params.set('plannedStartTo', filter.plannedStartTo);
  if (filter.plannedEndFrom) params.set('plannedEndFrom', filter.plannedEndFrom);
  if (filter.plannedEndTo) params.set('plannedEndTo', filter.plannedEndTo);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countChangeRequestActiveFilters(filter: ChangeRequestFilter): number {
  return Array.from(serializeChangeRequestFilter(filter).keys()).length;
}
