import type { LifecycleStatus, SortState } from '@/shared/types';

export const SKU_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SkuCriticality = (typeof SKU_CRITICALITY_VALUES)[number];
export const SKU_CRITICALITY_LABELS: Record<SkuCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SKU_CRITICALITY_OPTIONS = SKU_CRITICALITY_VALUES.map((value) => ({ value, label: SKU_CRITICALITY_LABELS[value] }));

export interface Sku {
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
  modelId: string;
  skuCode: string;
  listPrice?: number;
  currency?: string;
  leadTimeDays?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SkuCriticality;
  costCenter?: string;
}

export interface SkuInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  modelId: string;
  skuCode: string;
  listPrice?: number;
  currency?: string;
  leadTimeDays?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SkuCriticality;
  costCenter?: string;
}

export type SkuFormValues = Partial<SkuInput>;

export interface SkuUpdateInput extends Partial<SkuInput> {
  expectedVersion?: number;
}

export interface SkuFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  modelId?: string;
  skuCode?: string;
  listPriceMin?: number;
  listPriceMax?: number;
  currency?: string;
  leadTimeDaysMin?: number;
  leadTimeDaysMax?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SkuCriticality[];
  costCenter?: string;
}

export type SkuSort = SortState<keyof Sku & string>;

export interface SkuSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SKU_PATH = '/api/v1/skus';
export const SKU_TITLE = 'Sku';
export const SKU_TITLE_PLURAL = 'Skus';

export const SKU_DEFAULTS: SkuFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  skuCode: '',
};
