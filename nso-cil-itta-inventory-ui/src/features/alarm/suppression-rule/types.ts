import type { LifecycleStatus, SortState } from '@/shared/types';

export const SUPPRESSION_RULE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SuppressionRuleCriticality = (typeof SUPPRESSION_RULE_CRITICALITY_VALUES)[number];
export const SUPPRESSION_RULE_CRITICALITY_LABELS: Record<SuppressionRuleCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SUPPRESSION_RULE_CRITICALITY_OPTIONS = SUPPRESSION_RULE_CRITICALITY_VALUES.map((value) => ({ value, label: SUPPRESSION_RULE_CRITICALITY_LABELS[value] }));

export interface SuppressionRule {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleName: string;
  matchExpression: string;
  startsAt?: string;
  endsAt?: string;
  reason?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SuppressionRuleCriticality;
  costCenter?: string;
}

export interface SuppressionRuleInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleName: string;
  matchExpression: string;
  startsAt?: string;
  endsAt?: string;
  reason?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SuppressionRuleCriticality;
  costCenter?: string;
}

export type SuppressionRuleFormValues = Partial<SuppressionRuleInput>;

export interface SuppressionRuleUpdateInput extends Partial<SuppressionRuleInput> {
  expectedVersion?: number;
}

export interface SuppressionRuleFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  ruleName?: string;
  startsAtFrom?: string;
  startsAtTo?: string;
  endsAtFrom?: string;
  endsAtTo?: string;
  reason?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SuppressionRuleCriticality[];
  costCenter?: string;
}

export type SuppressionRuleSort = SortState<keyof SuppressionRule & string>;

export interface SuppressionRuleSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SUPPRESSIONRULE_PATH = '/api/v1/suppression-rules';
export const SUPPRESSIONRULE_TITLE = 'Suppression Rule';
export const SUPPRESSIONRULE_TITLE_PLURAL = 'Suppression Rulees';

export const SUPPRESSIONRULE_DEFAULTS: SuppressionRuleFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  ruleName: '',
  matchExpression: '',
};
