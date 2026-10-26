import type { LifecycleStatus, SortState } from '@/shared/types';

export const ALARM_DEFINITION_SEVERITY_VALUES = ['INDETERMINATE', 'WARNING', 'MINOR', 'MAJOR', 'CRITICAL'] as const;
export type AlarmDefinitionSeverity = (typeof ALARM_DEFINITION_SEVERITY_VALUES)[number];
export const ALARM_DEFINITION_SEVERITY_LABELS: Record<AlarmDefinitionSeverity, string> = {
  INDETERMINATE: 'Indeterminate',
  WARNING: 'Warning',
  MINOR: 'Minor',
  MAJOR: 'Major',
  CRITICAL: 'Critical',
};
export const ALARM_DEFINITION_SEVERITY_OPTIONS = ALARM_DEFINITION_SEVERITY_VALUES.map((value) => ({ value, label: ALARM_DEFINITION_SEVERITY_LABELS[value] }));

export interface AlarmDefinition {
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
  alarmCode: string;
  severity?: AlarmDefinitionSeverity;
  probableCause?: string;
  autoClear?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface AlarmDefinitionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  alarmCode: string;
  severity?: AlarmDefinitionSeverity;
  probableCause?: string;
  autoClear?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type AlarmDefinitionFormValues = Partial<AlarmDefinitionInput>;

export interface AlarmDefinitionUpdateInput extends Partial<AlarmDefinitionInput> {
  expectedVersion?: number;
}

export interface AlarmDefinitionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  alarmCode?: string;
  severity?: AlarmDefinitionSeverity[];
  probableCause?: string;
  autoClear?: boolean;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type AlarmDefinitionSort = SortState<keyof AlarmDefinition & string>;

export interface AlarmDefinitionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ALARMDEFINITION_PATH = '/api/v1/alarm-definitions';
export const ALARMDEFINITION_TITLE = 'Alarm Definition';
export const ALARMDEFINITION_TITLE_PLURAL = 'Alarm Definitions';

export const ALARMDEFINITION_DEFAULTS: AlarmDefinitionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  alarmCode: '',
  autoClear: false,
};
