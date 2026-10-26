import type { QosPolicy, QosPolicyInput } from '../types';

export const qosPolicyInput: QosPolicyInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  policyName: 'policyName-1',
  classCount: 11,
  cirMbps: 1001,
  pirMbps: 1001,
  markingScheme: 'markingScheme-1',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'QOSPOL-0001',
  name: 'Qos Policy one',
  status: 'ACTIVE',
};

export const qosPolicyFixture: QosPolicy = {
  ...qosPolicyInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const qosPolicyPage = {
  content: [qosPolicyFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
