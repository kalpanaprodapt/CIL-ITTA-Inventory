import type { VirtualMachine, VirtualMachineInput } from '../types';

export const virtualMachineInput: VirtualMachineInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  hypervisorId: '00000000-0000-4000-8000-000000000011',
  vmName: 'vmName-1',
  vcpu: 11,
  memoryGb: 11,
  diskGb: 11,
  powerState: 'ON',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'VIRTUA-0001',
  name: 'Virtual Machine one',
  status: 'ACTIVE',
};

export const virtualMachineFixture: VirtualMachine = {
  ...virtualMachineInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const virtualMachinePage = {
  content: [virtualMachineFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
