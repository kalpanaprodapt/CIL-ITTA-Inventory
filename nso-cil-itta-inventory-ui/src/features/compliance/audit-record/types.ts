import type { LifecycleStatus, SortState } from '@/shared/types';

export const AUDIT_RECORD_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type AuditRecordLifecycleStage = (typeof AUDIT_RECORD_LIFECYCLE_STAGE_VALUES)[number];
export const AUDIT_RECORD_LIFECYCLE_STAGE_LABELS: Record<AuditRecordLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const AUDIT_RECORD_LIFECYCLE_STAGE_OPTIONS = AUDIT_RECORD_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: AUDIT_RECORD_LIFECYCLE_STAGE_LABELS[value] }));

export interface AuditRecord {
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
  actor: string;
  action: string;
  entityRef: string;
  occurredAt: string;
  beforeState?: string;
  afterState?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: AuditRecordLifecycleStage;
}

export interface AuditRecordInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  actor: string;
  action: string;
  entityRef: string;
  occurredAt: string;
  beforeState?: string;
  afterState?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: AuditRecordLifecycleStage;
}

export type AuditRecordFormValues = Partial<AuditRecordInput>;

export interface AuditRecordUpdateInput extends Partial<AuditRecordInput> {
  expectedVersion?: number;
}

export interface AuditRecordFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  actor?: string;
  action?: string;
  entityRef?: string;
  occurredAtFrom?: string;
  occurredAtTo?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: AuditRecordLifecycleStage[];
}

export type AuditRecordSort = SortState<keyof AuditRecord & string>;

export interface AuditRecordSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const AUDITRECORD_PATH = '/api/v1/audit-records';
export const AUDITRECORD_TITLE = 'Audit Record';
export const AUDITRECORD_TITLE_PLURAL = 'Audit Records';

export const AUDITRECORD_DEFAULTS: AuditRecordFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  actor: '',
  action: '',
  entityRef: '',
};
