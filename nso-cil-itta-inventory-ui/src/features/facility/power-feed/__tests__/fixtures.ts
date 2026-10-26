import type { PowerFeed, PowerFeedInput } from '../types';

export const powerFeedInput: PowerFeedInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  roomId: '00000000-0000-4000-8000-000000000011',
  feedType: 'AC',
  voltage: 13.5,
  amperage: 13.5,
  phase: 'phase-1',
  breakerLabel: 'breakerLabel-1',
  redundancyGroup: 'redundancyGroup-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'POWERF-0001',
  name: 'Power Feed one',
  status: 'ACTIVE',
};

export const powerFeedFixture: PowerFeed = {
  ...powerFeedInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const powerFeedPage = {
  content: [powerFeedFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
