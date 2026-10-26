import type { LifecycleStatus, SortState } from '@/shared/types';

export const VLAN_VLAN_TYPE_VALUES = ['DATA', 'VOICE', 'MGMT', 'NATIVE'] as const;
export type VlanVlanType = (typeof VLAN_VLAN_TYPE_VALUES)[number];
export const VLAN_VLAN_TYPE_LABELS: Record<VlanVlanType, string> = {
  DATA: 'Data',
  VOICE: 'Voice',
  MGMT: 'Mgmt',
  NATIVE: 'Native',
};
export const VLAN_VLAN_TYPE_OPTIONS = VLAN_VLAN_TYPE_VALUES.map((value) => ({ value, label: VLAN_VLAN_TYPE_LABELS[value] }));

export const VLAN_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type VlanLifecycleStage = (typeof VLAN_LIFECYCLE_STAGE_VALUES)[number];
export const VLAN_LIFECYCLE_STAGE_LABELS: Record<VlanLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const VLAN_LIFECYCLE_STAGE_OPTIONS = VLAN_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: VLAN_LIFECYCLE_STAGE_LABELS[value] }));

export interface Vlan {
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
  vlanId: number;
  vlanName: string;
  vlanType?: VlanVlanType;
  poolId?: string;
  stpMode?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VlanLifecycleStage;
}

export interface VlanInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vlanId: number;
  vlanName: string;
  vlanType?: VlanVlanType;
  poolId?: string;
  stpMode?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VlanLifecycleStage;
}

export type VlanFormValues = Partial<VlanInput>;

export interface VlanUpdateInput extends Partial<VlanInput> {
  expectedVersion?: number;
}

export interface VlanFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vlanIdMin?: number;
  vlanIdMax?: number;
  vlanName?: string;
  vlanType?: VlanVlanType[];
  poolId?: string;
  stpMode?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: VlanLifecycleStage[];
}

export type VlanSort = SortState<keyof Vlan & string>;

export interface VlanSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VLAN_PATH = '/api/v1/vlans';
export const VLAN_TITLE = 'Vlan';
export const VLAN_TITLE_PLURAL = 'Vlans';

export const VLAN_DEFAULTS: VlanFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  vlanName: '',
};
