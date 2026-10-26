import type { Building, BuildingInput } from '../types';

export const buildingInput: BuildingInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  siteId: '00000000-0000-4000-8000-000000000011',
  floors: 11,
  yearBuilt: 11,
  ownership: 'OWNED',
  leaseExpiry: '2026-02-11',
  securityLevel: 11,
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'BUILDI-0001',
  name: 'Building one',
  status: 'ACTIVE',
};

export const buildingFixture: Building = {
  ...buildingInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const buildingPage = {
  content: [buildingFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
