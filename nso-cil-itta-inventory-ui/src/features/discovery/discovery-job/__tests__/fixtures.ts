import type { DiscoveryJob, DiscoveryJobInput } from '../types';

export const discoveryJobInput: DiscoveryJobInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  profileId: '00000000-0000-4000-8000-000000000011',
  startedAt: '2026-02-11T10:15:30Z',
  finishedAt: '2026-02-11T10:15:30Z',
  jobState: 'QUEUED',
  assetsFound: 11,
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'DISCOV-0001',
  name: 'Discovery Job one',
  status: 'ACTIVE',
};

export const discoveryJobFixture: DiscoveryJob = {
  ...discoveryJobInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const discoveryJobPage = {
  content: [discoveryJobFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
