import type { LifecycleStatus, SortState } from '@/shared/types';

export interface Floor {
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
  buildingId: string;
  level?: number;
  areaSqm?: number;
  loadCapacityKg?: number;
  hasRaisedFloor?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface FloorInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  buildingId: string;
  level?: number;
  areaSqm?: number;
  loadCapacityKg?: number;
  hasRaisedFloor?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type FloorFormValues = Partial<FloorInput>;

export interface FloorUpdateInput extends Partial<FloorInput> {
  expectedVersion?: number;
}

export interface FloorFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  buildingId?: string;
  levelMin?: number;
  levelMax?: number;
  areaSqmMin?: number;
  areaSqmMax?: number;
  loadCapacityKgMin?: number;
  loadCapacityKgMax?: number;
  hasRaisedFloor?: boolean;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type FloorSort = SortState<keyof Floor & string>;

export interface FloorSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const FLOOR_PATH = '/api/v1/floors';
export const FLOOR_TITLE = 'Floor';
export const FLOOR_TITLE_PLURAL = 'Floors';

export const FLOOR_DEFAULTS: FloorFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  hasRaisedFloor: false,
};
