import type { LifecycleStatus, SortState } from '@/shared/types';

export const CIRCUIT_TERMINATION_SIDE_VALUES = ['A', 'Z'] as const;
export type CircuitTerminationSide = (typeof CIRCUIT_TERMINATION_SIDE_VALUES)[number];
export const CIRCUIT_TERMINATION_SIDE_LABELS: Record<CircuitTerminationSide, string> = {
  A: 'A',
  Z: 'Z',
};
export const CIRCUIT_TERMINATION_SIDE_OPTIONS = CIRCUIT_TERMINATION_SIDE_VALUES.map((value) => ({ value, label: CIRCUIT_TERMINATION_SIDE_LABELS[value] }));

export interface CircuitTermination {
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
  side?: CircuitTerminationSide;
  portId: string;
  xconnectId?: string;
  patchInfo?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface CircuitTerminationInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  circuitId: string;
  side?: CircuitTerminationSide;
  portId: string;
  xconnectId?: string;
  patchInfo?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type CircuitTerminationFormValues = Partial<CircuitTerminationInput>;

export interface CircuitTerminationUpdateInput extends Partial<CircuitTerminationInput> {
  expectedVersion?: number;
}

export interface CircuitTerminationFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  circuitId?: string;
  side?: CircuitTerminationSide[];
  portId?: string;
  xconnectId?: string;
  patchInfo?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type CircuitTerminationSort = SortState<keyof CircuitTermination & string>;

export interface CircuitTerminationSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CIRCUITTERMINATION_PATH = '/api/v1/circuit-terminations';
export const CIRCUITTERMINATION_TITLE = 'Circuit Termination';
export const CIRCUITTERMINATION_TITLE_PLURAL = 'Circuit Terminations';

export const CIRCUITTERMINATION_DEFAULTS: CircuitTerminationFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
