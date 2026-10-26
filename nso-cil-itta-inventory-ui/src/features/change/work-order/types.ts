import type { LifecycleStatus, SortState } from '@/shared/types';

export const WORK_ORDER_WORK_STATE_VALUES = ['OPEN', 'ASSIGNED', 'IN_PROGRESS', 'DONE', 'CANCELLED'] as const;
export type WorkOrderWorkState = (typeof WORK_ORDER_WORK_STATE_VALUES)[number];
export const WORK_ORDER_WORK_STATE_LABELS: Record<WorkOrderWorkState, string> = {
  OPEN: 'Open',
  ASSIGNED: 'Assigned',
  IN_PROGRESS: 'In Progress',
  DONE: 'Done',
  CANCELLED: 'Cancelled',
};
export const WORK_ORDER_WORK_STATE_OPTIONS = WORK_ORDER_WORK_STATE_VALUES.map((value) => ({ value, label: WORK_ORDER_WORK_STATE_LABELS[value] }));

export const WORK_ORDER_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type WorkOrderLifecycleStage = (typeof WORK_ORDER_LIFECYCLE_STAGE_VALUES)[number];
export const WORK_ORDER_LIFECYCLE_STAGE_LABELS: Record<WorkOrderLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const WORK_ORDER_LIFECYCLE_STAGE_OPTIONS = WORK_ORDER_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: WORK_ORDER_LIFECYCLE_STAGE_LABELS[value] }));

export const WORK_ORDER_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type WorkOrderCriticality = (typeof WORK_ORDER_CRITICALITY_VALUES)[number];
export const WORK_ORDER_CRITICALITY_LABELS: Record<WorkOrderCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const WORK_ORDER_CRITICALITY_OPTIONS = WORK_ORDER_CRITICALITY_VALUES.map((value) => ({ value, label: WORK_ORDER_CRITICALITY_LABELS[value] }));

export interface WorkOrder {
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
  workOrderNumber: string;
  workType: string;
  assignedTechnicianId?: string;
  dueDate?: string;
  workState?: WorkOrderWorkState;
  lifecycleStage?: WorkOrderLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: WorkOrderCriticality;
}

export interface WorkOrderInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  workOrderNumber: string;
  workType: string;
  assignedTechnicianId?: string;
  dueDate?: string;
  workState?: WorkOrderWorkState;
  lifecycleStage?: WorkOrderLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: WorkOrderCriticality;
}

export type WorkOrderFormValues = Partial<WorkOrderInput>;

export interface WorkOrderUpdateInput extends Partial<WorkOrderInput> {
  expectedVersion?: number;
}

export interface WorkOrderFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  workOrderNumber?: string;
  workType?: string;
  assignedTechnicianId?: string;
  dueDateFrom?: string;
  dueDateTo?: string;
  workState?: WorkOrderWorkState[];
  lifecycleStage?: WorkOrderLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: WorkOrderCriticality[];
}

export type WorkOrderSort = SortState<keyof WorkOrder & string>;

export interface WorkOrderSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const WORKORDER_PATH = '/api/v1/work-orders';
export const WORKORDER_TITLE = 'Work Order';
export const WORKORDER_TITLE_PLURAL = 'Work Orders';

export const WORKORDER_DEFAULTS: WorkOrderFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  workOrderNumber: '',
  workType: '',
};
