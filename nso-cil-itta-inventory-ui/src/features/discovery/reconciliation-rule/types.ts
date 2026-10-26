import type { LifecycleStatus, SortState } from '@/shared/types';

export const RECONCILIATION_RULE_ACTION_VALUES = ['MERGE', 'CREATE', 'IGNORE', 'REVIEW'] as const;
export type ReconciliationRuleAction = (typeof RECONCILIATION_RULE_ACTION_VALUES)[number];
export const RECONCILIATION_RULE_ACTION_LABELS: Record<ReconciliationRuleAction, string> = {
  MERGE: 'Merge',
  CREATE: 'Create',
  IGNORE: 'Ignore',
  REVIEW: 'Review',
};
export const RECONCILIATION_RULE_ACTION_OPTIONS = RECONCILIATION_RULE_ACTION_VALUES.map((value) => ({ value, label: RECONCILIATION_RULE_ACTION_LABELS[value] }));

export const RECONCILIATION_RULE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ReconciliationRuleLifecycleStage = (typeof RECONCILIATION_RULE_LIFECYCLE_STAGE_VALUES)[number];
export const RECONCILIATION_RULE_LIFECYCLE_STAGE_LABELS: Record<ReconciliationRuleLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const RECONCILIATION_RULE_LIFECYCLE_STAGE_OPTIONS = RECONCILIATION_RULE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: RECONCILIATION_RULE_LIFECYCLE_STAGE_LABELS[value] }));

export interface ReconciliationRule {
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
  matchKey: string;
  action?: ReconciliationRuleAction;
  priority: number;
  expression?: string;
  labels?: string;
  lifecycleStage?: ReconciliationRuleLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface ReconciliationRuleInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleName: string;
  matchKey: string;
  action?: ReconciliationRuleAction;
  priority: number;
  expression?: string;
  labels?: string;
  lifecycleStage?: ReconciliationRuleLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type ReconciliationRuleFormValues = Partial<ReconciliationRuleInput>;

export interface ReconciliationRuleUpdateInput extends Partial<ReconciliationRuleInput> {
  expectedVersion?: number;
}

export interface ReconciliationRuleFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  ruleName?: string;
  matchKey?: string;
  action?: ReconciliationRuleAction[];
  priorityMin?: number;
  priorityMax?: number;
  labels?: string;
  lifecycleStage?: ReconciliationRuleLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type ReconciliationRuleSort = SortState<keyof ReconciliationRule & string>;

export interface ReconciliationRuleSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RECONCILIATIONRULE_PATH = '/api/v1/reconciliation-rules';
export const RECONCILIATIONRULE_TITLE = 'Reconciliation Rule';
export const RECONCILIATIONRULE_TITLE_PLURAL = 'Reconciliation Rulees';

export const RECONCILIATIONRULE_DEFAULTS: ReconciliationRuleFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  ruleName: '',
  matchKey: '',
};
