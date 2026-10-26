import type { ServiceInstance, ServiceInstanceInput } from '../types';

export const serviceInstanceInput: ServiceInstanceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  catalogItemId: '00000000-0000-4000-8000-000000000011',
  customerId: '00000000-0000-4000-8000-000000000011',
  activatedOn: '2026-02-11',
  bandwidthMbps: 1001,
  serviceState: 'PLANNED',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'SERVIC-0001',
  name: 'Service Instance one',
  status: 'ACTIVE',
};

export const serviceInstanceFixture: ServiceInstance = {
  ...serviceInstanceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const serviceInstancePage = {
  content: [serviceInstanceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
