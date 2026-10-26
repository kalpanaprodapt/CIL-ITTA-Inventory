import type { TopologyEdge, TopologyEdgeInput } from '../types';

export const topologyEdgeInput: TopologyEdgeInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  viewId: '00000000-0000-4000-8000-000000000011',
  sourceNodeId: '00000000-0000-4000-8000-000000000011',
  targetNodeId: '00000000-0000-4000-8000-000000000011',
  weight: 13.5,
  directed: true,
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'TOPOLO-0001',
  name: 'Topology Edge one',
  status: 'ACTIVE',
};

export const topologyEdgeFixture: TopologyEdge = {
  ...topologyEdgeInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const topologyEdgePage = {
  content: [topologyEdgeFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
