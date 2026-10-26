import type { BaseStation, BaseStationInput } from '../types';

export const baseStationInput: BaseStationInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  siteId: '00000000-0000-4000-8000-000000000011',
  enbId: 1001,
  technology: 'LTE',
  sectorCount: 11,
  cellCount: 11,
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'BASEST-0001',
  name: 'Base Station one',
  status: 'ACTIVE',
};

export const baseStationFixture: BaseStation = {
  ...baseStationInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const baseStationPage = {
  content: [baseStationFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
