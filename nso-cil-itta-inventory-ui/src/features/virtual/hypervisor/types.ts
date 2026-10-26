import type { LifecycleStatus, SortState } from '@/shared/types';

export const HYPERVISOR_HYPERVISOR_TYPE_VALUES = ['KVM', 'VMWARE', 'HYPERV', 'XEN'] as const;
export type HypervisorHypervisorType = (typeof HYPERVISOR_HYPERVISOR_TYPE_VALUES)[number];
export const HYPERVISOR_HYPERVISOR_TYPE_LABELS: Record<HypervisorHypervisorType, string> = {
  KVM: 'Kvm',
  VMWARE: 'Vmware',
  HYPERV: 'Hyperv',
  XEN: 'Xen',
};
export const HYPERVISOR_HYPERVISOR_TYPE_OPTIONS = HYPERVISOR_HYPERVISOR_TYPE_VALUES.map((value) => ({ value, label: HYPERVISOR_HYPERVISOR_TYPE_LABELS[value] }));

export const HYPERVISOR_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type HypervisorCriticality = (typeof HYPERVISOR_CRITICALITY_VALUES)[number];
export const HYPERVISOR_CRITICALITY_LABELS: Record<HypervisorCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const HYPERVISOR_CRITICALITY_OPTIONS = HYPERVISOR_CRITICALITY_VALUES.map((value) => ({ value, label: HYPERVISOR_CRITICALITY_LABELS[value] }));

export interface Hypervisor {
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
  hostDeviceId: string;
  hypervisorType?: HypervisorHypervisorType;
  hvVersion?: string;
  vcpuCapacity?: number;
  memoryGbCapacity?: number;
  region?: string;
  owner?: string;
  criticality?: HypervisorCriticality;
  costCenter?: string;
  notes?: string;
}

export interface HypervisorInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  hostDeviceId: string;
  hypervisorType?: HypervisorHypervisorType;
  hvVersion?: string;
  vcpuCapacity?: number;
  memoryGbCapacity?: number;
  region?: string;
  owner?: string;
  criticality?: HypervisorCriticality;
  costCenter?: string;
  notes?: string;
}

export type HypervisorFormValues = Partial<HypervisorInput>;

export interface HypervisorUpdateInput extends Partial<HypervisorInput> {
  expectedVersion?: number;
}

export interface HypervisorFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  hostDeviceId?: string;
  hypervisorType?: HypervisorHypervisorType[];
  hvVersion?: string;
  vcpuCapacityMin?: number;
  vcpuCapacityMax?: number;
  memoryGbCapacityMin?: number;
  memoryGbCapacityMax?: number;
  region?: string;
  owner?: string;
  criticality?: HypervisorCriticality[];
  costCenter?: string;
}

export type HypervisorSort = SortState<keyof Hypervisor & string>;

export interface HypervisorSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const HYPERVISOR_PATH = '/api/v1/hypervisors';
export const HYPERVISOR_TITLE = 'Hypervisor';
export const HYPERVISOR_TITLE_PLURAL = 'Hypervisors';

export const HYPERVISOR_DEFAULTS: HypervisorFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
