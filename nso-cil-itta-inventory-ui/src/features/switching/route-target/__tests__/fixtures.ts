import type { RouteTarget, RouteTargetInput } from '../types';

export const routeTargetInput: RouteTargetInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  asn: 1001,
  assignedNumber: 1001,
  rtType: 'IMPORT',
  vrfId: '00000000-0000-4000-8000-000000000011',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'ROUTET-0001',
  name: 'Route Target one',
  status: 'ACTIVE',
};

export const routeTargetFixture: RouteTarget = {
  ...routeTargetInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const routeTargetPage = {
  content: [routeTargetFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
