import type { LifecycleStatus, SortState } from '@/shared/types';

export const REGION_REGION_TYPE_VALUES = ['STATE', 'PROVINCE', 'TERRITORY', 'ZONE'] as const;
export type RegionRegionType = (typeof REGION_REGION_TYPE_VALUES)[number];
export const REGION_REGION_TYPE_LABELS: Record<RegionRegionType, string> = {
  STATE: 'State',
  PROVINCE: 'Province',
  TERRITORY: 'Territory',
  ZONE: 'Zone',
};
export const REGION_REGION_TYPE_OPTIONS = REGION_REGION_TYPE_VALUES.map((value) => ({ value, label: REGION_REGION_TYPE_LABELS[value] }));

export const REGION_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type RegionCriticality = (typeof REGION_CRITICALITY_VALUES)[number];
export const REGION_CRITICALITY_LABELS: Record<RegionCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const REGION_CRITICALITY_OPTIONS = REGION_CRITICALITY_VALUES.map((value) => ({ value, label: REGION_CRITICALITY_LABELS[value] }));

export interface Region {
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
  countryId: string;
  regionType?: RegionRegionType;
  population?: number;
  latitude?: number;
  longitude?: number;
  criticality?: RegionCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface RegionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  countryId: string;
  regionType?: RegionRegionType;
  population?: number;
  latitude?: number;
  longitude?: number;
  criticality?: RegionCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type RegionFormValues = Partial<RegionInput>;

export interface RegionUpdateInput extends Partial<RegionInput> {
  expectedVersion?: number;
}

export interface RegionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  countryId?: string;
  regionType?: RegionRegionType[];
  populationMin?: number;
  populationMax?: number;
  latitudeMin?: number;
  latitudeMax?: number;
  longitudeMin?: number;
  longitudeMax?: number;
  criticality?: RegionCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type RegionSort = SortState<keyof Region & string>;

export interface RegionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const REGION_PATH = '/api/v1/regions';
export const REGION_TITLE = 'Region';
export const REGION_TITLE_PLURAL = 'Regions';

export const REGION_DEFAULTS: RegionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
