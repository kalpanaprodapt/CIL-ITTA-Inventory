import type { SyncMapping, SyncMappingInput } from '../types';

export const syncMappingInput: SyncMappingInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  jobId: '00000000-0000-4000-8000-000000000011',
  sourceField: 'sourceField-1',
  targetField: 'targetField-1',
  transform: 'transform-1',
  required: true,
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'SYNCMA-0001',
  name: 'Sync Mapping one',
  status: 'ACTIVE',
};

export const syncMappingFixture: SyncMapping = {
  ...syncMappingInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const syncMappingPage = {
  content: [syncMappingFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
