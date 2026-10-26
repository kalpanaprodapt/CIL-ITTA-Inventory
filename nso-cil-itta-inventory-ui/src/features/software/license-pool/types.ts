import type { LifecycleStatus, SortState } from '@/shared/types';

export const LICENSE_POOL_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type LicensePoolLifecycleStage = (typeof LICENSE_POOL_LIFECYCLE_STAGE_VALUES)[number];
export const LICENSE_POOL_LIFECYCLE_STAGE_LABELS: Record<LicensePoolLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const LICENSE_POOL_LIFECYCLE_STAGE_OPTIONS = LICENSE_POOL_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: LICENSE_POOL_LIFECYCLE_STAGE_LABELS[value] }));

export interface LicensePool {
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
  vendorId: string;
  poolName: string;
  totalSeats: number;
  usedSeats?: number;
  renewalDate?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LicensePoolLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface LicensePoolInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vendorId: string;
  poolName: string;
  totalSeats: number;
  usedSeats?: number;
  renewalDate?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LicensePoolLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type LicensePoolFormValues = Partial<LicensePoolInput>;

export interface LicensePoolUpdateInput extends Partial<LicensePoolInput> {
  expectedVersion?: number;
}

export interface LicensePoolFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vendorId?: string;
  poolName?: string;
  totalSeatsMin?: number;
  totalSeatsMax?: number;
  usedSeatsMin?: number;
  usedSeatsMax?: number;
  renewalDateFrom?: string;
  renewalDateTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: LicensePoolLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type LicensePoolSort = SortState<keyof LicensePool & string>;

export interface LicensePoolSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const LICENSEPOOL_PATH = '/api/v1/license-pools';
export const LICENSEPOOL_TITLE = 'License Pool';
export const LICENSEPOOL_TITLE_PLURAL = 'License Pools';

export const LICENSEPOOL_DEFAULTS: LicensePoolFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  poolName: '',
};
