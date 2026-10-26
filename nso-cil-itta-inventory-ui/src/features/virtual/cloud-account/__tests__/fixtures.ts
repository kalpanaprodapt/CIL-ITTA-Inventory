import type { CloudAccount, CloudAccountInput } from '../types';

export const cloudAccountInput: CloudAccountInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  provider: 'AZURE',
  accountRef: 'accountRef-1',
  subscriptionName: 'subscriptionName-1',
  monthlyBudget: 13.5,
  environment: 'environment-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'CLOUDA-0001',
  name: 'Cloud Account one',
  status: 'ACTIVE',
};

export const cloudAccountFixture: CloudAccount = {
  ...cloudAccountInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const cloudAccountPage = {
  content: [cloudAccountFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
