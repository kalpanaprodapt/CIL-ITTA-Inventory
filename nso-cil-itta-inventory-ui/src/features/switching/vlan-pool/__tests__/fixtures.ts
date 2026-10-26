import type { VlanPool, VlanPoolInput } from '../types';

export const vlanPoolInput: VlanPoolInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  poolName: 'poolName-1',
  rangeStart: 11,
  rangeEnd: 11,
  allocated: 11,
  scope: 'scope-1',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'VLANPO-0001',
  name: 'Vlan Pool one',
  status: 'ACTIVE',
};

export const vlanPoolFixture: VlanPool = {
  ...vlanPoolInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const vlanPoolPage = {
  content: [vlanPoolFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
