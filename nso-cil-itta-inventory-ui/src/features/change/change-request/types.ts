import type { LifecycleStatus, SortState } from '@/shared/types';

export const CHANGE_REQUEST_CHANGE_TYPE_VALUES = ['STANDARD', 'NORMAL', 'EMERGENCY'] as const;
export type ChangeRequestChangeType = (typeof CHANGE_REQUEST_CHANGE_TYPE_VALUES)[number];
export const CHANGE_REQUEST_CHANGE_TYPE_LABELS: Record<ChangeRequestChangeType, string> = {
  STANDARD: 'Standard',
  NORMAL: 'Normal',
  EMERGENCY: 'Emergency',
};
export const CHANGE_REQUEST_CHANGE_TYPE_OPTIONS = CHANGE_REQUEST_CHANGE_TYPE_VALUES.map((value) => ({ value, label: CHANGE_REQUEST_CHANGE_TYPE_LABELS[value] }));

export const CHANGE_REQUEST_RISK_LEVEL_VALUES = ['LOW', 'MEDIUM', 'HIGH'] as const;
export type ChangeRequestRiskLevel = (typeof CHANGE_REQUEST_RISK_LEVEL_VALUES)[number];
export const CHANGE_REQUEST_RISK_LEVEL_LABELS: Record<ChangeRequestRiskLevel, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
};
export const CHANGE_REQUEST_RISK_LEVEL_OPTIONS = CHANGE_REQUEST_RISK_LEVEL_VALUES.map((value) => ({ value, label: CHANGE_REQUEST_RISK_LEVEL_LABELS[value] }));

export const CHANGE_REQUEST_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ChangeRequestLifecycleStage = (typeof CHANGE_REQUEST_LIFECYCLE_STAGE_VALUES)[number];
export const CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS: Record<ChangeRequestLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CHANGE_REQUEST_LIFECYCLE_STAGE_OPTIONS = CHANGE_REQUEST_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS[value] }));

export interface ChangeRequest {
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
  changeNumber: string;
  changeType?: ChangeRequestChangeType;
  riskLevel?: ChangeRequestRiskLevel;
  plannedStart?: string;
  plannedEnd?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ChangeRequestLifecycleStage;
}

export interface ChangeRequestInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  changeNumber: string;
  changeType?: ChangeRequestChangeType;
  riskLevel?: ChangeRequestRiskLevel;
  plannedStart?: string;
  plannedEnd?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ChangeRequestLifecycleStage;
}

export type ChangeRequestFormValues = Partial<ChangeRequestInput>;

export interface ChangeRequestUpdateInput extends Partial<ChangeRequestInput> {
  expectedVersion?: number;
}

export interface ChangeRequestFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  changeNumber?: string;
  changeType?: ChangeRequestChangeType[];
  riskLevel?: ChangeRequestRiskLevel[];
  plannedStartFrom?: string;
  plannedStartTo?: string;
  plannedEndFrom?: string;
  plannedEndTo?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ChangeRequestLifecycleStage[];
}

export type ChangeRequestSort = SortState<keyof ChangeRequest & string>;

export interface ChangeRequestSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CHANGEREQUEST_PATH = '/api/v1/change-requests';
export const CHANGEREQUEST_TITLE = 'Change Request';
export const CHANGEREQUEST_TITLE_PLURAL = 'Change Requests';

export const CHANGEREQUEST_DEFAULTS: ChangeRequestFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  changeNumber: '',
};
