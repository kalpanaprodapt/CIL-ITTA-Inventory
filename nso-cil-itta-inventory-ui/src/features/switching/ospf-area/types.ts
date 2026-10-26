import type { LifecycleStatus, SortState } from '@/shared/types';

export const OSPF_AREA_AREA_TYPE_VALUES = ['BACKBONE', 'STANDARD', 'STUB', 'NSSA'] as const;
export type OspfAreaAreaType = (typeof OSPF_AREA_AREA_TYPE_VALUES)[number];
export const OSPF_AREA_AREA_TYPE_LABELS: Record<OspfAreaAreaType, string> = {
  BACKBONE: 'Backbone',
  STANDARD: 'Standard',
  STUB: 'Stub',
  NSSA: 'Nssa',
};
export const OSPF_AREA_AREA_TYPE_OPTIONS = OSPF_AREA_AREA_TYPE_VALUES.map((value) => ({ value, label: OSPF_AREA_AREA_TYPE_LABELS[value] }));

export const OSPF_AREA_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type OspfAreaCriticality = (typeof OSPF_AREA_CRITICALITY_VALUES)[number];
export const OSPF_AREA_CRITICALITY_LABELS: Record<OspfAreaCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const OSPF_AREA_CRITICALITY_OPTIONS = OSPF_AREA_CRITICALITY_VALUES.map((value) => ({ value, label: OSPF_AREA_CRITICALITY_LABELS[value] }));

export interface OspfArea {
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
  areaId: string;
  areaType?: OspfAreaAreaType;
  deviceCount?: number;
  authType?: string;
  criticality?: OspfAreaCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface OspfAreaInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  areaId: string;
  areaType?: OspfAreaAreaType;
  deviceCount?: number;
  authType?: string;
  criticality?: OspfAreaCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type OspfAreaFormValues = Partial<OspfAreaInput>;

export interface OspfAreaUpdateInput extends Partial<OspfAreaInput> {
  expectedVersion?: number;
}

export interface OspfAreaFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  areaId?: string;
  areaType?: OspfAreaAreaType[];
  deviceCountMin?: number;
  deviceCountMax?: number;
  authType?: string;
  criticality?: OspfAreaCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type OspfAreaSort = SortState<keyof OspfArea & string>;

export interface OspfAreaSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const OSPFAREA_PATH = '/api/v1/ospf-areas';
export const OSPFAREA_TITLE = 'Ospf Area';
export const OSPFAREA_TITLE_PLURAL = 'Ospf Areas';

export const OSPFAREA_DEFAULTS: OspfAreaFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  areaId: '',
};
