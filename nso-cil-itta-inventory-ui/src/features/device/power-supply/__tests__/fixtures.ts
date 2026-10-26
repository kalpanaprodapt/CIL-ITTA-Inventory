import type { PowerSupply, PowerSupplyInput } from '../types';

export const powerSupplyInput: PowerSupplyInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  chassisId: '00000000-0000-4000-8000-000000000011',
  slotNumber: 11,
  wattage: 11,
  feedId: '00000000-0000-4000-8000-000000000011',
  serialNumber: 'serialNumber-1',
  efficiency: 13.5,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'POWERS-0001',
  name: 'Power Supply one',
  status: 'ACTIVE',
};

export const powerSupplyFixture: PowerSupply = {
  ...powerSupplyInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const powerSupplyPage = {
  content: [powerSupplyFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
