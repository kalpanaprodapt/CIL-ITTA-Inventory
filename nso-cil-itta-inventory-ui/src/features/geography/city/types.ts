import type { LifecycleStatus, SortState } from '@/shared/types';

export const CITY_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CityLifecycleStage = (typeof CITY_LIFECYCLE_STAGE_VALUES)[number];
export const CITY_LIFECYCLE_STAGE_LABELS: Record<CityLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CITY_LIFECYCLE_STAGE_OPTIONS = CITY_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CITY_LIFECYCLE_STAGE_LABELS[value] }));

export interface City {
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
  regionId: string;
  postalPrefix?: string;
  population?: number;
  latitude?: number;
  longitude?: number;
  metroArea?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CityLifecycleStage;
}

export interface CityInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  regionId: string;
  postalPrefix?: string;
  population?: number;
  latitude?: number;
  longitude?: number;
  metroArea?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CityLifecycleStage;
}

export type CityFormValues = Partial<CityInput>;

export interface CityUpdateInput extends Partial<CityInput> {
  expectedVersion?: number;
}

export interface CityFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  regionId?: string;
  postalPrefix?: string;
  populationMin?: number;
  populationMax?: number;
  latitudeMin?: number;
  latitudeMax?: number;
  longitudeMin?: number;
  longitudeMax?: number;
  metroArea?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: CityLifecycleStage[];
}

export type CitySort = SortState<keyof City & string>;

export interface CitySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CITY_PATH = '/api/v1/cities';
export const CITY_TITLE = 'City';
export const CITY_TITLE_PLURAL = 'Cityes';

export const CITY_DEFAULTS: CityFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
