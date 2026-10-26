import type { BgpSession, BgpSessionInput } from '../types';

export const bgpSessionInput: BgpSessionInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  localDeviceId: '00000000-0000-4000-8000-000000000011',
  peerAddress: 'peerAddress-1',
  localAsn: 1001,
  remoteAsn: 1001,
  sessionState: 'IDLE',
  prefixLimit: 11,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'BGPSES-0001',
  name: 'Bgp Session one',
  status: 'ACTIVE',
};

export const bgpSessionFixture: BgpSession = {
  ...bgpSessionInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const bgpSessionPage = {
  content: [bgpSessionFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
