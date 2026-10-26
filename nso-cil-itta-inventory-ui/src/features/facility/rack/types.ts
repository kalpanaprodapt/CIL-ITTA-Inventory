import type { LifecycleStatus, SortState } from '@/shared/types';

export const RACK_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type RackLifecycleStage = (typeof RACK_LIFECYCLE_STAGE_VALUES)[number];
export const RACK_LIFECYCLE_STAGE_LABELS: Record<RackLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const RACK_LIFECYCLE_STAGE_OPTIONS = RACK_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: RACK_LIFECYCLE_STAGE_LABELS[value] }));

export interface Rack {
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
  roomId: string;
  rackUnits: number;
  widthMm?: number;
  depthMm?: number;
  maxWeightKg?: number;
  rowLabel?: string;
  position?: string;
  labels?: string;
  lifecycleStage?: RackLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface RackInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  roomId: string;
  rackUnits: number;
  widthMm?: number;
  depthMm?: number;
  maxWeightKg?: number;
  rowLabel?: string;
  position?: string;
  labels?: string;
  lifecycleStage?: RackLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type RackFormValues = Partial<RackInput>;

export interface RackUpdateInput extends Partial<RackInput> {
  expectedVersion?: number;
}

export interface RackFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  roomId?: string;
  rackUnitsMin?: number;
  rackUnitsMax?: number;
  widthMmMin?: number;
  widthMmMax?: number;
  depthMmMin?: number;
  depthMmMax?: number;
  maxWeightKgMin?: number;
  maxWeightKgMax?: number;
  rowLabel?: string;
  position?: string;
  labels?: string;
  lifecycleStage?: RackLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type RackSort = SortState<keyof Rack & string>;

export interface RackSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RACK_PATH = '/api/v1/racks';
export const RACK_TITLE = 'Rack';
export const RACK_TITLE_PLURAL = 'Racks';

export const RACK_DEFAULTS: RackFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
