import type { TopologyView, TopologyViewInput } from '../types';

export const topologyViewInput: TopologyViewInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  viewName: 'viewName-1',
  viewType: 'PHYSICAL',
  scopeFilter: 'scopeFilter-1',
  refreshedAt: '2026-02-11T10:15:30Z',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'TOPOLO-0001',
  name: 'Topology View one',
  status: 'ACTIVE',
};

export const topologyViewFixture: TopologyView = {
  ...topologyViewInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const topologyViewPage = {
  content: [topologyViewFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
