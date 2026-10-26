import type { Hypervisor, HypervisorInput } from '../types';

export const hypervisorInput: HypervisorInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  hostDeviceId: '00000000-0000-4000-8000-000000000011',
  hypervisorType: 'KVM',
  hvVersion: 'hvVersion-1',
  vcpuCapacity: 11,
  memoryGbCapacity: 11,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'HYPERV-0001',
  name: 'Hypervisor one',
  status: 'ACTIVE',
};

export const hypervisorFixture: Hypervisor = {
  ...hypervisorInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const hypervisorPage = {
  content: [hypervisorFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
