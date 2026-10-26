import type { LifecycleStatus } from '@/shared/types';
import type { SyncMappingFilter } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSyncMappingQuery(filter: SyncMappingFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.jobId) query.jobId = filter.jobId;
  if (filter.sourceField) query.sourceField = filter.sourceField;
  if (filter.targetField) query.targetField = filter.targetField;
  if (filter.transform) query.transform = filter.transform;
  if (filter.required !== undefined) query.required = filter.required;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseSyncMappingFilter(params: URLSearchParams): SyncMappingFilter {
  const filter: SyncMappingFilter = {};
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
  const jobId = params.get('jobId');
  if (jobId) filter.jobId = jobId;
  const sourceField = params.get('sourceField');
  if (sourceField) filter.sourceField = sourceField;
  const targetField = params.get('targetField');
  if (targetField) filter.targetField = targetField;
  const transform = params.get('transform');
  if (transform) filter.transform = transform;
  const required = params.get('required');
  if (required !== null) filter.required = required === 'true';
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

export function serializeSyncMappingFilter(filter: SyncMappingFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.jobId) params.set('jobId', filter.jobId);
  if (filter.sourceField) params.set('sourceField', filter.sourceField);
  if (filter.targetField) params.set('targetField', filter.targetField);
  if (filter.transform) params.set('transform', filter.transform);
  if (filter.required !== undefined) params.set('required', String(filter.required));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countSyncMappingActiveFilters(filter: SyncMappingFilter): number {
  return Array.from(serializeSyncMappingFilter(filter).keys()).length;
}
