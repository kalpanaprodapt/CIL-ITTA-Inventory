import type { IpAddress, IpAddressInput } from '../types';

export const ipAddressInput: IpAddressInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  subnetId: '00000000-0000-4000-8000-000000000011',
  address: 'address-1',
  assignedToId: '00000000-0000-4000-8000-000000000011',
  assignmentType: 'STATIC',
  dnsName: 'dnsName-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'IPADDR-0001',
  name: 'Ip Address one',
  status: 'ACTIVE',
};

export const ipAddressFixture: IpAddress = {
  ...ipAddressInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const ipAddressPage = {
  content: [ipAddressFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
