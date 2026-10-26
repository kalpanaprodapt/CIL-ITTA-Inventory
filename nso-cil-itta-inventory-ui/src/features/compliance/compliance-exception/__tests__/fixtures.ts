import type { ComplianceException, ComplianceExceptionInput } from '../types';

export const complianceExceptionInput: ComplianceExceptionInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  ruleId: '00000000-0000-4000-8000-000000000011',
  targetRef: 'targetRef-1',
  justification: 'justification-1',
  approvedBy: 'approvedBy-1',
  expiresOn: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'COMPLI-0001',
  name: 'Compliance Exception one',
  status: 'ACTIVE',
};

export const complianceExceptionFixture: ComplianceException = {
  ...complianceExceptionInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const complianceExceptionPage = {
  content: [complianceExceptionFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
