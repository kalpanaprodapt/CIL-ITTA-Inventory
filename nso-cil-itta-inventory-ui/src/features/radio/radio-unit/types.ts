import type { LifecycleStatus, SortState } from '@/shared/types';

export const RADIO_UNIT_UNIT_TYPE_VALUES = ['RRU', 'AAU', 'MICROWAVE', 'SMALL_CELL'] as const;
export type RadioUnitUnitType = (typeof RADIO_UNIT_UNIT_TYPE_VALUES)[number];
export const RADIO_UNIT_UNIT_TYPE_LABELS: Record<RadioUnitUnitType, string> = {
  RRU: 'Rru',
  AAU: 'Aau',
  MICROWAVE: 'Microwave',
  SMALL_CELL: 'Small Cell',
};
export const RADIO_UNIT_UNIT_TYPE_OPTIONS = RADIO_UNIT_UNIT_TYPE_VALUES.map((value) => ({ value, label: RADIO_UNIT_UNIT_TYPE_LABELS[value] }));

export const RADIO_UNIT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type RadioUnitLifecycleStage = (typeof RADIO_UNIT_LIFECYCLE_STAGE_VALUES)[number];
export const RADIO_UNIT_LIFECYCLE_STAGE_LABELS: Record<RadioUnitLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const RADIO_UNIT_LIFECYCLE_STAGE_OPTIONS = RADIO_UNIT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: RADIO_UNIT_LIFECYCLE_STAGE_LABELS[value] }));

export interface RadioUnit {
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
  unitType?: RadioUnitUnitType;
  bandMhz?: number;
  txPowerDbm?: number;
  serialNumber?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: RadioUnitLifecycleStage;
  sourceSystem?: string;
}

export interface RadioUnitInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  siteId: string;
  unitType?: RadioUnitUnitType;
  bandMhz?: number;
  txPowerDbm?: number;
  serialNumber?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: RadioUnitLifecycleStage;
  sourceSystem?: string;
}

export type RadioUnitFormValues = Partial<RadioUnitInput>;

export interface RadioUnitUpdateInput extends Partial<RadioUnitInput> {
  expectedVersion?: number;
}

export interface RadioUnitFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  siteId?: string;
  unitType?: RadioUnitUnitType[];
  bandMhzMin?: number;
  bandMhzMax?: number;
  txPowerDbmMin?: number;
  txPowerDbmMax?: number;
  serialNumber?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: RadioUnitLifecycleStage[];
  sourceSystem?: string;
}

export type RadioUnitSort = SortState<keyof RadioUnit & string>;

export interface RadioUnitSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RADIOUNIT_PATH = '/api/v1/radio-units';
export const RADIOUNIT_TITLE = 'Radio Unit';
export const RADIOUNIT_TITLE_PLURAL = 'Radio Units';

export const RADIOUNIT_DEFAULTS: RadioUnitFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
