import type { TopologyNode, TopologyNodeInput } from '../types';

export const topologyNodeInput: TopologyNodeInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  viewId: '00000000-0000-4000-8000-000000000011',
  entityRef: 'entityRef-1',
  nodeType: 'nodeType-1',
  xPos: 13.5,
  yPos: 13.5,
  layer: 11,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'TOPOLO-0001',
  name: 'Topology Node one',
  status: 'ACTIVE',
};

export const topologyNodeFixture: TopologyNode = {
  ...topologyNodeInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const topologyNodePage = {
  content: [topologyNodeFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
