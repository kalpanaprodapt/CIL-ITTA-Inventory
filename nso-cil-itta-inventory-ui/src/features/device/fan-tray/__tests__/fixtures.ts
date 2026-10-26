import type { FanTray, FanTrayInput } from '../types';

export const fanTrayInput: FanTrayInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  chassisId: '00000000-0000-4000-8000-000000000011',
  slotNumber: 11,
  fanCount: 11,
  airflow: 'FRONT_TO_BACK',
  serialNumber: 'serialNumber-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'FANTRA-0001',
  name: 'Fan Tray one',
  status: 'ACTIVE',
};

export const fanTrayFixture: FanTray = {
  ...fanTrayInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const fanTrayPage = {
  content: [fanTrayFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
