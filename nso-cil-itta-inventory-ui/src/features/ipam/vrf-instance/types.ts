import type { LifecycleStatus, SortState } from '@/shared/types';

export const VRF_INSTANCE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type VrfInstanceLifecycleStage = (typeof VRF_INSTANCE_LIFECYCLE_STAGE_VALUES)[number];
export const VRF_INSTANCE_LIFECYCLE_STAGE_LABELS: Record<VrfInstanceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const VRF_INSTANCE_LIFECYCLE_STAGE_OPTIONS = VRF_INSTANCE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: VRF_INSTANCE_LIFECYCLE_STAGE_LABELS[value] }));

export interface VrfInstance {
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
  vrfName: string;
  routeDistinguisher: string;
  importTargets?: string;
  exportTargets?: string;
  deviceId?: string;
  labels?: string;
  lifecycleStage?: VrfInstanceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface VrfInstanceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vrfName: string;
  routeDistinguisher: string;
  importTargets?: string;
  exportTargets?: string;
  deviceId?: string;
  labels?: string;
  lifecycleStage?: VrfInstanceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type VrfInstanceFormValues = Partial<VrfInstanceInput>;

export interface VrfInstanceUpdateInput extends Partial<VrfInstanceInput> {
  expectedVersion?: number;
}

export interface VrfInstanceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vrfName?: string;
  routeDistinguisher?: string;
  importTargets?: string;
  exportTargets?: string;
  deviceId?: string;
  labels?: string;
  lifecycleStage?: VrfInstanceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type VrfInstanceSort = SortState<keyof VrfInstance & string>;

export interface VrfInstanceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VRFINSTANCE_PATH = '/api/v1/vrf-instances';
export const VRFINSTANCE_TITLE = 'Vrf Instance';
export const VRFINSTANCE_TITLE_PLURAL = 'Vrf Instancees';

export const VRFINSTANCE_DEFAULTS: VrfInstanceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  vrfName: '',
  routeDistinguisher: '',
};
