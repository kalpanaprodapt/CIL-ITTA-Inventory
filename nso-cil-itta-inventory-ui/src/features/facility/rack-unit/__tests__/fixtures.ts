import type { RackUnit, RackUnitInput } from '../types';

export const rackUnitInput: RackUnitInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  rackId: '00000000-0000-4000-8000-000000000011',
  unitNumber: 11,
  occupied: true,
  mountedDeviceId: '00000000-0000-4000-8000-000000000011',
  face: 'FRONT',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'RACKUN-0001',
  name: 'Rack Unit one',
  status: 'ACTIVE',
};

export const rackUnitFixture: RackUnit = {
  ...rackUnitInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const rackUnitPage = {
  content: [rackUnitFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
