import type { DepreciationSchedule, DepreciationScheduleInput } from '../types';

export const depreciationScheduleInput: DepreciationScheduleInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  assetId: '00000000-0000-4000-8000-000000000011',
  method: 'STRAIGHT_LINE',
  usefulLifeMonths: 11,
  salvageValue: 13.5,
  startDate: '2026-02-11',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'DEPREC-0001',
  name: 'Depreciation Schedule one',
  status: 'ACTIVE',
};

export const depreciationScheduleFixture: DepreciationSchedule = {
  ...depreciationScheduleInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const depreciationSchedulePage = {
  content: [depreciationScheduleFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
