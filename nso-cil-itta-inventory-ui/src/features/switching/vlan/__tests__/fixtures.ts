import type { Vlan, VlanInput } from '../types';

export const vlanInput: VlanInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vlanId: 11,
  vlanName: 'vlanName-1',
  vlanType: 'DATA',
  poolId: '00000000-0000-4000-8000-000000000011',
  stpMode: 'stpMode-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'VLAN-0001',
  name: 'Vlan one',
  status: 'ACTIVE',
};

export const vlanFixture: Vlan = {
  ...vlanInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const vlanPage = {
  content: [vlanFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
