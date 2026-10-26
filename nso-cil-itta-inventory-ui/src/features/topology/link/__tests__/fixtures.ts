import type { Link, LinkInput } from '../types';

export const linkInput: LinkInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  aInterfaceId: '00000000-0000-4000-8000-000000000011',
  zInterfaceId: '00000000-0000-4000-8000-000000000011',
  linkType: 'PHYSICAL',
  speedMbps: 1001,
  discoveredBy: 'discoveredBy-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'LINK-0001',
  name: 'Link one',
  status: 'ACTIVE',
};

export const linkFixture: Link = {
  ...linkInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const linkPage = {
  content: [linkFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
