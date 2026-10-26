import type { Circuit, CircuitInput } from '../types';

export const circuitInput: CircuitInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  circuitId: 'circuitId-1',
  circuitType: 'ETHERNET',
  bandwidthMbps: 1001,
  providerId: '00000000-0000-4000-8000-000000000011',
  aSiteId: '00000000-0000-4000-8000-000000000011',
  zSiteId: '00000000-0000-4000-8000-000000000011',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'CIRCUI-0001',
  name: 'Circuit one',
  status: 'ACTIVE',
};

export const circuitFixture: Circuit = {
  ...circuitInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const circuitPage = {
  content: [circuitFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
