import type { LifecycleStatus, SortState } from '@/shared/types';

export const RACK_UNIT_FACE_VALUES = ['FRONT', 'REAR'] as const;
export type RackUnitFace = (typeof RACK_UNIT_FACE_VALUES)[number];
export const RACK_UNIT_FACE_LABELS: Record<RackUnitFace, string> = {
  FRONT: 'Front',
  REAR: 'Rear',
};
export const RACK_UNIT_FACE_OPTIONS = RACK_UNIT_FACE_VALUES.map((value) => ({ value, label: RACK_UNIT_FACE_LABELS[value] }));

export const RACK_UNIT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type RackUnitCriticality = (typeof RACK_UNIT_CRITICALITY_VALUES)[number];
export const RACK_UNIT_CRITICALITY_LABELS: Record<RackUnitCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const RACK_UNIT_CRITICALITY_OPTIONS = RACK_UNIT_CRITICALITY_VALUES.map((value) => ({ value, label: RACK_UNIT_CRITICALITY_LABELS[value] }));

export interface RackUnit {
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
  rackId: string;
  unitNumber: number;
  occupied?: boolean;
  mountedDeviceId?: string;
  face?: RackUnitFace;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RackUnitCriticality;
  costCenter?: string;
}

export interface RackUnitInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  rackId: string;
  unitNumber: number;
  occupied?: boolean;
  mountedDeviceId?: string;
  face?: RackUnitFace;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RackUnitCriticality;
  costCenter?: string;
}

export type RackUnitFormValues = Partial<RackUnitInput>;

export interface RackUnitUpdateInput extends Partial<RackUnitInput> {
  expectedVersion?: number;
}

export interface RackUnitFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  rackId?: string;
  unitNumberMin?: number;
  unitNumberMax?: number;
  occupied?: boolean;
  mountedDeviceId?: string;
  face?: RackUnitFace[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: RackUnitCriticality[];
  costCenter?: string;
}

export type RackUnitSort = SortState<keyof RackUnit & string>;

export interface RackUnitSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RACKUNIT_PATH = '/api/v1/rack-units';
export const RACKUNIT_TITLE = 'Rack Unit';
export const RACKUNIT_TITLE_PLURAL = 'Rack Units';

export const RACKUNIT_DEFAULTS: RackUnitFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  occupied: false,
};
