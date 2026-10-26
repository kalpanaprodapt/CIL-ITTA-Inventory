import type { WorkOrder, WorkOrderInput } from '../types';

export const workOrderInput: WorkOrderInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  workOrderNumber: 'workOrderNumber-1',
  workType: 'workType-1',
  assignedTechnicianId: '00000000-0000-4000-8000-000000000011',
  dueDate: '2026-02-11',
  workState: 'OPEN',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'WORKOR-0001',
  name: 'Work Order one',
  status: 'ACTIVE',
};

export const workOrderFixture: WorkOrder = {
  ...workOrderInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const workOrderPage = {
  content: [workOrderFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
