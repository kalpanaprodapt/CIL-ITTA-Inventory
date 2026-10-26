import type { LifecycleStatus, SortState } from '@/shared/types';

export const TRANSCEIVER_FORM_FACTOR_VALUES = ['SFP', 'SFPP', 'SFP28', 'QSFP', 'QSFP28', 'CFP'] as const;
export type TransceiverFormFactor = (typeof TRANSCEIVER_FORM_FACTOR_VALUES)[number];
export const TRANSCEIVER_FORM_FACTOR_LABELS: Record<TransceiverFormFactor, string> = {
  SFP: 'Sfp',
  SFPP: 'Sfpp',
  SFP28: 'Sfp28',
  QSFP: 'Qsfp',
  QSFP28: 'Qsfp28',
  CFP: 'Cfp',
};
export const TRANSCEIVER_FORM_FACTOR_OPTIONS = TRANSCEIVER_FORM_FACTOR_VALUES.map((value) => ({ value, label: TRANSCEIVER_FORM_FACTOR_LABELS[value] }));

export const TRANSCEIVER_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type TransceiverLifecycleStage = (typeof TRANSCEIVER_LIFECYCLE_STAGE_VALUES)[number];
export const TRANSCEIVER_LIFECYCLE_STAGE_LABELS: Record<TransceiverLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const TRANSCEIVER_LIFECYCLE_STAGE_OPTIONS = TRANSCEIVER_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: TRANSCEIVER_LIFECYCLE_STAGE_LABELS[value] }));

export interface Transceiver {
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
  portId?: string;
  formFactor?: TransceiverFormFactor;
  wavelengthNm?: number;
  reachKm?: number;
  serialNumber?: string;
  vendorPart?: string;
  labels?: string;
  lifecycleStage?: TransceiverLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface TransceiverInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  portId?: string;
  formFactor?: TransceiverFormFactor;
  wavelengthNm?: number;
  reachKm?: number;
  serialNumber?: string;
  vendorPart?: string;
  labels?: string;
  lifecycleStage?: TransceiverLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type TransceiverFormValues = Partial<TransceiverInput>;

export interface TransceiverUpdateInput extends Partial<TransceiverInput> {
  expectedVersion?: number;
}

export interface TransceiverFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  portId?: string;
  formFactor?: TransceiverFormFactor[];
  wavelengthNmMin?: number;
  wavelengthNmMax?: number;
  reachKmMin?: number;
  reachKmMax?: number;
  serialNumber?: string;
  vendorPart?: string;
  labels?: string;
  lifecycleStage?: TransceiverLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type TransceiverSort = SortState<keyof Transceiver & string>;

export interface TransceiverSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TRANSCEIVER_PATH = '/api/v1/transceivers';
export const TRANSCEIVER_TITLE = 'Transceiver';
export const TRANSCEIVER_TITLE_PLURAL = 'Transceivers';

export const TRANSCEIVER_DEFAULTS: TransceiverFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
