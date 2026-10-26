import type { ReconciliationRule, ReconciliationRuleInput } from '../types';

export const reconciliationRuleInput: ReconciliationRuleInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  ruleName: 'ruleName-1',
  matchKey: 'matchKey-1',
  action: 'MERGE',
  priority: 11,
  expression: 'expression-1',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'RECONC-0001',
  name: 'Reconciliation Rule one',
  status: 'ACTIVE',
};

export const reconciliationRuleFixture: ReconciliationRule = {
  ...reconciliationRuleInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const reconciliationRulePage = {
  content: [reconciliationRuleFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
