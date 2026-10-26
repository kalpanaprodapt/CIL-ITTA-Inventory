import type { SuppressionRule, SuppressionRuleInput } from '../types';

export const suppressionRuleInput: SuppressionRuleInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  ruleName: 'ruleName-1',
  matchExpression: 'matchExpression-1',
  startsAt: '2026-02-11T10:15:30Z',
  endsAt: '2026-02-11T10:15:30Z',
  reason: 'reason-1',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'SUPPRE-0001',
  name: 'Suppression Rule one',
  status: 'ACTIVE',
};

export const suppressionRuleFixture: SuppressionRule = {
  ...suppressionRuleInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const suppressionRulePage = {
  content: [suppressionRuleFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
