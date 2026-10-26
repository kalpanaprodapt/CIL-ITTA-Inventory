import type { LifecycleStatus, SortState } from '@/shared/types';

export const INTERFACE_OPER_STATE_VALUES = ['UP', 'DOWN', 'TESTING', 'UNKNOWN'] as const;
export type InterfaceOperState = (typeof INTERFACE_OPER_STATE_VALUES)[number];
export const INTERFACE_OPER_STATE_LABELS: Record<InterfaceOperState, string> = {
  UP: 'Up',
  DOWN: 'Down',
  TESTING: 'Testing',
  UNKNOWN: 'Unknown',
};
export const INTERFACE_OPER_STATE_OPTIONS = INTERFACE_OPER_STATE_VALUES.map((value) => ({ value, label: INTERFACE_OPER_STATE_LABELS[value] }));

export const INTERFACE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type InterfaceCriticality = (typeof INTERFACE_CRITICALITY_VALUES)[number];
export const INTERFACE_CRITICALITY_LABELS: Record<InterfaceCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const INTERFACE_CRITICALITY_OPTIONS = INTERFACE_CRITICALITY_VALUES.map((value) => ({ value, label: INTERFACE_CRITICALITY_LABELS[value] }));

export interface Interface {
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
  portId: string;
  ifName: string;
  ifIndex?: number;
  macAddress?: string;
  mtu?: number;
  operState?: InterfaceOperState;
  owner?: string;
  criticality?: InterfaceCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface InterfaceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  portId: string;
  ifName: string;
  ifIndex?: number;
  macAddress?: string;
  mtu?: number;
  operState?: InterfaceOperState;
  owner?: string;
  criticality?: InterfaceCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type InterfaceFormValues = Partial<InterfaceInput>;

export interface InterfaceUpdateInput extends Partial<InterfaceInput> {
  expectedVersion?: number;
}

export interface InterfaceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  portId?: string;
  ifName?: string;
  ifIndexMin?: number;
  ifIndexMax?: number;
  macAddress?: string;
  mtuMin?: number;
  mtuMax?: number;
  operState?: InterfaceOperState[];
  owner?: string;
  criticality?: InterfaceCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type InterfaceSort = SortState<keyof Interface & string>;

export interface InterfaceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const INTERFACE_PATH = '/api/v1/interfaces';
export const INTERFACE_TITLE = 'Interface';
export const INTERFACE_TITLE_PLURAL = 'Interfacees';

export const INTERFACE_DEFAULTS: InterfaceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  ifName: '',
};
