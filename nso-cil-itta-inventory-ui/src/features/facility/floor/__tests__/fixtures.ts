import type { Floor, FloorInput } from '../types';

export const floorInput: FloorInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  buildingId: '00000000-0000-4000-8000-000000000011',
  level: 11,
  areaSqm: 13.5,
  loadCapacityKg: 13.5,
  hasRaisedFloor: true,
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'FLOOR-0001',
  name: 'Floor one',
  status: 'ACTIVE',
};

export const floorFixture: Floor = {
  ...floorInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const floorPage = {
  content: [floorFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
