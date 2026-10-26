import type { CustomerAccount, CustomerAccountInput } from '../types';

export const customerAccountInput: CustomerAccountInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  customerId: '00000000-0000-4000-8000-000000000011',
  accountNumber: 'accountNumber-1',
  billingCycle: 'MONTHLY',
  currency: 'currency-1',
  creditLimit: 13.5,
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'CUSTOM-0001',
  name: 'Customer Account one',
  status: 'ACTIVE',
};

export const customerAccountFixture: CustomerAccount = {
  ...customerAccountInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const customerAccountPage = {
  content: [customerAccountFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
