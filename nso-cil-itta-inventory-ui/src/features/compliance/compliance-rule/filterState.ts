import type { LifecycleStatus } from '@/shared/types';
import type { ComplianceRuleFilter, ComplianceRuleFramework, ComplianceRuleSeverity, ComplianceRuleCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toComplianceRuleQuery(filter: ComplianceRuleFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.ruleCode) query.ruleCode = filter.ruleCode;
  if (filter.framework?.length) query.framework = filter.framework.join(',');
  if (filter.severity?.length) query.severity = filter.severity.join(',');
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseComplianceRuleFilter(params: URLSearchParams): ComplianceRuleFilter {
  const filter: ComplianceRuleFilter = {};
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
  const ruleCode = params.get('ruleCode');
  if (ruleCode) filter.ruleCode = ruleCode;
  const framework = params.get('framework');
  if (framework) filter.framework = framework.split(',') as ComplianceRuleFramework[];
  const severity = params.get('severity');
  if (severity) filter.severity = severity.split(',') as ComplianceRuleSeverity[];
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ComplianceRuleCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeComplianceRuleFilter(filter: ComplianceRuleFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.ruleCode) params.set('ruleCode', filter.ruleCode);
  if (filter.framework?.length) params.set('framework', filter.framework.join(','));
  if (filter.severity?.length) params.set('severity', filter.severity.join(','));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countComplianceRuleActiveFilters(filter: ComplianceRuleFilter): number {
  return Array.from(serializeComplianceRuleFilter(filter).keys()).length;
}
