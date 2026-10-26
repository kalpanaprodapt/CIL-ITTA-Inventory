import type { IpSubnet, IpSubnetInput } from '../types';

export const ipSubnetInput: IpSubnetInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  cidr: 'cidr-1',
  ipVersion: 'V4',
  vrfId: '00000000-0000-4000-8000-000000000011',
  gateway: 'gateway-1',
  vlanId: '00000000-0000-4000-8000-000000000011',
  utilizationPct: 13.5,
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'IPSUBN-0001',
  name: 'Ip Subnet one',
  status: 'ACTIVE',
};

export const ipSubnetFixture: IpSubnet = {
  ...ipSubnetInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const ipSubnetPage = {
  content: [ipSubnetFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
