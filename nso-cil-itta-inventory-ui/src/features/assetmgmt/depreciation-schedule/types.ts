import type { LifecycleStatus, SortState } from '@/shared/types';

export const DEPRECIATION_SCHEDULE_METHOD_VALUES = ['STRAIGHT_LINE', 'DECLINING', 'UNITS'] as const;
export type DepreciationScheduleMethod = (typeof DEPRECIATION_SCHEDULE_METHOD_VALUES)[number];
export const DEPRECIATION_SCHEDULE_METHOD_LABELS: Record<DepreciationScheduleMethod, string> = {
  STRAIGHT_LINE: 'Straight Line',
  DECLINING: 'Declining',
  UNITS: 'Units',
};
export const DEPRECIATION_SCHEDULE_METHOD_OPTIONS = DEPRECIATION_SCHEDULE_METHOD_VALUES.map((value) => ({ value, label: DEPRECIATION_SCHEDULE_METHOD_LABELS[value] }));

export const DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type DepreciationScheduleLifecycleStage = (typeof DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_VALUES)[number];
export const DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS: Record<DepreciationScheduleLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_OPTIONS = DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_LABELS[value] }));

export interface DepreciationSchedule {
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
  method?: DepreciationScheduleMethod;
  usefulLifeMonths: number;
  salvageValue?: number;
  startDate?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: DepreciationScheduleLifecycleStage;
  sourceSystem?: string;
}

export interface DepreciationScheduleInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  assetId: string;
  method?: DepreciationScheduleMethod;
  usefulLifeMonths: number;
  salvageValue?: number;
  startDate?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: DepreciationScheduleLifecycleStage;
  sourceSystem?: string;
}

export type DepreciationScheduleFormValues = Partial<DepreciationScheduleInput>;

export interface DepreciationScheduleUpdateInput extends Partial<DepreciationScheduleInput> {
  expectedVersion?: number;
}

export interface DepreciationScheduleFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  assetId?: string;
  method?: DepreciationScheduleMethod[];
  usefulLifeMonthsMin?: number;
  usefulLifeMonthsMax?: number;
  salvageValueMin?: number;
  salvageValueMax?: number;
  startDateFrom?: string;
  startDateTo?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: DepreciationScheduleLifecycleStage[];
  sourceSystem?: string;
}

export type DepreciationScheduleSort = SortState<keyof DepreciationSchedule & string>;

export interface DepreciationScheduleSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const DEPRECIATIONSCHEDULE_PATH = '/api/v1/depreciation-schedules';
export const DEPRECIATIONSCHEDULE_TITLE = 'Depreciation Schedule';
export const DEPRECIATIONSCHEDULE_TITLE_PLURAL = 'Depreciation Schedulees';

export const DEPRECIATIONSCHEDULE_DEFAULTS: DepreciationScheduleFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
