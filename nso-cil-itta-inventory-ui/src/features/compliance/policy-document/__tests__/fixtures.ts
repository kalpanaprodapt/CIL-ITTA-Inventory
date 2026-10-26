import type { PolicyDocument, PolicyDocumentInput } from '../types';

export const policyDocumentInput: PolicyDocumentInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  title: 'title-1',
  docVersion: 'docVersion-1',
  effectiveDate: '2026-02-11',
  owner: 'owner-1',
  documentUrl: 'documentUrl-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'POLICY-0001',
  name: 'Policy Document one',
  status: 'ACTIVE',
};

export const policyDocumentFixture: PolicyDocument = {
  ...policyDocumentInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const policyDocumentPage = {
  content: [policyDocumentFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
