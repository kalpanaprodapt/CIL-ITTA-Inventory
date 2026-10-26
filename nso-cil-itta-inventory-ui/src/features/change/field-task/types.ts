import type { LifecycleStatus, SortState } from '@/shared/types';

export const FIELD_TASK_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type FieldTaskCriticality = (typeof FIELD_TASK_CRITICALITY_VALUES)[number];
export const FIELD_TASK_CRITICALITY_LABELS: Record<FieldTaskCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const FIELD_TASK_CRITICALITY_OPTIONS = FIELD_TASK_CRITICALITY_VALUES.map((value) => ({ value, label: FIELD_TASK_CRITICALITY_LABELS[value] }));

export interface FieldTask {
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
  workOrderId: string;
  taskName: string;
  sequence: number;
  durationMinutes?: number;
  completedOn?: string;
  region?: string;
  owner?: string;
  criticality?: FieldTaskCriticality;
  costCenter?: string;
  notes?: string;
}

export interface FieldTaskInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  workOrderId: string;
  taskName: string;
  sequence: number;
  durationMinutes?: number;
  completedOn?: string;
  region?: string;
  owner?: string;
  criticality?: FieldTaskCriticality;
  costCenter?: string;
  notes?: string;
}

export type FieldTaskFormValues = Partial<FieldTaskInput>;

export interface FieldTaskUpdateInput extends Partial<FieldTaskInput> {
  expectedVersion?: number;
}

export interface FieldTaskFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  workOrderId?: string;
  taskName?: string;
  sequenceMin?: number;
  sequenceMax?: number;
  durationMinutesMin?: number;
  durationMinutesMax?: number;
  completedOnFrom?: string;
  completedOnTo?: string;
  region?: string;
  owner?: string;
  criticality?: FieldTaskCriticality[];
  costCenter?: string;
}

export type FieldTaskSort = SortState<keyof FieldTask & string>;

export interface FieldTaskSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const FIELDTASK_PATH = '/api/v1/field-tasks';
export const FIELDTASK_TITLE = 'Field Task';
export const FIELDTASK_TITLE_PLURAL = 'Field Tasks';

export const FIELDTASK_DEFAULTS: FieldTaskFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  taskName: '',
};
