import type { CircuitSegment, CircuitSegmentInput } from '../types';

export const circuitSegmentInput: CircuitSegmentInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  circuitId: '00000000-0000-4000-8000-000000000011',
  segmentOrder: 11,
  aTerminationId: '00000000-0000-4000-8000-000000000011',
  zTerminationId: '00000000-0000-4000-8000-000000000011',
  latencyMs: 13.5,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'CIRCUI-0001',
  name: 'Circuit Segment one',
  status: 'ACTIVE',
};

export const circuitSegmentFixture: CircuitSegment = {
  ...circuitSegmentInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const circuitSegmentPage = {
  content: [circuitSegmentFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
