import type { Rack, RackInput } from '../types';

export const rackInput: RackInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  roomId: '00000000-0000-4000-8000-000000000011',
  rackUnits: 11,
  widthMm: 11,
  depthMm: 11,
  maxWeightKg: 13.5,
  rowLabel: 'rowLabel-1',
  position: 'position-1',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'RACK-0001',
  name: 'Rack one',
  status: 'ACTIVE',
};

export const rackFixture: Rack = {
  ...rackInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const rackPage = {
  content: [rackFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
