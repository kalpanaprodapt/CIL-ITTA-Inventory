import type { FiberStrand, FiberStrandInput } from '../types';

export const fiberStrandInput: FiberStrandInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  cableId: '00000000-0000-4000-8000-000000000011',
  strandNumber: 11,
  bufferTube: 'bufferTube-1',
  tubeColor: 'tubeColor-1',
  attenuationDb: 13.5,
  inUse: true,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'FIBERS-0001',
  name: 'Fiber Strand one',
  status: 'ACTIVE',
};

export const fiberStrandFixture: FiberStrand = {
  ...fiberStrandInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const fiberStrandPage = {
  content: [fiberStrandFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
