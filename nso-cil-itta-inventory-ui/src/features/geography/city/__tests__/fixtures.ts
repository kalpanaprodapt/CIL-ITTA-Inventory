import type { City, CityInput } from '../types';

export const cityInput: CityInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  regionId: '00000000-0000-4000-8000-000000000011',
  postalPrefix: 'postalPrefix-1',
  population: 1001,
  latitude: 13.5,
  longitude: 13.5,
  metroArea: 'metroArea-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'CITY-0001',
  name: 'City one',
  status: 'ACTIVE',
};

export const cityFixture: City = {
  ...cityInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const cityPage = {
  content: [cityFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
