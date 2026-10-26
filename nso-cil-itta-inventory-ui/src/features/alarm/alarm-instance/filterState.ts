import type { LifecycleStatus } from '@/shared/types';
import type { AlarmInstanceFilter, AlarmInstanceAlarmState, AlarmInstanceLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAlarmInstanceQuery(filter: AlarmInstanceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.definitionId) query.definitionId = filter.definitionId;
  if (filter.sourceRef) query.sourceRef = filter.sourceRef;
  if (filter.raisedAtFrom) query.raisedAtFrom = filter.raisedAtFrom;
  if (filter.raisedAtTo) query.raisedAtTo = filter.raisedAtTo;
  if (filter.clearedAtFrom) query.clearedAtFrom = filter.clearedAtFrom;
  if (filter.clearedAtTo) query.clearedAtTo = filter.clearedAtTo;
  if (filter.ackedBy) query.ackedBy = filter.ackedBy;
  if (filter.alarmState?.length) query.alarmState = filter.alarmState.join(',');
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseAlarmInstanceFilter(params: URLSearchParams): AlarmInstanceFilter {
  const filter: AlarmInstanceFilter = {};
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
  const definitionId = params.get('definitionId');
  if (definitionId) filter.definitionId = definitionId;
  const sourceRef = params.get('sourceRef');
  if (sourceRef) filter.sourceRef = sourceRef;
  const raisedAtFrom = params.get('raisedAtFrom');
  if (raisedAtFrom) filter.raisedAtFrom = raisedAtFrom;
  const raisedAtTo = params.get('raisedAtTo');
  if (raisedAtTo) filter.raisedAtTo = raisedAtTo;
  const clearedAtFrom = params.get('clearedAtFrom');
  if (clearedAtFrom) filter.clearedAtFrom = clearedAtFrom;
  const clearedAtTo = params.get('clearedAtTo');
  if (clearedAtTo) filter.clearedAtTo = clearedAtTo;
  const ackedBy = params.get('ackedBy');
  if (ackedBy) filter.ackedBy = ackedBy;
  const alarmState = params.get('alarmState');
  if (alarmState) filter.alarmState = alarmState.split(',') as AlarmInstanceAlarmState[];
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as AlarmInstanceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeAlarmInstanceFilter(filter: AlarmInstanceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.definitionId) params.set('definitionId', filter.definitionId);
  if (filter.sourceRef) params.set('sourceRef', filter.sourceRef);
  if (filter.raisedAtFrom) params.set('raisedAtFrom', filter.raisedAtFrom);
  if (filter.raisedAtTo) params.set('raisedAtTo', filter.raisedAtTo);
  if (filter.clearedAtFrom) params.set('clearedAtFrom', filter.clearedAtFrom);
  if (filter.clearedAtTo) params.set('clearedAtTo', filter.clearedAtTo);
  if (filter.ackedBy) params.set('ackedBy', filter.ackedBy);
  if (filter.alarmState?.length) params.set('alarmState', filter.alarmState.join(','));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countAlarmInstanceActiveFilters(filter: AlarmInstanceFilter): number {
  return Array.from(serializeAlarmInstanceFilter(filter).keys()).length;
}
