import type { PseudoWire, PseudoWireInput } from '../types';

export const pseudoWireInput: PseudoWireInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vcId: 1001,
  aEndId: '00000000-0000-4000-8000-000000000011',
  zEndId: '00000000-0000-4000-8000-000000000011',
  encapsulation: 'encapsulation-1',
  controlWord: true,
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'PSEUDO-0001',
  name: 'Pseudo Wire one',
  status: 'ACTIVE',
};

export const pseudoWireFixture: PseudoWire = {
  ...pseudoWireInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const pseudoWirePage = {
  content: [pseudoWireFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
