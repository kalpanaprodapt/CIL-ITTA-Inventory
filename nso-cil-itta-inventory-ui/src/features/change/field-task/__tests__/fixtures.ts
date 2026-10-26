import type { FieldTask, FieldTaskInput } from '../types';

export const fieldTaskInput: FieldTaskInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  workOrderId: '00000000-0000-4000-8000-000000000011',
  taskName: 'taskName-1',
  sequence: 11,
  durationMinutes: 11,
  completedOn: '2026-02-11T10:15:30Z',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'FIELDT-0001',
  name: 'Field Task one',
  status: 'ACTIVE',
};

export const fieldTaskFixture: FieldTask = {
  ...fieldTaskInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const fieldTaskPage = {
  content: [fieldTaskFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
