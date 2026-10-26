import type { LifecycleStatus, SortState } from '@/shared/types';

export const UTILIZATION_SAMPLE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type UtilizationSampleLifecycleStage = (typeof UTILIZATION_SAMPLE_LIFECYCLE_STAGE_VALUES)[number];
export const UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS: Record<UtilizationSampleLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const UTILIZATION_SAMPLE_LIFECYCLE_STAGE_OPTIONS = UTILIZATION_SAMPLE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: UTILIZATION_SAMPLE_LIFECYCLE_STAGE_LABELS[value] }));

export const UTILIZATION_SAMPLE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type UtilizationSampleCriticality = (typeof UTILIZATION_SAMPLE_CRITICALITY_VALUES)[number];
export const UTILIZATION_SAMPLE_CRITICALITY_LABELS: Record<UtilizationSampleCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const UTILIZATION_SAMPLE_CRITICALITY_OPTIONS = UTILIZATION_SAMPLE_CRITICALITY_VALUES.map((value) => ({ value, label: UTILIZATION_SAMPLE_CRITICALITY_LABELS[value] }));

export interface UtilizationSample {
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
  targetRef: string;
  metric: string;
  sampledAt: string;
  valuePct: number;
  peak?: boolean;
  lifecycleStage?: UtilizationSampleLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: UtilizationSampleCriticality;
}

export interface UtilizationSampleInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  targetRef: string;
  metric: string;
  sampledAt: string;
  valuePct: number;
  peak?: boolean;
  lifecycleStage?: UtilizationSampleLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: UtilizationSampleCriticality;
}

export type UtilizationSampleFormValues = Partial<UtilizationSampleInput>;

export interface UtilizationSampleUpdateInput extends Partial<UtilizationSampleInput> {
  expectedVersion?: number;
}

export interface UtilizationSampleFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  targetRef?: string;
  metric?: string;
  sampledAtFrom?: string;
  sampledAtTo?: string;
  valuePctMin?: number;
  valuePctMax?: number;
  peak?: boolean;
  lifecycleStage?: UtilizationSampleLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: UtilizationSampleCriticality[];
}

export type UtilizationSampleSort = SortState<keyof UtilizationSample & string>;

export interface UtilizationSampleSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const UTILIZATIONSAMPLE_PATH = '/api/v1/utilization-samples';
export const UTILIZATIONSAMPLE_TITLE = 'Utilization Sample';
export const UTILIZATIONSAMPLE_TITLE_PLURAL = 'Utilization Samplees';

export const UTILIZATIONSAMPLE_DEFAULTS: UtilizationSampleFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  targetRef: '',
  metric: '',
  peak: false,
};
