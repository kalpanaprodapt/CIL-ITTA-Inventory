import type { LifecycleStatus, SortState } from '@/shared/types';

export const WDM_SYSTEM_BAND_VALUES = ['C', 'L', 'C_L'] as const;
export type WdmSystemBand = (typeof WDM_SYSTEM_BAND_VALUES)[number];
export const WDM_SYSTEM_BAND_LABELS: Record<WdmSystemBand, string> = {
  C: 'C',
  L: 'L',
  C_L: 'C L',
};
export const WDM_SYSTEM_BAND_OPTIONS = WDM_SYSTEM_BAND_VALUES.map((value) => ({ value, label: WDM_SYSTEM_BAND_LABELS[value] }));

export const WDM_SYSTEM_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type WdmSystemLifecycleStage = (typeof WDM_SYSTEM_LIFECYCLE_STAGE_VALUES)[number];
export const WDM_SYSTEM_LIFECYCLE_STAGE_LABELS: Record<WdmSystemLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const WDM_SYSTEM_LIFECYCLE_STAGE_OPTIONS = WDM_SYSTEM_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: WDM_SYSTEM_LIFECYCLE_STAGE_LABELS[value] }));

export interface WdmSystem {
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
  systemName: string;
  channelCapacity: number;
  band?: WdmSystemBand;
  spanCount?: number;
  vendorId?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: WdmSystemLifecycleStage;
}

export interface WdmSystemInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  systemName: string;
  channelCapacity: number;
  band?: WdmSystemBand;
  spanCount?: number;
  vendorId?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: WdmSystemLifecycleStage;
}

export type WdmSystemFormValues = Partial<WdmSystemInput>;

export interface WdmSystemUpdateInput extends Partial<WdmSystemInput> {
  expectedVersion?: number;
}

export interface WdmSystemFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  systemName?: string;
  channelCapacityMin?: number;
  channelCapacityMax?: number;
  band?: WdmSystemBand[];
  spanCountMin?: number;
  spanCountMax?: number;
  vendorId?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: WdmSystemLifecycleStage[];
}

export type WdmSystemSort = SortState<keyof WdmSystem & string>;

export interface WdmSystemSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const WDMSYSTEM_PATH = '/api/v1/wdm-systems';
export const WDMSYSTEM_TITLE = 'Wdm System';
export const WDMSYSTEM_TITLE_PLURAL = 'Wdm Systems';

export const WDMSYSTEM_DEFAULTS: WdmSystemFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  systemName: '',
};
