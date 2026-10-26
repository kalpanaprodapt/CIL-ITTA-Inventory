import type { ReconciliationResult, ReconciliationResultInput } from '../types';

export const reconciliationResultInput: ReconciliationResultInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  jobId: '00000000-0000-4000-8000-000000000011',
  ruleId: '00000000-0000-4000-8000-000000000011',
  outcome: 'MATCHED',
  diffSummary: 'diffSummary-1',
  resolvedBy: 'resolvedBy-1',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'RECONC-0001',
  name: 'Reconciliation Result one',
  status: 'ACTIVE',
};

export const reconciliationResultFixture: ReconciliationResult = {
  ...reconciliationResultInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const reconciliationResultPage = {
  content: [reconciliationResultFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
