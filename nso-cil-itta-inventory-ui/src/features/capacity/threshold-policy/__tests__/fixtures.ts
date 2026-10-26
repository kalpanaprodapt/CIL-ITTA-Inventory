import type { ThresholdPolicy, ThresholdPolicyInput } from '../types';

export const thresholdPolicyInput: ThresholdPolicyInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  policyName: 'policyName-1',
  metric: 'metric-1',
  warnPct: 13.5,
  critPct: 13.5,
  evaluationWindowMinutes: 11,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'THRESH-0001',
  name: 'Threshold Policy one',
  status: 'ACTIVE',
};

export const thresholdPolicyFixture: ThresholdPolicy = {
  ...thresholdPolicyInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const thresholdPolicyPage = {
  content: [thresholdPolicyFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
