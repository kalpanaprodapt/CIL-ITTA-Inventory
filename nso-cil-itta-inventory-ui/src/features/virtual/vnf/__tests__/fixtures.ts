import type { Vnf, VnfInput } from '../types';

export const vnfInput: VnfInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vnfName: 'vnfName-1',
  vnfType: 'vnfType-1',
  flavor: 'flavor-1',
  vcpu: 11,
  memoryGb: 11,
  instanceCount: 11,
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'VNF-0001',
  name: 'Vnf one',
  status: 'ACTIVE',
};

export const vnfFixture: Vnf = {
  ...vnfInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const vnfPage = {
  content: [vnfFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
