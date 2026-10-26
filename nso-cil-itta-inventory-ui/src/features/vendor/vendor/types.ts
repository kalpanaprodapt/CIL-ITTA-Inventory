import type { LifecycleStatus, SortState } from '@/shared/types';

export const VENDOR_TIER_VALUES = ['STRATEGIC', 'PREFERRED', 'APPROVED', 'BLOCKED'] as const;
export type VendorTier = (typeof VENDOR_TIER_VALUES)[number];
export const VENDOR_TIER_LABELS: Record<VendorTier, string> = {
  STRATEGIC: 'Strategic',
  PREFERRED: 'Preferred',
  APPROVED: 'Approved',
  BLOCKED: 'Blocked',
};
export const VENDOR_TIER_OPTIONS = VENDOR_TIER_VALUES.map((value) => ({ value, label: VENDOR_TIER_LABELS[value] }));

export const VENDOR_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type VendorLifecycleStage = (typeof VENDOR_LIFECYCLE_STAGE_VALUES)[number];
export const VENDOR_LIFECYCLE_STAGE_LABELS: Record<VendorLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const VENDOR_LIFECYCLE_STAGE_OPTIONS = VENDOR_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: VENDOR_LIFECYCLE_STAGE_LABELS[value] }));

export interface Vendor {
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
  legalName: string;
  supportEmail?: string;
  supportPhone?: string;
  website?: string;
  tier?: VendorTier;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VendorLifecycleStage;
  sourceSystem?: string;
}

export interface VendorInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  legalName: string;
  supportEmail?: string;
  supportPhone?: string;
  website?: string;
  tier?: VendorTier;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VendorLifecycleStage;
  sourceSystem?: string;
}

export type VendorFormValues = Partial<VendorInput>;

export interface VendorUpdateInput extends Partial<VendorInput> {
  expectedVersion?: number;
}

export interface VendorFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  legalName?: string;
  supportEmail?: string;
  supportPhone?: string;
  website?: string;
  tier?: VendorTier[];
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: VendorLifecycleStage[];
  sourceSystem?: string;
}

export type VendorSort = SortState<keyof Vendor & string>;

export interface VendorSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VENDOR_PATH = '/api/v1/vendors';
export const VENDOR_TITLE = 'Vendor';
export const VENDOR_TITLE_PLURAL = 'Vendors';

export const VENDOR_DEFAULTS: VendorFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  legalName: '',
};
