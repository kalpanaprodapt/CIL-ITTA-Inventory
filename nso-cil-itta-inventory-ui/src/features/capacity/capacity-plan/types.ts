import type { LifecycleStatus, SortState } from '@/shared/types';

export const CAPACITY_PLAN_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CapacityPlanLifecycleStage = (typeof CAPACITY_PLAN_LIFECYCLE_STAGE_VALUES)[number];
export const CAPACITY_PLAN_LIFECYCLE_STAGE_LABELS: Record<CapacityPlanLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CAPACITY_PLAN_LIFECYCLE_STAGE_OPTIONS = CAPACITY_PLAN_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CAPACITY_PLAN_LIFECYCLE_STAGE_LABELS[value] }));

export interface CapacityPlan {
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
  planName: string;
  horizonMonths: number;
  targetUtilizationPct?: number;
  owner?: string;
  approvedOn?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CapacityPlanLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface CapacityPlanInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  planName: string;
  horizonMonths: number;
  targetUtilizationPct?: number;
  owner?: string;
  approvedOn?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CapacityPlanLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type CapacityPlanFormValues = Partial<CapacityPlanInput>;

export interface CapacityPlanUpdateInput extends Partial<CapacityPlanInput> {
  expectedVersion?: number;
}

export interface CapacityPlanFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  planName?: string;
  horizonMonthsMin?: number;
  horizonMonthsMax?: number;
  targetUtilizationPctMin?: number;
  targetUtilizationPctMax?: number;
  owner?: string;
  approvedOnFrom?: string;
  approvedOnTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: CapacityPlanLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type CapacityPlanSort = SortState<keyof CapacityPlan & string>;

export interface CapacityPlanSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CAPACITYPLAN_PATH = '/api/v1/capacity-plans';
export const CAPACITYPLAN_TITLE = 'Capacity Plan';
export const CAPACITYPLAN_TITLE_PLURAL = 'Capacity Plans';

export const CAPACITYPLAN_DEFAULTS: CapacityPlanFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  planName: '',
};
