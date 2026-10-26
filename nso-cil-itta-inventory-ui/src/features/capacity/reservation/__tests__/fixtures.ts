import type { Reservation, ReservationInput } from '../types';

export const reservationInput: ReservationInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  resourceRef: 'resourceRef-1',
  reservedFor: 'reservedFor-1',
  quantity: 13.5,
  startDate: '2026-02-11',
  endDate: '2026-02-11',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'RESERV-0001',
  name: 'Reservation one',
  status: 'ACTIVE',
};

export const reservationFixture: Reservation = {
  ...reservationInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const reservationPage = {
  content: [reservationFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
