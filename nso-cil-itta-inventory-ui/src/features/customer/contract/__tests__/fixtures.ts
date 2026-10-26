import type { Contract, ContractInput } from '../types';

export const contractInput: ContractInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  customerId: '00000000-0000-4000-8000-000000000011',
  contractNumber: 'contractNumber-1',
  startDate: '2026-02-11',
  endDate: '2026-02-11',
  termMonths: 11,
  autoRenew: true,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'CONTRA-0001',
  name: 'Contract one',
  status: 'ACTIVE',
};

export const contractFixture: Contract = {
  ...contractInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const contractPage = {
  content: [contractFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
