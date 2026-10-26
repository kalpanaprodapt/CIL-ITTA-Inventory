import type { LifecycleStatus } from '@/shared/types';
import type { AlarmDefinitionFilter, AlarmDefinitionSeverity } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAlarmDefinitionQuery(filter: AlarmDefinitionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.alarmCode) query.alarmCode = filter.alarmCode;
  if (filter.severity?.length) query.severity = filter.severity.join(',');
  if (filter.probableCause) query.probableCause = filter.probableCause;
  if (filter.autoClear !== undefined) query.autoClear = filter.autoClear;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseAlarmDefinitionFilter(params: URLSearchParams): AlarmDefinitionFilter {
  const filter: AlarmDefinitionFilter = {};
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
  const alarmCode = params.get('alarmCode');
  if (alarmCode) filter.alarmCode = alarmCode;
  const severity = params.get('severity');
  if (severity) filter.severity = severity.split(',') as AlarmDefinitionSeverity[];
  const probableCause = params.get('probableCause');
  if (probableCause) filter.probableCause = probableCause;
  const autoClear = params.get('autoClear');
  if (autoClear !== null) filter.autoClear = autoClear === 'true';
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

export function serializeAlarmDefinitionFilter(filter: AlarmDefinitionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.alarmCode) params.set('alarmCode', filter.alarmCode);
  if (filter.severity?.length) params.set('severity', filter.severity.join(','));
  if (filter.probableCause) params.set('probableCause', filter.probableCause);
  if (filter.autoClear !== undefined) params.set('autoClear', String(filter.autoClear));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countAlarmDefinitionActiveFilters(filter: AlarmDefinitionFilter): number {
  return Array.from(serializeAlarmDefinitionFilter(filter).keys()).length;
}
