import type { LifecycleStatus, SortState } from '@/shared/types';

export const COMPLIANCE_CHECK_RESULT_VALUES = ['PASS', 'FAIL', 'WARN', 'NA'] as const;
export type ComplianceCheckResult = (typeof COMPLIANCE_CHECK_RESULT_VALUES)[number];
export const COMPLIANCE_CHECK_RESULT_LABELS: Record<ComplianceCheckResult, string> = {
  PASS: 'Pass',
  FAIL: 'Fail',
  WARN: 'Warn',
  NA: 'Na',
};
export const COMPLIANCE_CHECK_RESULT_OPTIONS = COMPLIANCE_CHECK_RESULT_VALUES.map((value) => ({ value, label: COMPLIANCE_CHECK_RESULT_LABELS[value] }));

export const COMPLIANCE_CHECK_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ComplianceCheckCriticality = (typeof COMPLIANCE_CHECK_CRITICALITY_VALUES)[number];
export const COMPLIANCE_CHECK_CRITICALITY_LABELS: Record<ComplianceCheckCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const COMPLIANCE_CHECK_CRITICALITY_OPTIONS = COMPLIANCE_CHECK_CRITICALITY_VALUES.map((value) => ({ value, label: COMPLIANCE_CHECK_CRITICALITY_LABELS[value] }));

export interface ComplianceCheck {
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
  ruleId: string;
  targetRef: string;
  checkedAt: string;
  result?: ComplianceCheckResult;
  evidence?: string;
  criticality?: ComplianceCheckCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface ComplianceCheckInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleId: string;
  targetRef: string;
  checkedAt: string;
  result?: ComplianceCheckResult;
  evidence?: string;
  criticality?: ComplianceCheckCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type ComplianceCheckFormValues = Partial<ComplianceCheckInput>;

export interface ComplianceCheckUpdateInput extends Partial<ComplianceCheckInput> {
  expectedVersion?: number;
}

export interface ComplianceCheckFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  ruleId?: string;
  targetRef?: string;
  checkedAtFrom?: string;
  checkedAtTo?: string;
  result?: ComplianceCheckResult[];
  criticality?: ComplianceCheckCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type ComplianceCheckSort = SortState<keyof ComplianceCheck & string>;

export interface ComplianceCheckSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const COMPLIANCECHECK_PATH = '/api/v1/compliance-checks';
export const COMPLIANCECHECK_TITLE = 'Compliance Check';
export const COMPLIANCECHECK_TITLE_PLURAL = 'Compliance Checks';

export const COMPLIANCECHECK_DEFAULTS: ComplianceCheckFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  targetRef: '',
};
