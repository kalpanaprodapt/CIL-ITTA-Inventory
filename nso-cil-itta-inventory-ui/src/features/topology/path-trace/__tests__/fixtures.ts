import type { PathTrace, PathTraceInput } from '../types';

export const pathTraceInput: PathTraceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  sourceRef: 'sourceRef-1',
  targetRef: 'targetRef-1',
  hopCount: 11,
  totalLatencyMs: 13.5,
  traceMethod: 'traceMethod-1',
  tracedAt: '2026-02-11T10:15:30Z',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'PATHTR-0001',
  name: 'Path Trace one',
  status: 'ACTIVE',
};

export const pathTraceFixture: PathTrace = {
  ...pathTraceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const pathTracePage = {
  content: [pathTraceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
