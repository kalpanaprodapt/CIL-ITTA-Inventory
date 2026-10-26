import type { RadioUnit, RadioUnitInput } from '../types';

export const radioUnitInput: RadioUnitInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  siteId: '00000000-0000-4000-8000-000000000011',
  unitType: 'RRU',
  bandMhz: 11,
  txPowerDbm: 13.5,
  serialNumber: 'serialNumber-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'RADIOU-0001',
  name: 'Radio Unit one',
  status: 'ACTIVE',
};

export const radioUnitFixture: RadioUnit = {
  ...radioUnitInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const radioUnitPage = {
  content: [radioUnitFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
