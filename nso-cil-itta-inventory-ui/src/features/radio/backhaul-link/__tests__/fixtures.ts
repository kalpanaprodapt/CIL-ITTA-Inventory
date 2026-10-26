import type { BackhaulLink, BackhaulLinkInput } from '../types';

export const backhaulLinkInput: BackhaulLinkInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  baseStationId: '00000000-0000-4000-8000-000000000011',
  linkMedium: 'FIBER',
  capacityMbps: 1001,
  latencyMs: 13.5,
  circuitRef: 'circuitRef-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'BACKHA-0001',
  name: 'Backhaul Link one',
  status: 'ACTIVE',
};

export const backhaulLinkFixture: BackhaulLink = {
  ...backhaulLinkInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const backhaulLinkPage = {
  content: [backhaulLinkFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
