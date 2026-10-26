import type { LifecycleStatus } from '@/shared/types';
import type { SuppressionRuleFilter, SuppressionRuleCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSuppressionRuleQuery(filter: SuppressionRuleFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.ruleName) query.ruleName = filter.ruleName;
  if (filter.startsAtFrom) query.startsAtFrom = filter.startsAtFrom;
  if (filter.startsAtTo) query.startsAtTo = filter.startsAtTo;
  if (filter.endsAtFrom) query.endsAtFrom = filter.endsAtFrom;
  if (filter.endsAtTo) query.endsAtTo = filter.endsAtTo;
  if (filter.reason) query.reason = filter.reason;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseSuppressionRuleFilter(params: URLSearchParams): SuppressionRuleFilter {
  const filter: SuppressionRuleFilter = {};
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
  const ruleName = params.get('ruleName');
  if (ruleName) filter.ruleName = ruleName;
  const startsAtFrom = params.get('startsAtFrom');
  if (startsAtFrom) filter.startsAtFrom = startsAtFrom;
  const startsAtTo = params.get('startsAtTo');
  if (startsAtTo) filter.startsAtTo = startsAtTo;
  const endsAtFrom = params.get('endsAtFrom');
  if (endsAtFrom) filter.endsAtFrom = endsAtFrom;
  const endsAtTo = params.get('endsAtTo');
  if (endsAtTo) filter.endsAtTo = endsAtTo;
  const reason = params.get('reason');
  if (reason) filter.reason = reason;
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SuppressionRuleCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeSuppressionRuleFilter(filter: SuppressionRuleFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.ruleName) params.set('ruleName', filter.ruleName);
  if (filter.startsAtFrom) params.set('startsAtFrom', filter.startsAtFrom);
  if (filter.startsAtTo) params.set('startsAtTo', filter.startsAtTo);
  if (filter.endsAtFrom) params.set('endsAtFrom', filter.endsAtFrom);
  if (filter.endsAtTo) params.set('endsAtTo', filter.endsAtTo);
  if (filter.reason) params.set('reason', filter.reason);
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countSuppressionRuleActiveFilters(filter: SuppressionRuleFilter): number {
  return Array.from(serializeSuppressionRuleFilter(filter).keys()).length;
}
