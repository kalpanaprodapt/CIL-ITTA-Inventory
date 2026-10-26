import type { LifecycleStatus, SortState } from '@/shared/types';

export const LICENSE_KEY_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type LicenseKeyLifecycleStage = (typeof LICENSE_KEY_LIFECYCLE_STAGE_VALUES)[number];
export const LICENSE_KEY_LIFECYCLE_STAGE_LABELS: Record<LicenseKeyLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const LICENSE_KEY_LIFECYCLE_STAGE_OPTIONS = LICENSE_KEY_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: LICENSE_KEY_LIFECYCLE_STAGE_LABELS[value] }));

export interface LicenseKey {
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
  poolId: string;
  keyValue: string;
  feature: string;
  expiresOn?: string;
  assignedDeviceId?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LicenseKeyLifecycleStage;
}

export interface LicenseKeyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  poolId: string;
  keyValue: string;
  feature: string;
  expiresOn?: string;
  assignedDeviceId?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LicenseKeyLifecycleStage;
}

export type LicenseKeyFormValues = Partial<LicenseKeyInput>;

export interface LicenseKeyUpdateInput extends Partial<LicenseKeyInput> {
  expectedVersion?: number;
}

export interface LicenseKeyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  poolId?: string;
  keyValue?: string;
  feature?: string;
  expiresOnFrom?: string;
  expiresOnTo?: string;
  assignedDeviceId?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: LicenseKeyLifecycleStage[];
}

export type LicenseKeySort = SortState<keyof LicenseKey & string>;

export interface LicenseKeySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const LICENSEKEY_PATH = '/api/v1/license-keies';
export const LICENSEKEY_TITLE = 'License Key';
export const LICENSEKEY_TITLE_PLURAL = 'License Keyes';

export const LICENSEKEY_DEFAULTS: LicenseKeyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  keyValue: '',
  feature: '',
};
