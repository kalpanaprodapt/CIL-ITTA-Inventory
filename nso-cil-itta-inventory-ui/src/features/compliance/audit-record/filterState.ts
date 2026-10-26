import type { LifecycleStatus } from '@/shared/types';
import type { AuditRecordFilter, AuditRecordLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAuditRecordQuery(filter: AuditRecordFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.actor) query.actor = filter.actor;
  if (filter.action) query.action = filter.action;
  if (filter.entityRef) query.entityRef = filter.entityRef;
  if (filter.occurredAtFrom) query.occurredAtFrom = filter.occurredAtFrom;
  if (filter.occurredAtTo) query.occurredAtTo = filter.occurredAtTo;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseAuditRecordFilter(params: URLSearchParams): AuditRecordFilter {
  const filter: AuditRecordFilter = {};
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
  const actor = params.get('actor');
  if (actor) filter.actor = actor;
  const action = params.get('action');
  if (action) filter.action = action;
  const entityRef = params.get('entityRef');
  if (entityRef) filter.entityRef = entityRef;
  const occurredAtFrom = params.get('occurredAtFrom');
  if (occurredAtFrom) filter.occurredAtFrom = occurredAtFrom;
  const occurredAtTo = params.get('occurredAtTo');
  if (occurredAtTo) filter.occurredAtTo = occurredAtTo;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as AuditRecordLifecycleStage[];
  return filter;
}

export function serializeAuditRecordFilter(filter: AuditRecordFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.actor) params.set('actor', filter.actor);
  if (filter.action) params.set('action', filter.action);
  if (filter.entityRef) params.set('entityRef', filter.entityRef);
  if (filter.occurredAtFrom) params.set('occurredAtFrom', filter.occurredAtFrom);
  if (filter.occurredAtTo) params.set('occurredAtTo', filter.occurredAtTo);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countAuditRecordActiveFilters(filter: AuditRecordFilter): number {
  return Array.from(serializeAuditRecordFilter(filter).keys()).length;
}
