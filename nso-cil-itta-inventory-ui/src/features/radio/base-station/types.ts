import type { LifecycleStatus, SortState } from '@/shared/types';

export const BASE_STATION_TECHNOLOGY_VALUES = ['LTE', 'NR', 'GSM', 'UMTS'] as const;
export type BaseStationTechnology = (typeof BASE_STATION_TECHNOLOGY_VALUES)[number];
export const BASE_STATION_TECHNOLOGY_LABELS: Record<BaseStationTechnology, string> = {
  LTE: 'Lte',
  NR: 'Nr',
  GSM: 'Gsm',
  UMTS: 'Umts',
};
export const BASE_STATION_TECHNOLOGY_OPTIONS = BASE_STATION_TECHNOLOGY_VALUES.map((value) => ({ value, label: BASE_STATION_TECHNOLOGY_LABELS[value] }));

export const BASE_STATION_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type BaseStationLifecycleStage = (typeof BASE_STATION_LIFECYCLE_STAGE_VALUES)[number];
export const BASE_STATION_LIFECYCLE_STAGE_LABELS: Record<BaseStationLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const BASE_STATION_LIFECYCLE_STAGE_OPTIONS = BASE_STATION_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: BASE_STATION_LIFECYCLE_STAGE_LABELS[value] }));

export interface BaseStation {
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
  enbId: number;
  technology?: BaseStationTechnology;
  sectorCount?: number;
  cellCount?: number;
  labels?: string;
  lifecycleStage?: BaseStationLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface BaseStationInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  siteId: string;
  enbId: number;
  technology?: BaseStationTechnology;
  sectorCount?: number;
  cellCount?: number;
  labels?: string;
  lifecycleStage?: BaseStationLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type BaseStationFormValues = Partial<BaseStationInput>;

export interface BaseStationUpdateInput extends Partial<BaseStationInput> {
  expectedVersion?: number;
}

export interface BaseStationFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  siteId?: string;
  enbIdMin?: number;
  enbIdMax?: number;
  technology?: BaseStationTechnology[];
  sectorCountMin?: number;
  sectorCountMax?: number;
  cellCountMin?: number;
  cellCountMax?: number;
  labels?: string;
  lifecycleStage?: BaseStationLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type BaseStationSort = SortState<keyof BaseStation & string>;

export interface BaseStationSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const BASESTATION_PATH = '/api/v1/base-stations';
export const BASESTATION_TITLE = 'Base Station';
export const BASESTATION_TITLE_PLURAL = 'Base Stations';

export const BASESTATION_DEFAULTS: BaseStationFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
