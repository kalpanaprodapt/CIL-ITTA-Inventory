import type { LifecycleStatus, SortState } from '@/shared/types';

export const CABLE_CABLE_TYPE_VALUES = ['CAT6', 'SM_FIBER', 'MM_FIBER', 'COAX', 'DAC'] as const;
export type CableCableType = (typeof CABLE_CABLE_TYPE_VALUES)[number];
export const CABLE_CABLE_TYPE_LABELS: Record<CableCableType, string> = {
  CAT6: 'Cat6',
  SM_FIBER: 'Sm Fiber',
  MM_FIBER: 'Mm Fiber',
  COAX: 'Coax',
  DAC: 'Dac',
};
export const CABLE_CABLE_TYPE_OPTIONS = CABLE_CABLE_TYPE_VALUES.map((value) => ({ value, label: CABLE_CABLE_TYPE_LABELS[value] }));

export const CABLE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type CableCriticality = (typeof CABLE_CRITICALITY_VALUES)[number];
export const CABLE_CRITICALITY_LABELS: Record<CableCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const CABLE_CRITICALITY_OPTIONS = CABLE_CRITICALITY_VALUES.map((value) => ({ value, label: CABLE_CRITICALITY_LABELS[value] }));

export interface Cable {
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
  cableType?: CableCableType;
  lengthM?: number;
  aEndPortId?: string;
  zEndPortId?: string;
  color?: string;
  label?: string;
  criticality?: CableCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface CableInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  cableType?: CableCableType;
  lengthM?: number;
  aEndPortId?: string;
  zEndPortId?: string;
  color?: string;
  label?: string;
  criticality?: CableCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type CableFormValues = Partial<CableInput>;

export interface CableUpdateInput extends Partial<CableInput> {
  expectedVersion?: number;
}

export interface CableFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  cableType?: CableCableType[];
  lengthMMin?: number;
  lengthMMax?: number;
  aEndPortId?: string;
  zEndPortId?: string;
  color?: string;
  label?: string;
  criticality?: CableCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type CableSort = SortState<keyof Cable & string>;

export interface CableSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CABLE_PATH = '/api/v1/cables';
export const CABLE_TITLE = 'Cable';
export const CABLE_TITLE_PLURAL = 'Cablees';

export const CABLE_DEFAULTS: CableFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
