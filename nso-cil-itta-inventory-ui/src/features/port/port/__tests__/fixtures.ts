import type { Port, PortInput } from '../types';

export const portInput: PortInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  deviceId: '00000000-0000-4000-8000-000000000011',
  cardId: '00000000-0000-4000-8000-000000000011',
  portNumber: 'portNumber-1',
  speedMbps: 1001,
  mediaType: 'COPPER',
  adminState: 'UP',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'PORT-0001',
  name: 'Port one',
  status: 'ACTIVE',
};

export const portFixture: Port = {
  ...portInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const portPage = {
  content: [portFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
