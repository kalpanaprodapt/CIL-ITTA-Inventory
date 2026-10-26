import type { LifecycleStatus, SortState } from '@/shared/types';

export const SERVICE_ORDER_ORDER_TYPE_VALUES = ['NEW', 'CHANGE', 'DISCONNECT', 'MOVE'] as const;
export type ServiceOrderOrderType = (typeof SERVICE_ORDER_ORDER_TYPE_VALUES)[number];
export const SERVICE_ORDER_ORDER_TYPE_LABELS: Record<ServiceOrderOrderType, string> = {
  NEW: 'New',
  CHANGE: 'Change',
  DISCONNECT: 'Disconnect',
  MOVE: 'Move',
};
export const SERVICE_ORDER_ORDER_TYPE_OPTIONS = SERVICE_ORDER_ORDER_TYPE_VALUES.map((value) => ({ value, label: SERVICE_ORDER_ORDER_TYPE_LABELS[value] }));

export const SERVICE_ORDER_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ServiceOrderCriticality = (typeof SERVICE_ORDER_CRITICALITY_VALUES)[number];
export const SERVICE_ORDER_CRITICALITY_LABELS: Record<ServiceOrderCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SERVICE_ORDER_CRITICALITY_OPTIONS = SERVICE_ORDER_CRITICALITY_VALUES.map((value) => ({ value, label: SERVICE_ORDER_CRITICALITY_LABELS[value] }));

export interface ServiceOrder {
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
  customerId: string;
  orderType?: ServiceOrderOrderType;
  requestedOn: string;
  targetDate?: string;
  priority?: number;
  region?: string;
  owner?: string;
  criticality?: ServiceOrderCriticality;
  costCenter?: string;
  notes?: string;
}

export interface ServiceOrderInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  customerId: string;
  orderType?: ServiceOrderOrderType;
  requestedOn: string;
  targetDate?: string;
  priority?: number;
  region?: string;
  owner?: string;
  criticality?: ServiceOrderCriticality;
  costCenter?: string;
  notes?: string;
}

export type ServiceOrderFormValues = Partial<ServiceOrderInput>;

export interface ServiceOrderUpdateInput extends Partial<ServiceOrderInput> {
  expectedVersion?: number;
}

export interface ServiceOrderFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  customerId?: string;
  orderType?: ServiceOrderOrderType[];
  requestedOnFrom?: string;
  requestedOnTo?: string;
  targetDateFrom?: string;
  targetDateTo?: string;
  priorityMin?: number;
  priorityMax?: number;
  region?: string;
  owner?: string;
  criticality?: ServiceOrderCriticality[];
  costCenter?: string;
}

export type ServiceOrderSort = SortState<keyof ServiceOrder & string>;

export interface ServiceOrderSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SERVICEORDER_PATH = '/api/v1/service-orders';
export const SERVICEORDER_TITLE = 'Service Order';
export const SERVICEORDER_TITLE_PLURAL = 'Service Orders';

export const SERVICEORDER_DEFAULTS: ServiceOrderFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
