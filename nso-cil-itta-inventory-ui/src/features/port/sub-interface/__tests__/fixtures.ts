import type { SubInterface, SubInterfaceInput } from '../types';

export const subInterfaceInput: SubInterfaceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  interfaceId: '00000000-0000-4000-8000-000000000011',
  vlanTag: 11,
  encapsulation: 'encapsulation-1',
  ipv4Address: 'ipv4Address-1',
  ipv6Address: 'ipv6Address-1',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  code: 'SUBINT-0001',
  name: 'Sub Interface one',
  status: 'ACTIVE',
};

export const subInterfaceFixture: SubInterface = {
  ...subInterfaceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const subInterfacePage = {
  content: [subInterfaceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
