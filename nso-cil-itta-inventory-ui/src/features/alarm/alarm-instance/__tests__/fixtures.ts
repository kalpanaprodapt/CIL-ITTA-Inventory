import type { AlarmInstance, AlarmInstanceInput } from '../types';

export const alarmInstanceInput: AlarmInstanceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  definitionId: '00000000-0000-4000-8000-000000000011',
  sourceRef: 'sourceRef-1',
  raisedAt: '2026-02-11T10:15:30Z',
  clearedAt: '2026-02-11T10:15:30Z',
  ackedBy: 'ackedBy-1',
  alarmState: 'ACTIVE',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'ALARMI-0001',
  name: 'Alarm Instance one',
  status: 'ACTIVE',
};

export const alarmInstanceFixture: AlarmInstance = {
  ...alarmInstanceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const alarmInstancePage = {
  content: [alarmInstanceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
