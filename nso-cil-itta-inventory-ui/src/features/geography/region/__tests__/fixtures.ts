import type { Region, RegionInput } from '../types';

export const regionInput: RegionInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  countryId: '00000000-0000-4000-8000-000000000011',
  regionType: 'STATE',
  population: 1001,
  latitude: 13.5,
  longitude: 13.5,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'REGION-0001',
  name: 'Region one',
  status: 'ACTIVE',
};

export const regionFixture: Region = {
  ...regionInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const regionPage = {
  content: [regionFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
