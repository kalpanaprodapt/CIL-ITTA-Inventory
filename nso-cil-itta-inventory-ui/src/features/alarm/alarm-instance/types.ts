import type { LifecycleStatus, SortState } from '@/shared/types';

export const ALARM_INSTANCE_ALARM_STATE_VALUES = ['ACTIVE', 'ACKED', 'CLEARED'] as const;
export type AlarmInstanceAlarmState = (typeof ALARM_INSTANCE_ALARM_STATE_VALUES)[number];
export const ALARM_INSTANCE_ALARM_STATE_LABELS: Record<AlarmInstanceAlarmState, string> = {
  ACTIVE: 'Active',
  ACKED: 'Acked',
  CLEARED: 'Cleared',
};
export const ALARM_INSTANCE_ALARM_STATE_OPTIONS = ALARM_INSTANCE_ALARM_STATE_VALUES.map((value) => ({ value, label: ALARM_INSTANCE_ALARM_STATE_LABELS[value] }));

export const ALARM_INSTANCE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type AlarmInstanceLifecycleStage = (typeof ALARM_INSTANCE_LIFECYCLE_STAGE_VALUES)[number];
export const ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS: Record<AlarmInstanceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const ALARM_INSTANCE_LIFECYCLE_STAGE_OPTIONS = ALARM_INSTANCE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS[value] }));

export interface AlarmInstance {
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
  definitionId: string;
  sourceRef: string;
  raisedAt: string;
  clearedAt?: string;
  ackedBy?: string;
  alarmState?: AlarmInstanceAlarmState;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: AlarmInstanceLifecycleStage;
  sourceSystem?: string;
}

export interface AlarmInstanceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  definitionId: string;
  sourceRef: string;
  raisedAt: string;
  clearedAt?: string;
  ackedBy?: string;
  alarmState?: AlarmInstanceAlarmState;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: AlarmInstanceLifecycleStage;
  sourceSystem?: string;
}

export type AlarmInstanceFormValues = Partial<AlarmInstanceInput>;

export interface AlarmInstanceUpdateInput extends Partial<AlarmInstanceInput> {
  expectedVersion?: number;
}

export interface AlarmInstanceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  definitionId?: string;
  sourceRef?: string;
  raisedAtFrom?: string;
  raisedAtTo?: string;
  clearedAtFrom?: string;
  clearedAtTo?: string;
  ackedBy?: string;
  alarmState?: AlarmInstanceAlarmState[];
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: AlarmInstanceLifecycleStage[];
  sourceSystem?: string;
}

export type AlarmInstanceSort = SortState<keyof AlarmInstance & string>;

export interface AlarmInstanceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ALARMINSTANCE_PATH = '/api/v1/alarm-instances';
export const ALARMINSTANCE_TITLE = 'Alarm Instance';
export const ALARMINSTANCE_TITLE_PLURAL = 'Alarm Instancees';

export const ALARMINSTANCE_DEFAULTS: AlarmInstanceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  sourceRef: '',
};
