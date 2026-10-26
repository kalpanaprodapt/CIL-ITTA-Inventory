import type { LifecycleStatus, SortState } from '@/shared/types';

export const ROADM_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type RoadmCriticality = (typeof ROADM_CRITICALITY_VALUES)[number];
export const ROADM_CRITICALITY_LABELS: Record<RoadmCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const ROADM_CRITICALITY_OPTIONS = ROADM_CRITICALITY_VALUES.map((value) => ({ value, label: ROADM_CRITICALITY_LABELS[value] }));

export interface Roadm {
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
  systemId: string;
  degrees: number;
  colorless?: boolean;
  directionless?: boolean;
  contentionless?: boolean;
  region?: string;
  owner?: string;
  criticality?: RoadmCriticality;
  costCenter?: string;
  notes?: string;
}

export interface RoadmInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  systemId: string;
  degrees: number;
  colorless?: boolean;
  directionless?: boolean;
  contentionless?: boolean;
  region?: string;
  owner?: string;
  criticality?: RoadmCriticality;
  costCenter?: string;
  notes?: string;
}

export type RoadmFormValues = Partial<RoadmInput>;

export interface RoadmUpdateInput extends Partial<RoadmInput> {
  expectedVersion?: number;
}

export interface RoadmFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  systemId?: string;
  degreesMin?: number;
  degreesMax?: number;
  colorless?: boolean;
  directionless?: boolean;
  contentionless?: boolean;
  region?: string;
  owner?: string;
  criticality?: RoadmCriticality[];
  costCenter?: string;
}

export type RoadmSort = SortState<keyof Roadm & string>;

export interface RoadmSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ROADM_PATH = '/api/v1/roadms';
export const ROADM_TITLE = 'Roadm';
export const ROADM_TITLE_PLURAL = 'Roadms';

export const ROADM_DEFAULTS: RoadmFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  colorless: false,
  directionless: false,
  contentionless: false,
};
