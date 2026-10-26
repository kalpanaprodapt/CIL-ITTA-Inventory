import type { VrfInstance, VrfInstanceInput } from '../types';

export const vrfInstanceInput: VrfInstanceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vrfName: 'vrfName-1',
  routeDistinguisher: 'routeDistinguisher-1',
  importTargets: 'importTargets-1',
  exportTargets: 'exportTargets-1',
  deviceId: '00000000-0000-4000-8000-000000000011',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'VRFINS-0001',
  name: 'Vrf Instance one',
  status: 'ACTIVE',
};

export const vrfInstanceFixture: VrfInstance = {
  ...vrfInstanceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const vrfInstancePage = {
  content: [vrfInstanceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
