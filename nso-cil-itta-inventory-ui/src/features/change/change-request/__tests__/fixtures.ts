import type { ChangeRequest, ChangeRequestInput } from '../types';

export const changeRequestInput: ChangeRequestInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  changeNumber: 'changeNumber-1',
  changeType: 'STANDARD',
  riskLevel: 'LOW',
  plannedStart: '2026-02-11T10:15:30Z',
  plannedEnd: '2026-02-11T10:15:30Z',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'CHANGE-0001',
  name: 'Change Request one',
  status: 'ACTIVE',
};

export const changeRequestFixture: ChangeRequest = {
  ...changeRequestInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const changeRequestPage = {
  content: [changeRequestFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
