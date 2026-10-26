import type { ComplianceRule, ComplianceRuleInput } from '../types';

export const complianceRuleInput: ComplianceRuleInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  ruleCode: 'ruleCode-1',
  framework: 'ISO27001',
  severity: 'LOW',
  expression: 'expression-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'COMPLI-0001',
  name: 'Compliance Rule one',
  status: 'ACTIVE',
};

export const complianceRuleFixture: ComplianceRule = {
  ...complianceRuleInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const complianceRulePage = {
  content: [complianceRuleFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
