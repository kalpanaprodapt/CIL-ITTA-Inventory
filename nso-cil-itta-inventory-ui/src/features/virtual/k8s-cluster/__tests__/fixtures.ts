import type { K8sCluster, K8sClusterInput } from '../types';

export const k8sClusterInput: K8sClusterInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  clusterName: 'clusterName-1',
  clusterVersion: 'clusterVersion-1',
  nodeCount: 11,
  cloudAccountId: '00000000-0000-4000-8000-000000000011',
  region: 'region-1',
  apiEndpoint: 'apiEndpoint-1',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  owner: 'owner-1',
  code: 'K8SCLU-0001',
  name: 'K8s Cluster one',
  status: 'ACTIVE',
};

export const k8sClusterFixture: K8sCluster = {
  ...k8sClusterInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const k8sClusterPage = {
  content: [k8sClusterFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
