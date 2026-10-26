import type { LifecycleStatus, SortState } from '@/shared/types';

export const WARRANTY_WARRANTY_TYPE_VALUES = ['STANDARD', 'EXTENDED', 'ADVANCED_REPLACEMENT'] as const;
export type WarrantyWarrantyType = (typeof WARRANTY_WARRANTY_TYPE_VALUES)[number];
export const WARRANTY_WARRANTY_TYPE_LABELS: Record<WarrantyWarrantyType, string> = {
  STANDARD: 'Standard',
  EXTENDED: 'Extended',
  ADVANCED_REPLACEMENT: 'Advanced Replacement',
};
export const WARRANTY_WARRANTY_TYPE_OPTIONS = WARRANTY_WARRANTY_TYPE_VALUES.map((value) => ({ value, label: WARRANTY_WARRANTY_TYPE_LABELS[value] }));

export interface Warranty {
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
  assetId: string;
  startDate: string;
  endDate: string;
  warrantyType?: WarrantyWarrantyType;
  terms?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface WarrantyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  assetId: string;
  startDate: string;
  endDate: string;
  warrantyType?: WarrantyWarrantyType;
  terms?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type WarrantyFormValues = Partial<WarrantyInput>;

export interface WarrantyUpdateInput extends Partial<WarrantyInput> {
  expectedVersion?: number;
}

export interface WarrantyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  assetId?: string;
  startDateFrom?: string;
  startDateTo?: string;
  endDateFrom?: string;
  endDateTo?: string;
  warrantyType?: WarrantyWarrantyType[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type WarrantySort = SortState<keyof Warranty & string>;

export interface WarrantySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const WARRANTY_PATH = '/api/v1/warranties';
export const WARRANTY_TITLE = 'Warranty';
export const WARRANTY_TITLE_PLURAL = 'Warrantyes';

export const WARRANTY_DEFAULTS: WarrantyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
