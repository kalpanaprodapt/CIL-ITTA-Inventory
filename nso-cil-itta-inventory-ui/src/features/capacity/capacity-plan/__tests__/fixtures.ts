import type { CapacityPlan, CapacityPlanInput } from '../types';

export const capacityPlanInput: CapacityPlanInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  planName: 'planName-1',
  horizonMonths: 11,
  targetUtilizationPct: 13.5,
  owner: 'owner-1',
  approvedOn: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'CAPACI-0001',
  name: 'Capacity Plan one',
  status: 'ACTIVE',
};

export const capacityPlanFixture: CapacityPlan = {
  ...capacityPlanInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const capacityPlanPage = {
  content: [capacityPlanFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
