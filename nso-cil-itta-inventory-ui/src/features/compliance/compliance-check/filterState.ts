import type { LifecycleStatus } from '@/shared/types';
import type { ComplianceCheckFilter, ComplianceCheckResult, ComplianceCheckCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toComplianceCheckQuery(filter: ComplianceCheckFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.ruleId) query.ruleId = filter.ruleId;
  if (filter.targetRef) query.targetRef = filter.targetRef;
  if (filter.checkedAtFrom) query.checkedAtFrom = filter.checkedAtFrom;
  if (filter.checkedAtTo) query.checkedAtTo = filter.checkedAtTo;
  if (filter.result?.length) query.result = filter.result.join(',');
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseComplianceCheckFilter(params: URLSearchParams): ComplianceCheckFilter {
  const filter: ComplianceCheckFilter = {};
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
  const checkedAtFrom = params.get('checkedAtFrom');
  if (checkedAtFrom) filter.checkedAtFrom = checkedAtFrom;
  const checkedAtTo = params.get('checkedAtTo');
  if (checkedAtTo) filter.checkedAtTo = checkedAtTo;
  const result = params.get('result');
  if (result) filter.result = result.split(',') as ComplianceCheckResult[];
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ComplianceCheckCriticality[];
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
  return filter;
}

export function serializeComplianceCheckFilter(filter: ComplianceCheckFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.ruleId) params.set('ruleId', filter.ruleId);
  if (filter.targetRef) params.set('targetRef', filter.targetRef);
  if (filter.checkedAtFrom) params.set('checkedAtFrom', filter.checkedAtFrom);
  if (filter.checkedAtTo) params.set('checkedAtTo', filter.checkedAtTo);
  if (filter.result?.length) params.set('result', filter.result.join(','));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countComplianceCheckActiveFilters(filter: ComplianceCheckFilter): number {
  return Array.from(serializeComplianceCheckFilter(filter).keys()).length;
}
