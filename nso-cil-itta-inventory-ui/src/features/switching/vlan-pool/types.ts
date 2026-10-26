import type { LifecycleStatus, SortState } from '@/shared/types';

export const VLAN_POOL_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type VlanPoolLifecycleStage = (typeof VLAN_POOL_LIFECYCLE_STAGE_VALUES)[number];
export const VLAN_POOL_LIFECYCLE_STAGE_LABELS: Record<VlanPoolLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const VLAN_POOL_LIFECYCLE_STAGE_OPTIONS = VLAN_POOL_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: VLAN_POOL_LIFECYCLE_STAGE_LABELS[value] }));

export interface VlanPool {
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
  poolName: string;
  rangeStart: number;
  rangeEnd: number;
  allocated?: number;
  scope?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VlanPoolLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface VlanPoolInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  poolName: string;
  rangeStart: number;
  rangeEnd: number;
  allocated?: number;
  scope?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: VlanPoolLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type VlanPoolFormValues = Partial<VlanPoolInput>;

export interface VlanPoolUpdateInput extends Partial<VlanPoolInput> {
  expectedVersion?: number;
}

export interface VlanPoolFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  poolName?: string;
  rangeStartMin?: number;
  rangeStartMax?: number;
  rangeEndMin?: number;
  rangeEndMax?: number;
  allocatedMin?: number;
  allocatedMax?: number;
  scope?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: VlanPoolLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type VlanPoolSort = SortState<keyof VlanPool & string>;

export interface VlanPoolSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VLANPOOL_PATH = '/api/v1/vlan-pools';
export const VLANPOOL_TITLE = 'Vlan Pool';
export const VLANPOOL_TITLE_PLURAL = 'Vlan Pools';

export const VLANPOOL_DEFAULTS: VlanPoolFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  poolName: '',
};
