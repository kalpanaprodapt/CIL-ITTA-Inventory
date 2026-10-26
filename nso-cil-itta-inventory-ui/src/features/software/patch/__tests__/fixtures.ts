import type { Patch, PatchInput } from '../types';

export const patchInput: PatchInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  versionId: '00000000-0000-4000-8000-000000000011',
  patchId: 'patchId-1',
  severity: 'LOW',
  releasedOn: '2026-02-11',
  rebootRequired: true,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'PATCH-0001',
  name: 'Patch one',
  status: 'ACTIVE',
};

export const patchFixture: Patch = {
  ...patchInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const patchPage = {
  content: [patchFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
