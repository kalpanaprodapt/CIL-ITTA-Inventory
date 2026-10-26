import type { LifecycleStatus, SortState } from '@/shared/types';

export const BUILDING_OWNERSHIP_VALUES = ['OWNED', 'LEASED', 'COLO'] as const;
export type BuildingOwnership = (typeof BUILDING_OWNERSHIP_VALUES)[number];
export const BUILDING_OWNERSHIP_LABELS: Record<BuildingOwnership, string> = {
  OWNED: 'Owned',
  LEASED: 'Leased',
  COLO: 'Colo',
};
export const BUILDING_OWNERSHIP_OPTIONS = BUILDING_OWNERSHIP_VALUES.map((value) => ({ value, label: BUILDING_OWNERSHIP_LABELS[value] }));

export const BUILDING_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type BuildingLifecycleStage = (typeof BUILDING_LIFECYCLE_STAGE_VALUES)[number];
export const BUILDING_LIFECYCLE_STAGE_LABELS: Record<BuildingLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const BUILDING_LIFECYCLE_STAGE_OPTIONS = BUILDING_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: BUILDING_LIFECYCLE_STAGE_LABELS[value] }));

export const BUILDING_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type BuildingCriticality = (typeof BUILDING_CRITICALITY_VALUES)[number];
export const BUILDING_CRITICALITY_LABELS: Record<BuildingCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const BUILDING_CRITICALITY_OPTIONS = BUILDING_CRITICALITY_VALUES.map((value) => ({ value, label: BUILDING_CRITICALITY_LABELS[value] }));

export interface Building {
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
  siteId: string;
  floors?: number;
  yearBuilt?: number;
  ownership?: BuildingOwnership;
  leaseExpiry?: string;
  securityLevel?: number;
  lifecycleStage?: BuildingLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: BuildingCriticality;
}

export interface BuildingInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  siteId: string;
  floors?: number;
  yearBuilt?: number;
  ownership?: BuildingOwnership;
  leaseExpiry?: string;
  securityLevel?: number;
  lifecycleStage?: BuildingLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: BuildingCriticality;
}

export type BuildingFormValues = Partial<BuildingInput>;

export interface BuildingUpdateInput extends Partial<BuildingInput> {
  expectedVersion?: number;
}

export interface BuildingFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  siteId?: string;
  floorsMin?: number;
  floorsMax?: number;
  yearBuiltMin?: number;
  yearBuiltMax?: number;
  ownership?: BuildingOwnership[];
  leaseExpiryFrom?: string;
  leaseExpiryTo?: string;
  securityLevelMin?: number;
  securityLevelMax?: number;
  lifecycleStage?: BuildingLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: BuildingCriticality[];
}

export type BuildingSort = SortState<keyof Building & string>;

export interface BuildingSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const BUILDING_PATH = '/api/v1/buildings';
export const BUILDING_TITLE = 'Building';
export const BUILDING_TITLE_PLURAL = 'Buildings';

export const BUILDING_DEFAULTS: BuildingFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
