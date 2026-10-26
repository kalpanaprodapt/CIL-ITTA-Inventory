import type { PatchPort, PatchPortInput } from '../types';

export const patchPortInput: PatchPortInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  panelId: '00000000-0000-4000-8000-000000000011',
  portNumber: 11,
  connectedCableId: '00000000-0000-4000-8000-000000000011',
  polarity: 'polarity-1',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'PATCHP-0001',
  name: 'Patch Port one',
  status: 'ACTIVE',
};

export const patchPortFixture: PatchPort = {
  ...patchPortInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const patchPortPage = {
  content: [patchPortFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
