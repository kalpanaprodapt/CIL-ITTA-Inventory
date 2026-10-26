import type { CircuitProvider, CircuitProviderInput } from '../types';

export const circuitProviderInput: CircuitProviderInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  providerName: 'providerName-1',
  accountNumber: 'accountNumber-1',
  noc: 'noc-1',
  escalationEmail: 'escalationEmail-1',
  providerType: 'TIER1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'CIRCUI-0001',
  name: 'Circuit Provider one',
  status: 'ACTIVE',
};

export const circuitProviderFixture: CircuitProvider = {
  ...circuitProviderInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const circuitProviderPage = {
  content: [circuitProviderFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
