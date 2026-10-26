import type { LifecycleStatus, SortState } from '@/shared/types';

export const IP_POOL_POOL_TYPE_VALUES = ['DYNAMIC', 'STATIC', 'NAT'] as const;
export type IpPoolPoolType = (typeof IP_POOL_POOL_TYPE_VALUES)[number];
export const IP_POOL_POOL_TYPE_LABELS: Record<IpPoolPoolType, string> = {
  DYNAMIC: 'Dynamic',
  STATIC: 'Static',
  NAT: 'Nat',
};
export const IP_POOL_POOL_TYPE_OPTIONS = IP_POOL_POOL_TYPE_VALUES.map((value) => ({ value, label: IP_POOL_POOL_TYPE_LABELS[value] }));

export const IP_POOL_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type IpPoolLifecycleStage = (typeof IP_POOL_LIFECYCLE_STAGE_VALUES)[number];
export const IP_POOL_LIFECYCLE_STAGE_LABELS: Record<IpPoolLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const IP_POOL_LIFECYCLE_STAGE_OPTIONS = IP_POOL_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: IP_POOL_LIFECYCLE_STAGE_LABELS[value] }));

export interface IpPool {
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
  subnetId: string;
  rangeStart: string;
  rangeEnd: string;
  poolType?: IpPoolPoolType;
  leaseSeconds?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: IpPoolLifecycleStage;
  sourceSystem?: string;
}

export interface IpPoolInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  subnetId: string;
  rangeStart: string;
  rangeEnd: string;
  poolType?: IpPoolPoolType;
  leaseSeconds?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: IpPoolLifecycleStage;
  sourceSystem?: string;
}

export type IpPoolFormValues = Partial<IpPoolInput>;

export interface IpPoolUpdateInput extends Partial<IpPoolInput> {
  expectedVersion?: number;
}

export interface IpPoolFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  subnetId?: string;
  rangeStart?: string;
  rangeEnd?: string;
  poolType?: IpPoolPoolType[];
  leaseSecondsMin?: number;
  leaseSecondsMax?: number;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: IpPoolLifecycleStage[];
  sourceSystem?: string;
}

export type IpPoolSort = SortState<keyof IpPool & string>;

export interface IpPoolSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const IPPOOL_PATH = '/api/v1/ip-pools';
export const IPPOOL_TITLE = 'Ip Pool';
export const IPPOOL_TITLE_PLURAL = 'Ip Pools';

export const IPPOOL_DEFAULTS: IpPoolFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  rangeStart: '',
  rangeEnd: '',
};
