import type { LifecycleStatus, SortState } from '@/shared/types';

export const ROOM_ROOM_TYPE_VALUES = ['MMR', 'COLO', 'POWER', 'BATTERY', 'STAGING', 'NOC'] as const;
export type RoomRoomType = (typeof ROOM_ROOM_TYPE_VALUES)[number];
export const ROOM_ROOM_TYPE_LABELS: Record<RoomRoomType, string> = {
  MMR: 'Mmr',
  COLO: 'Colo',
  POWER: 'Power',
  BATTERY: 'Battery',
  STAGING: 'Staging',
  NOC: 'Noc',
};
export const ROOM_ROOM_TYPE_OPTIONS = ROOM_ROOM_TYPE_VALUES.map((value) => ({ value, label: ROOM_ROOM_TYPE_LABELS[value] }));

export const ROOM_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type RoomLifecycleStage = (typeof ROOM_LIFECYCLE_STAGE_VALUES)[number];
export const ROOM_LIFECYCLE_STAGE_LABELS: Record<RoomLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const ROOM_LIFECYCLE_STAGE_OPTIONS = ROOM_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: ROOM_LIFECYCLE_STAGE_LABELS[value] }));

export interface Room {
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
  floorId: string;
  roomType?: RoomRoomType;
  areaSqm?: number;
  coolingTons?: number;
  fireZone?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: RoomLifecycleStage;
  sourceSystem?: string;
}

export interface RoomInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  floorId: string;
  roomType?: RoomRoomType;
  areaSqm?: number;
  coolingTons?: number;
  fireZone?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: RoomLifecycleStage;
  sourceSystem?: string;
}

export type RoomFormValues = Partial<RoomInput>;

export interface RoomUpdateInput extends Partial<RoomInput> {
  expectedVersion?: number;
}

export interface RoomFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  floorId?: string;
  roomType?: RoomRoomType[];
  areaSqmMin?: number;
  areaSqmMax?: number;
  coolingTonsMin?: number;
  coolingTonsMax?: number;
  fireZone?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: RoomLifecycleStage[];
  sourceSystem?: string;
}

export type RoomSort = SortState<keyof Room & string>;

export interface RoomSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ROOM_PATH = '/api/v1/rooms';
export const ROOM_TITLE = 'Room';
export const ROOM_TITLE_PLURAL = 'Rooms';

export const ROOM_DEFAULTS: RoomFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
