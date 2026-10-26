import type { VpnService, VpnServiceInput } from '../types';

export const vpnServiceInput: VpnServiceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vpnName: 'vpnName-1',
  vpnType: 'L3VPN',
  vrfId: '00000000-0000-4000-8000-000000000011',
  customerId: '00000000-0000-4000-8000-000000000011',
  topology: 'MESH',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'VPNSER-0001',
  name: 'Vpn Service one',
  status: 'ACTIVE',
};

export const vpnServiceFixture: VpnService = {
  ...vpnServiceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const vpnServicePage = {
  content: [vpnServiceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
