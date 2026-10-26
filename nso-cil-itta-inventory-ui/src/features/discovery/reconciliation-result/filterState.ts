import type { LifecycleStatus } from '@/shared/types';
import type { ReconciliationResultFilter, ReconciliationResultOutcome, ReconciliationResultCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toReconciliationResultQuery(filter: ReconciliationResultFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.jobId) query.jobId = filter.jobId;
  if (filter.ruleId) query.ruleId = filter.ruleId;
  if (filter.outcome?.length) query.outcome = filter.outcome.join(',');
  if (filter.resolvedBy) query.resolvedBy = filter.resolvedBy;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseReconciliationResultFilter(params: URLSearchParams): ReconciliationResultFilter {
  const filter: ReconciliationResultFilter = {};
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
  const ruleId = params.get('ruleId');
  if (ruleId) filter.ruleId = ruleId;
  const outcome = params.get('outcome');
  if (outcome) filter.outcome = outcome.split(',') as ReconciliationResultOutcome[];
  const resolvedBy = params.get('resolvedBy');
  if (resolvedBy) filter.resolvedBy = resolvedBy;
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ReconciliationResultCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeReconciliationResultFilter(filter: ReconciliationResultFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.jobId) params.set('jobId', filter.jobId);
  if (filter.ruleId) params.set('ruleId', filter.ruleId);
  if (filter.outcome?.length) params.set('outcome', filter.outcome.join(','));
  if (filter.resolvedBy) params.set('resolvedBy', filter.resolvedBy);
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countReconciliationResultActiveFilters(filter: ReconciliationResultFilter): number {
  return Array.from(serializeReconciliationResultFilter(filter).keys()).length;
}
