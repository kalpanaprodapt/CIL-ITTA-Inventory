import type { LifecycleStatus, SortState } from '@/shared/types';

export const VIRTUAL_MACHINE_POWER_STATE_VALUES = ['ON', 'OFF', 'SUSPENDED'] as const;
export type VirtualMachinePowerState = (typeof VIRTUAL_MACHINE_POWER_STATE_VALUES)[number];
export const VIRTUAL_MACHINE_POWER_STATE_LABELS: Record<VirtualMachinePowerState, string> = {
  ON: 'On',
  OFF: 'Off',
  SUSPENDED: 'Suspended',
};
export const VIRTUAL_MACHINE_POWER_STATE_OPTIONS = VIRTUAL_MACHINE_POWER_STATE_VALUES.map((value) => ({ value, label: VIRTUAL_MACHINE_POWER_STATE_LABELS[value] }));

export const VIRTUAL_MACHINE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type VirtualMachineCriticality = (typeof VIRTUAL_MACHINE_CRITICALITY_VALUES)[number];
export const VIRTUAL_MACHINE_CRITICALITY_LABELS: Record<VirtualMachineCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const VIRTUAL_MACHINE_CRITICALITY_OPTIONS = VIRTUAL_MACHINE_CRITICALITY_VALUES.map((value) => ({ value, label: VIRTUAL_MACHINE_CRITICALITY_LABELS[value] }));

export interface VirtualMachine {
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
  hypervisorId: string;
  vmName: string;
  vcpu: number;
  memoryGb: number;
  diskGb?: number;
  powerState?: VirtualMachinePowerState;
  criticality?: VirtualMachineCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface VirtualMachineInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  hypervisorId: string;
  vmName: string;
  vcpu: number;
  memoryGb: number;
  diskGb?: number;
  powerState?: VirtualMachinePowerState;
  criticality?: VirtualMachineCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type VirtualMachineFormValues = Partial<VirtualMachineInput>;

export interface VirtualMachineUpdateInput extends Partial<VirtualMachineInput> {
  expectedVersion?: number;
}

export interface VirtualMachineFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  hypervisorId?: string;
  vmName?: string;
  vcpuMin?: number;
  vcpuMax?: number;
  memoryGbMin?: number;
  memoryGbMax?: number;
  diskGbMin?: number;
  diskGbMax?: number;
  powerState?: VirtualMachinePowerState[];
  criticality?: VirtualMachineCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type VirtualMachineSort = SortState<keyof VirtualMachine & string>;

export interface VirtualMachineSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VIRTUALMACHINE_PATH = '/api/v1/virtual-machines';
export const VIRTUALMACHINE_TITLE = 'Virtual Machine';
export const VIRTUALMACHINE_TITLE_PLURAL = 'Virtual Machinees';

export const VIRTUALMACHINE_DEFAULTS: VirtualMachineFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  vmName: '',
};
