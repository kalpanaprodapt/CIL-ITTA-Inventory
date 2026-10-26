import type { LifecycleStatus } from '@/shared/types';
import type { ComplianceExceptionFilter, ComplianceExceptionLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toComplianceExceptionQuery(filter: ComplianceExceptionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.ruleId) query.ruleId = filter.ruleId;
  if (filter.targetRef) query.targetRef = filter.targetRef;
  if (filter.approvedBy) query.approvedBy = filter.approvedBy;
  if (filter.expiresOnFrom) query.expiresOnFrom = filter.expiresOnFrom;
  if (filter.expiresOnTo) query.expiresOnTo = filter.expiresOnTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseComplianceExceptionFilter(params: URLSearchParams): ComplianceExceptionFilter {
  const filter: ComplianceExceptionFilter = {};
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
  const ruleId = params.get('ruleId');
  if (ruleId) filter.ruleId = ruleId;
  const targetRef = params.get('targetRef');
  if (targetRef) filter.targetRef = targetRef;
  const approvedBy = params.get('approvedBy');
  if (approvedBy) filter.approvedBy = approvedBy;
  const expiresOnFrom = params.get('expiresOnFrom');
  if (expiresOnFrom) filter.expiresOnFrom = expiresOnFrom;
  const expiresOnTo = params.get('expiresOnTo');
  if (expiresOnTo) filter.expiresOnTo = expiresOnTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ComplianceExceptionLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeComplianceExceptionFilter(filter: ComplianceExceptionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.ruleId) params.set('ruleId', filter.ruleId);
  if (filter.targetRef) params.set('targetRef', filter.targetRef);
  if (filter.approvedBy) params.set('approvedBy', filter.approvedBy);
  if (filter.expiresOnFrom) params.set('expiresOnFrom', filter.expiresOnFrom);
  if (filter.expiresOnTo) params.set('expiresOnTo', filter.expiresOnTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countComplianceExceptionActiveFilters(filter: ComplianceExceptionFilter): number {
  return Array.from(serializeComplianceExceptionFilter(filter).keys()).length;
}
