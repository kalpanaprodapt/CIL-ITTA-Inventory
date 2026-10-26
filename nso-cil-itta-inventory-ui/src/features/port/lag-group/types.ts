import type { LifecycleStatus, SortState } from '@/shared/types';

export const LAG_GROUP_LACP_MODE_VALUES = ['ACTIVE', 'PASSIVE', 'STATIC'] as const;
export type LagGroupLacpMode = (typeof LAG_GROUP_LACP_MODE_VALUES)[number];
export const LAG_GROUP_LACP_MODE_LABELS: Record<LagGroupLacpMode, string> = {
  ACTIVE: 'Active',
  PASSIVE: 'Passive',
  STATIC: 'Static',
};
export const LAG_GROUP_LACP_MODE_OPTIONS = LAG_GROUP_LACP_MODE_VALUES.map((value) => ({ value, label: LAG_GROUP_LACP_MODE_LABELS[value] }));

export const LAG_GROUP_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type LagGroupLifecycleStage = (typeof LAG_GROUP_LIFECYCLE_STAGE_VALUES)[number];
export const LAG_GROUP_LIFECYCLE_STAGE_LABELS: Record<LagGroupLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const LAG_GROUP_LIFECYCLE_STAGE_OPTIONS = LAG_GROUP_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: LAG_GROUP_LIFECYCLE_STAGE_LABELS[value] }));

export interface LagGroup {
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
  deviceId: string;
  lagName: string;
  memberCount?: number;
  lacpMode?: LagGroupLacpMode;
  minLinks?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LagGroupLifecycleStage;
  sourceSystem?: string;
}

export interface LagGroupInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  deviceId: string;
  lagName: string;
  memberCount?: number;
  lacpMode?: LagGroupLacpMode;
  minLinks?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: LagGroupLifecycleStage;
  sourceSystem?: string;
}

export type LagGroupFormValues = Partial<LagGroupInput>;

export interface LagGroupUpdateInput extends Partial<LagGroupInput> {
  expectedVersion?: number;
}

export interface LagGroupFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  deviceId?: string;
  lagName?: string;
  memberCountMin?: number;
  memberCountMax?: number;
  lacpMode?: LagGroupLacpMode[];
  minLinksMin?: number;
  minLinksMax?: number;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: LagGroupLifecycleStage[];
  sourceSystem?: string;
}

export type LagGroupSort = SortState<keyof LagGroup & string>;

export interface LagGroupSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const LAGGROUP_PATH = '/api/v1/lag-groups';
export const LAGGROUP_TITLE = 'Lag Group';
export const LAGGROUP_TITLE_PLURAL = 'Lag Groups';

export const LAGGROUP_DEFAULTS: LagGroupFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  lagName: '',
};
