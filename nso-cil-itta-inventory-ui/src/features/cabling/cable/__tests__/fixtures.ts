import type { Cable, CableInput } from '../types';

export const cableInput: CableInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  cableType: 'CAT6',
  lengthM: 13.5,
  aEndPortId: '00000000-0000-4000-8000-000000000011',
  zEndPortId: '00000000-0000-4000-8000-000000000011',
  color: 'color-1',
  label: 'label-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'CABLE-0001',
  name: 'Cable one',
  status: 'ACTIVE',
};

export const cableFixture: Cable = {
  ...cableInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const cablePage = {
  content: [cableFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
