import type { LifecycleStatus, SortState } from '@/shared/types';

export const CIRCUIT_ORDER_ORDER_STATUS_VALUES = ['DRAFT', 'SUBMITTED', 'CONFIRMED', 'DELIVERED', 'CANCELLED'] as const;
export type CircuitOrderOrderStatus = (typeof CIRCUIT_ORDER_ORDER_STATUS_VALUES)[number];
export const CIRCUIT_ORDER_ORDER_STATUS_LABELS: Record<CircuitOrderOrderStatus, string> = {
  DRAFT: 'Draft',
  SUBMITTED: 'Submitted',
  CONFIRMED: 'Confirmed',
  DELIVERED: 'Delivered',
  CANCELLED: 'Cancelled',
};
export const CIRCUIT_ORDER_ORDER_STATUS_OPTIONS = CIRCUIT_ORDER_ORDER_STATUS_VALUES.map((value) => ({ value, label: CIRCUIT_ORDER_ORDER_STATUS_LABELS[value] }));

export const CIRCUIT_ORDER_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CircuitOrderLifecycleStage = (typeof CIRCUIT_ORDER_LIFECYCLE_STAGE_VALUES)[number];
export const CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS: Record<CircuitOrderLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CIRCUIT_ORDER_LIFECYCLE_STAGE_OPTIONS = CIRCUIT_ORDER_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS[value] }));

export interface CircuitOrder {
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
  circuitId?: string;
  orderNumber: string;
  orderedOn: string;
  requestedDate?: string;
  committedDate?: string;
  orderStatus?: CircuitOrderOrderStatus;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CircuitOrderLifecycleStage;
  sourceSystem?: string;
}

export interface CircuitOrderInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  circuitId?: string;
  orderNumber: string;
  orderedOn: string;
  requestedDate?: string;
  committedDate?: string;
  orderStatus?: CircuitOrderOrderStatus;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CircuitOrderLifecycleStage;
  sourceSystem?: string;
}

export type CircuitOrderFormValues = Partial<CircuitOrderInput>;

export interface CircuitOrderUpdateInput extends Partial<CircuitOrderInput> {
  expectedVersion?: number;
}

export interface CircuitOrderFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  circuitId?: string;
  orderNumber?: string;
  orderedOnFrom?: string;
  orderedOnTo?: string;
  requestedDateFrom?: string;
  requestedDateTo?: string;
  committedDateFrom?: string;
  committedDateTo?: string;
  orderStatus?: CircuitOrderOrderStatus[];
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: CircuitOrderLifecycleStage[];
  sourceSystem?: string;
}

export type CircuitOrderSort = SortState<keyof CircuitOrder & string>;

export interface CircuitOrderSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CIRCUITORDER_PATH = '/api/v1/circuit-orders';
export const CIRCUITORDER_TITLE = 'Circuit Order';
export const CIRCUITORDER_TITLE_PLURAL = 'Circuit Orders';

export const CIRCUITORDER_DEFAULTS: CircuitOrderFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  orderNumber: '',
};
