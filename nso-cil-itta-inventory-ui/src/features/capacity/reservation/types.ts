import type { LifecycleStatus, SortState } from '@/shared/types';

export const RESERVATION_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ReservationLifecycleStage = (typeof RESERVATION_LIFECYCLE_STAGE_VALUES)[number];
export const RESERVATION_LIFECYCLE_STAGE_LABELS: Record<ReservationLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const RESERVATION_LIFECYCLE_STAGE_OPTIONS = RESERVATION_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: RESERVATION_LIFECYCLE_STAGE_LABELS[value] }));

export interface Reservation {
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
  resourceRef: string;
  reservedFor: string;
  quantity: number;
  startDate: string;
  endDate?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ReservationLifecycleStage;
}

export interface ReservationInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  resourceRef: string;
  reservedFor: string;
  quantity: number;
  startDate: string;
  endDate?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ReservationLifecycleStage;
}

export type ReservationFormValues = Partial<ReservationInput>;

export interface ReservationUpdateInput extends Partial<ReservationInput> {
  expectedVersion?: number;
}

export interface ReservationFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  resourceRef?: string;
  reservedFor?: string;
  quantityMin?: number;
  quantityMax?: number;
  startDateFrom?: string;
  startDateTo?: string;
  endDateFrom?: string;
  endDateTo?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ReservationLifecycleStage[];
}

export type ReservationSort = SortState<keyof Reservation & string>;

export interface ReservationSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const RESERVATION_PATH = '/api/v1/reservations';
export const RESERVATION_TITLE = 'Reservation';
export const RESERVATION_TITLE_PLURAL = 'Reservations';

export const RESERVATION_DEFAULTS: ReservationFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  resourceRef: '',
  reservedFor: '',
};
