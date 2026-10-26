import type { LifecycleStatus, SortState } from '@/shared/types';

export const VNF_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type VnfLifecycleStage = (typeof VNF_LIFECYCLE_STAGE_VALUES)[number];
export const VNF_LIFECYCLE_STAGE_LABELS: Record<VnfLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const VNF_LIFECYCLE_STAGE_OPTIONS = VNF_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: VNF_LIFECYCLE_STAGE_LABELS[value] }));

export const VNF_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type VnfCriticality = (typeof VNF_CRITICALITY_VALUES)[number];
export const VNF_CRITICALITY_LABELS: Record<VnfCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const VNF_CRITICALITY_OPTIONS = VNF_CRITICALITY_VALUES.map((value) => ({ value, label: VNF_CRITICALITY_LABELS[value] }));

export interface Vnf {
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
  vnfName: string;
  vnfType: string;
  flavor?: string;
  vcpu?: number;
  memoryGb?: number;
  instanceCount?: number;
  lifecycleStage?: VnfLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: VnfCriticality;
}

export interface VnfInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vnfName: string;
  vnfType: string;
  flavor?: string;
  vcpu?: number;
  memoryGb?: number;
  instanceCount?: number;
  lifecycleStage?: VnfLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: VnfCriticality;
}

export type VnfFormValues = Partial<VnfInput>;

export interface VnfUpdateInput extends Partial<VnfInput> {
  expectedVersion?: number;
}

export interface VnfFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vnfName?: string;
  vnfType?: string;
  flavor?: string;
  vcpuMin?: number;
  vcpuMax?: number;
  memoryGbMin?: number;
  memoryGbMax?: number;
  instanceCountMin?: number;
  instanceCountMax?: number;
  lifecycleStage?: VnfLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: VnfCriticality[];
}

export type VnfSort = SortState<keyof Vnf & string>;

export interface VnfSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VNF_PATH = '/api/v1/vnfs';
export const VNF_TITLE = 'Vnf';
export const VNF_TITLE_PLURAL = 'Vnfs';

export const VNF_DEFAULTS: VnfFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  vnfName: '',
  vnfType: '',
};
