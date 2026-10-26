import type { UtilizationSample, UtilizationSampleInput } from '../types';

export const utilizationSampleInput: UtilizationSampleInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  targetRef: 'targetRef-1',
  metric: 'metric-1',
  sampledAt: '2026-02-11T10:15:30Z',
  valuePct: 13.5,
  peak: true,
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'UTILIZ-0001',
  name: 'Utilization Sample one',
  status: 'ACTIVE',
};

export const utilizationSampleFixture: UtilizationSample = {
  ...utilizationSampleInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const utilizationSamplePage = {
  content: [utilizationSampleFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
