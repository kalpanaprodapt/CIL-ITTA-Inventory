import type { LifecycleStatus, SortState } from '@/shared/types';

export const CIRCUIT_CIRCUIT_TYPE_VALUES = ['ETHERNET', 'WAVELENGTH', 'MPLS', 'DIA', 'TDM'] as const;
export type CircuitCircuitType = (typeof CIRCUIT_CIRCUIT_TYPE_VALUES)[number];
export const CIRCUIT_CIRCUIT_TYPE_LABELS: Record<CircuitCircuitType, string> = {
  ETHERNET: 'Ethernet',
  WAVELENGTH: 'Wavelength',
  MPLS: 'Mpls',
  DIA: 'Dia',
  TDM: 'Tdm',
};
export const CIRCUIT_CIRCUIT_TYPE_OPTIONS = CIRCUIT_CIRCUIT_TYPE_VALUES.map((value) => ({ value, label: CIRCUIT_CIRCUIT_TYPE_LABELS[value] }));

export const CIRCUIT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CircuitLifecycleStage = (typeof CIRCUIT_LIFECYCLE_STAGE_VALUES)[number];
export const CIRCUIT_LIFECYCLE_STAGE_LABELS: Record<CircuitLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CIRCUIT_LIFECYCLE_STAGE_OPTIONS = CIRCUIT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CIRCUIT_LIFECYCLE_STAGE_LABELS[value] }));

export interface Circuit {
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
  circuitId: string;
  circuitType?: CircuitCircuitType;
  bandwidthMbps: number;
  providerId?: string;
  aSiteId?: string;
  zSiteId?: string;
  labels?: string;
  lifecycleStage?: CircuitLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface CircuitInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  circuitId: string;
  circuitType?: CircuitCircuitType;
  bandwidthMbps: number;
  providerId?: string;
  aSiteId?: string;
  zSiteId?: string;
  labels?: string;
  lifecycleStage?: CircuitLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type CircuitFormValues = Partial<CircuitInput>;

export interface CircuitUpdateInput extends Partial<CircuitInput> {
  expectedVersion?: number;
}

export interface CircuitFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  circuitId?: string;
  circuitType?: CircuitCircuitType[];
  bandwidthMbpsMin?: number;
  bandwidthMbpsMax?: number;
  providerId?: string;
  aSiteId?: string;
  zSiteId?: string;
  labels?: string;
  lifecycleStage?: CircuitLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type CircuitSort = SortState<keyof Circuit & string>;

export interface CircuitSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CIRCUIT_PATH = '/api/v1/circuits';
export const CIRCUIT_TITLE = 'Circuit';
export const CIRCUIT_TITLE_PLURAL = 'Circuits';

export const CIRCUIT_DEFAULTS: CircuitFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  circuitId: '',
};
