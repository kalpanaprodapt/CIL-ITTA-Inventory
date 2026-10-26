import type { LifecycleStatus, SortState } from '@/shared/types';

export const ROUTE_TARGET_RT_TYPE_VALUES = ['IMPORT', 'EXPORT', 'BOTH'] as const;
export type RouteTargetRtType = (typeof ROUTE_TARGET_RT_TYPE_VALUES)[number];
export const ROUTE_TARGET_RT_TYPE_LABELS: Record<RouteTargetRtType, string> = {
  IMPORT: 'Import',
  EXPORT: 'Export',
  BOTH: 'Both',
};
export const ROUTE_TARGET_RT_TYPE_OPTIONS = ROUTE_TARGET_RT_TYPE_VALUES.map((value) => ({ value, label: ROUTE_TARGET_RT_TYPE_LABELS[value] }));

export const ROUTE_TARGET_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type RouteTargetLifecycleStage = (typeof ROUTE_TARGET_LIFECYCLE_STAGE_VALUES)[number];
export const ROUTE_TARGET_LIFECYCLE_STAGE_LABELS: Record<RouteTargetLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const ROUTE_TARGET_LIFECYCLE_STAGE_OPTIONS = ROUTE_TARGET_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: ROUTE_TARGET_LIFECYCLE_STAGE_LABELS[value] }));

export const ROUTE_TARGET_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type RouteTargetCriticality = (typeof ROUTE_TARGET_CRITICALITY_VALUES)[number];
export const ROUTE_TARGET_CRITICALITY_LABELS: Record<RouteTargetCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const ROUTE_TARGET_CRITICALITY_OPTIONS = ROUTE_TARGET_CRITICALITY_VALUES.map((value) => ({ value, label: ROUTE_TARGET_CRITICALITY_LABELS[value] }));

export interface RouteTarget {
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
  asn: number;
  assignedNumber: number;
  rtType?: RouteTargetRtType;
  vrfId?: string;
  lifecycleStage?: RouteTargetLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RouteTargetCriticality;
}

export interface RouteTargetInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  asn: number;
  assignedNumber: number;
  rtType?: RouteTargetRtType;
  vrfId?: string;
  lifecycleStage?: RouteTargetLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RouteTargetCriticality;
}

export type RouteTargetFormValues = Partial<RouteTargetInput>;

export interface RouteTargetUpdateInput extends Partial<RouteTargetInput> {
  expectedVersion?: number;
}

export interface RouteTargetFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  asnMin?: number;
  asnMax?: number;
  assignedNumberMin?: number;
  assignedNumberMax?: number;
  rtType?: RouteTargetRtType[];
  vrfId?: string;
  lifecycleStage?: RouteTargetLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RouteTargetCriticality[];
}

export type RouteTargetSort = SortState<keyof RouteTarget & string>;

export interface RouteTargetSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ROUTETARGET_PATH = '/api/v1/route-targets';
export const ROUTETARGET_TITLE = 'Route Target';
export const ROUTETARGET_TITLE_PLURAL = 'Route Targets';

export const ROUTETARGET_DEFAULTS: RouteTargetFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
