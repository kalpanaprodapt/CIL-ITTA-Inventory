import type { CircuitTermination, CircuitTerminationInput } from '../types';

export const circuitTerminationInput: CircuitTerminationInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  circuitId: '00000000-0000-4000-8000-000000000011',
  side: 'A',
  portId: '00000000-0000-4000-8000-000000000011',
  xconnectId: 'xconnectId-1',
  patchInfo: 'patchInfo-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'CIRCUI-0001',
  name: 'Circuit Termination one',
  status: 'ACTIVE',
};

export const circuitTerminationFixture: CircuitTermination = {
  ...circuitTerminationInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const circuitTerminationPage = {
  content: [circuitTerminationFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
