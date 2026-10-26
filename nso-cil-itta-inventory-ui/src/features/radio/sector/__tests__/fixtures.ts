import type { Sector, SectorInput } from '../types';

export const sectorInput: SectorInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  baseStationId: '00000000-0000-4000-8000-000000000011',
  sectorNumber: 11,
  pci: 11,
  frequencyMhz: 11,
  bandwidthMhz: 11,
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'SECTOR-0001',
  name: 'Sector one',
  status: 'ACTIVE',
};

export const sectorFixture: Sector = {
  ...sectorInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const sectorPage = {
  content: [sectorFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
