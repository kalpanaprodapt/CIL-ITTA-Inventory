import type { CircuitOrder, CircuitOrderInput } from '../types';

export const circuitOrderInput: CircuitOrderInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  circuitId: '00000000-0000-4000-8000-000000000011',
  orderNumber: 'orderNumber-1',
  orderedOn: '2026-02-11',
  requestedDate: '2026-02-11',
  committedDate: '2026-02-11',
  orderStatus: 'DRAFT',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'CIRCUI-0001',
  name: 'Circuit Order one',
  status: 'ACTIVE',
};

export const circuitOrderFixture: CircuitOrder = {
  ...circuitOrderInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const circuitOrderPage = {
  content: [circuitOrderFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
