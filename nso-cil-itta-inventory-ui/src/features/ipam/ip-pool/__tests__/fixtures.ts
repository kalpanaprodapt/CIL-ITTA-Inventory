import type { IpPool, IpPoolInput } from '../types';

export const ipPoolInput: IpPoolInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  subnetId: '00000000-0000-4000-8000-000000000011',
  rangeStart: 'rangeStart-1',
  rangeEnd: 'rangeEnd-1',
  poolType: 'DYNAMIC',
  leaseSeconds: 11,
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'IPPOOL-0001',
  name: 'Ip Pool one',
  status: 'ACTIVE',
};

export const ipPoolFixture: IpPool = {
  ...ipPoolInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const ipPoolPage = {
  content: [ipPoolFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
