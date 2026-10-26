import type { Chassis, ChassisInput } from '../types';

export const chassisInput: ChassisInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  deviceId: '00000000-0000-4000-8000-000000000011',
  slotCount: 11,
  serialNumber: 'serialNumber-1',
  backplaneGbps: 13.5,
  redundancyMode: 'redundancyMode-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'CHASSI-0001',
  name: 'Chassis one',
  status: 'ACTIVE',
};

export const chassisFixture: Chassis = {
  ...chassisInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const chassisPage = {
  content: [chassisFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
