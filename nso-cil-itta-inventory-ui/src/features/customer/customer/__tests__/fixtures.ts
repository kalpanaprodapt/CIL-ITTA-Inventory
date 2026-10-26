import type { Customer, CustomerInput } from '../types';

export const customerInput: CustomerInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  legalName: 'legalName-1',
  segment: 'ENTERPRISE',
  taxId: 'taxId-1',
  industry: 'industry-1',
  accountManager: 'accountManager-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'CUSTOM-0001',
  name: 'Customer one',
  status: 'ACTIVE',
};

export const customerFixture: Customer = {
  ...customerInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const customerPage = {
  content: [customerFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
