import type { LifecycleStatus, SortState } from '@/shared/types';

export const RECONCILIATION_RESULT_OUTCOME_VALUES = ['MATCHED', 'CREATED', 'CONFLICT', 'SKIPPED'] as const;
export type ReconciliationResultOutcome = (typeof RECONCILIATION_RESULT_OUTCOME_VALUES)[number];
export const RECONCILIATION_RESULT_OUTCOME_LABELS: Record<ReconciliationResultOutcome, string> = {
  MATCHED: 'Matched',
  CREATED: 'Created',
  CONFLICT: 'Conflict',
  SKIPPED: 'Skipped',
};
export const RECONCILIATION_RESULT_OUTCOME_OPTIONS = RECONCILIATION_RESULT_OUTCOME_VALUES.map((value) => ({ value, label: RECONCILIATION_RESULT_OUTCOME_LABELS[value] }));

export const RECONCILIATION_RESULT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ReconciliationResultCriticality = (typeof RECONCILIATION_RESULT_CRITICALITY_VALUES)[number];
export const RECONCILIATION_RESULT_CRITICALITY_LABELS: Record<ReconciliationResultCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const RECONCILIATION_RESULT_CRITICALITY_OPTIONS = RECONCILIATION_RESULT_CRITICALITY_VALUES.map((value) => ({ value, label: RECONCILIATION_RESULT_CRITICALITY_LABELS[value] }));

export interface ReconciliationResult {
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
  jobId: string;
  ruleId?: string;
  outcome?: ReconciliationResultOutcome;
  diffSummary?: string;
  resolvedBy?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ReconciliationResultCriticality;
  costCenter?: string;
}

export interface ReconciliationResultInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  jobId: string;
  ruleId?: string;
  outcome?: ReconciliationResultOutcome;
  diffSummary?: string;
  resolvedBy?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ReconciliationResultCriticality;
  costCenter?: string;
}

export type ReconciliationResultFormValues = Partial<ReconciliationResultInput>;

export interface ReconciliationResultUpdateInput extends Partial<ReconciliationResultInput> {
  expectedVersion?: number;
}

export interface ReconciliationResultFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  jobId?: string;
  ruleId?: string;
  outcome?: ReconciliationResultOutcome[];
  resolvedBy?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ReconciliationResultCriticality[];
  costCenter?: string;
}

export type ReconciliationResultSort = SortState<keyof ReconciliationResult & string>;

export interface ReconciliationResultSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RECONCILIATIONRESULT_PATH = '/api/v1/reconciliation-results';
export const RECONCILIATIONRESULT_TITLE = 'Reconciliation Result';
export const RECONCILIATIONRESULT_TITLE_PLURAL = 'Reconciliation Results';

export const RECONCILIATIONRESULT_DEFAULTS: ReconciliationResultFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
