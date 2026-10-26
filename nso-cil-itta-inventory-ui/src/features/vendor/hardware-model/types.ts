import type { LifecycleStatus, SortState } from '@/shared/types';

export const HARDWARE_MODEL_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type HardwareModelLifecycleStage = (typeof HARDWARE_MODEL_LIFECYCLE_STAGE_VALUES)[number];
export const HARDWARE_MODEL_LIFECYCLE_STAGE_LABELS: Record<HardwareModelLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const HARDWARE_MODEL_LIFECYCLE_STAGE_OPTIONS = HARDWARE_MODEL_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: HARDWARE_MODEL_LIFECYCLE_STAGE_LABELS[value] }));

export interface HardwareModel {
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
  modelNumber: string;
  formFactor?: string;
  powerWatts?: number;
  eolDate?: string;
  eosDate?: string;
  labels?: string;
  lifecycleStage?: HardwareModelLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface HardwareModelInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vendorId: string;
  modelNumber: string;
  formFactor?: string;
  powerWatts?: number;
  eolDate?: string;
  eosDate?: string;
  labels?: string;
  lifecycleStage?: HardwareModelLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type HardwareModelFormValues = Partial<HardwareModelInput>;

export interface HardwareModelUpdateInput extends Partial<HardwareModelInput> {
  expectedVersion?: number;
}

export interface HardwareModelFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vendorId?: string;
  modelNumber?: string;
  formFactor?: string;
  powerWattsMin?: number;
  powerWattsMax?: number;
  eolDateFrom?: string;
  eolDateTo?: string;
  eosDateFrom?: string;
  eosDateTo?: string;
  labels?: string;
  lifecycleStage?: HardwareModelLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type HardwareModelSort = SortState<keyof HardwareModel & string>;

export interface HardwareModelSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const HARDWAREMODEL_PATH = '/api/v1/hardware-models';
export const HARDWAREMODEL_TITLE = 'Hardware Model';
export const HARDWAREMODEL_TITLE_PLURAL = 'Hardware Models';

export const HARDWAREMODEL_DEFAULTS: HardwareModelFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  modelNumber: '',
};
