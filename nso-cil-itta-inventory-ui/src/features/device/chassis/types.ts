import type { LifecycleStatus, SortState } from '@/shared/types';

export const CHASSIS_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ChassisLifecycleStage = (typeof CHASSIS_LIFECYCLE_STAGE_VALUES)[number];
export const CHASSIS_LIFECYCLE_STAGE_LABELS: Record<ChassisLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CHASSIS_LIFECYCLE_STAGE_OPTIONS = CHASSIS_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CHASSIS_LIFECYCLE_STAGE_LABELS[value] }));

export const CHASSIS_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ChassisCriticality = (typeof CHASSIS_CRITICALITY_VALUES)[number];
export const CHASSIS_CRITICALITY_LABELS: Record<ChassisCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const CHASSIS_CRITICALITY_OPTIONS = CHASSIS_CRITICALITY_VALUES.map((value) => ({ value, label: CHASSIS_CRITICALITY_LABELS[value] }));

export interface Chassis {
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
  deviceId: string;
  slotCount: number;
  serialNumber?: string;
  backplaneGbps?: number;
  redundancyMode?: string;
  lifecycleStage?: ChassisLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ChassisCriticality;
}

export interface ChassisInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  deviceId: string;
  slotCount: number;
  serialNumber?: string;
  backplaneGbps?: number;
  redundancyMode?: string;
  lifecycleStage?: ChassisLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ChassisCriticality;
}

export type ChassisFormValues = Partial<ChassisInput>;

export interface ChassisUpdateInput extends Partial<ChassisInput> {
  expectedVersion?: number;
}

export interface ChassisFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  deviceId?: string;
  slotCountMin?: number;
  slotCountMax?: number;
  serialNumber?: string;
  backplaneGbpsMin?: number;
  backplaneGbpsMax?: number;
  redundancyMode?: string;
  lifecycleStage?: ChassisLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ChassisCriticality[];
}

export type ChassisSort = SortState<keyof Chassis & string>;

export interface ChassisSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CHASSIS_PATH = '/api/v1/chassises';
export const CHASSIS_TITLE = 'Chassis';
export const CHASSIS_TITLE_PLURAL = 'Chassises';

export const CHASSIS_DEFAULTS: ChassisFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
