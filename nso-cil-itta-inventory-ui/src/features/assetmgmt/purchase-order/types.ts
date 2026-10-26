import type { LifecycleStatus, SortState } from '@/shared/types';

export const PURCHASE_ORDER_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PurchaseOrderCriticality = (typeof PURCHASE_ORDER_CRITICALITY_VALUES)[number];
export const PURCHASE_ORDER_CRITICALITY_LABELS: Record<PurchaseOrderCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const PURCHASE_ORDER_CRITICALITY_OPTIONS = PURCHASE_ORDER_CRITICALITY_VALUES.map((value) => ({ value, label: PURCHASE_ORDER_CRITICALITY_LABELS[value] }));

export interface PurchaseOrder {
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
  poNumber: string;
  vendorId: string;
  orderedOn: string;
  totalAmount: number;
  currency?: string;
  owner?: string;
  criticality?: PurchaseOrderCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface PurchaseOrderInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  poNumber: string;
  vendorId: string;
  orderedOn: string;
  totalAmount: number;
  currency?: string;
  owner?: string;
  criticality?: PurchaseOrderCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type PurchaseOrderFormValues = Partial<PurchaseOrderInput>;

export interface PurchaseOrderUpdateInput extends Partial<PurchaseOrderInput> {
  expectedVersion?: number;
}

export interface PurchaseOrderFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  poNumber?: string;
  vendorId?: string;
  orderedOnFrom?: string;
  orderedOnTo?: string;
  totalAmountMin?: number;
  totalAmountMax?: number;
  currency?: string;
  owner?: string;
  criticality?: PurchaseOrderCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type PurchaseOrderSort = SortState<keyof PurchaseOrder & string>;

export interface PurchaseOrderSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PURCHASEORDER_PATH = '/api/v1/purchase-orders';
export const PURCHASEORDER_TITLE = 'Purchase Order';
export const PURCHASEORDER_TITLE_PLURAL = 'Purchase Orders';

export const PURCHASEORDER_DEFAULTS: PurchaseOrderFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  poNumber: '',
};
