import type { Interface, InterfaceInput } from '../types';

export const interfaceInput: InterfaceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  portId: '00000000-0000-4000-8000-000000000011',
  ifName: 'ifName-1',
  ifIndex: 11,
  macAddress: 'macAddress-1',
  mtu: 11,
  operState: 'UP',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'INTERF-0001',
  name: 'Interface one',
  status: 'ACTIVE',
};

export const interfaceFixture: Interface = {
  ...interfaceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const interfacePage = {
  content: [interfaceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
