import type { LifecycleStatus, SortState } from '@/shared/types';

export const COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ComplianceExceptionLifecycleStage = (typeof COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_VALUES)[number];
export const COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_LABELS: Record<ComplianceExceptionLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_OPTIONS = COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_LABELS[value] }));

export interface ComplianceException {
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
  justification: string;
  approvedBy?: string;
  expiresOn?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ComplianceExceptionLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface ComplianceExceptionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleId: string;
  targetRef: string;
  justification: string;
  approvedBy?: string;
  expiresOn?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ComplianceExceptionLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type ComplianceExceptionFormValues = Partial<ComplianceExceptionInput>;

export interface ComplianceExceptionUpdateInput extends Partial<ComplianceExceptionInput> {
  expectedVersion?: number;
}

export interface ComplianceExceptionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  ruleId?: string;
  targetRef?: string;
  approvedBy?: string;
  expiresOnFrom?: string;
  expiresOnTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ComplianceExceptionLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type ComplianceExceptionSort = SortState<keyof ComplianceException & string>;

export interface ComplianceExceptionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const COMPLIANCEEXCEPTION_PATH = '/api/v1/compliance-exceptions';
export const COMPLIANCEEXCEPTION_TITLE = 'Compliance Exception';
export const COMPLIANCEEXCEPTION_TITLE_PLURAL = 'Compliance Exceptions';

export const COMPLIANCEEXCEPTION_DEFAULTS: ComplianceExceptionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  targetRef: '',
  justification: '',
};
