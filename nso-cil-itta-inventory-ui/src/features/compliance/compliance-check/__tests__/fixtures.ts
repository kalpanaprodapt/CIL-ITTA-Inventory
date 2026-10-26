import type { ComplianceCheck, ComplianceCheckInput } from '../types';

export const complianceCheckInput: ComplianceCheckInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  ruleId: '00000000-0000-4000-8000-000000000011',
  targetRef: 'targetRef-1',
  checkedAt: '2026-02-11T10:15:30Z',
  result: 'PASS',
  evidence: 'evidence-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'COMPLI-0001',
  name: 'Compliance Check one',
  status: 'ACTIVE',
};

export const complianceCheckFixture: ComplianceCheck = {
  ...complianceCheckInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const complianceCheckPage = {
  content: [complianceCheckFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
