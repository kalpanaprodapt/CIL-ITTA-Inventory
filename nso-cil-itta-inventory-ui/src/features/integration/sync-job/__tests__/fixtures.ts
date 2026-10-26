import type { SyncJob, SyncJobInput } from '../types';

export const syncJobInput: SyncJobInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  endpointId: '00000000-0000-4000-8000-000000000011',
  direction: 'INBOUND',
  scheduleCron: 'scheduleCron-1',
  lastRunAt: '2026-02-11T10:15:30Z',
  lastStatus: 'OK',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'SYNCJO-0001',
  name: 'Sync Job one',
  status: 'ACTIVE',
};

export const syncJobFixture: SyncJob = {
  ...syncJobInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const syncJobPage = {
  content: [syncJobFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
