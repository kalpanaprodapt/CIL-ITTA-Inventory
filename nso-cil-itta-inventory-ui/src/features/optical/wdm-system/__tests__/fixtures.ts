import type { WdmSystem, WdmSystemInput } from '../types';

export const wdmSystemInput: WdmSystemInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  systemName: 'systemName-1',
  channelCapacity: 11,
  band: 'C',
  spanCount: 11,
  vendorId: '00000000-0000-4000-8000-000000000011',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'WDMSYS-0001',
  name: 'Wdm System one',
  status: 'ACTIVE',
};

export const wdmSystemFixture: WdmSystem = {
  ...wdmSystemInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const wdmSystemPage = {
  content: [wdmSystemFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
