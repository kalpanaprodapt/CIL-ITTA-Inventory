import type { DiscoveryProfile, DiscoveryProfileInput } from '../types';

export const discoveryProfileInput: DiscoveryProfileInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  profileName: 'profileName-1',
  protocol: 'SNMP',
  credentialRef: 'credentialRef-1',
  scopeCidr: 'scopeCidr-1',
  scheduleCron: 'scheduleCron-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'DISCOV-0001',
  name: 'Discovery Profile one',
  status: 'ACTIVE',
};

export const discoveryProfileFixture: DiscoveryProfile = {
  ...discoveryProfileInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const discoveryProfilePage = {
  content: [discoveryProfileFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
