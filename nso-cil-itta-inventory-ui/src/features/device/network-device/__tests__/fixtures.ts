import type { NetworkDevice, NetworkDeviceInput } from '../types';

export const networkDeviceInput: NetworkDeviceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  hostname: 'hostname-1',
  deviceRole: 'CORE',
  mgmtIp: 'mgmtIp-1',
  serialNumber: 'serialNumber-1',
  modelId: '00000000-0000-4000-8000-000000000011',
  rackUnitId: '00000000-0000-4000-8000-000000000011',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'NETWOR-0001',
  name: 'Network Device one',
  status: 'ACTIVE',
};

export const networkDeviceFixture: NetworkDevice = {
  ...networkDeviceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const networkDevicePage = {
  content: [networkDeviceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
