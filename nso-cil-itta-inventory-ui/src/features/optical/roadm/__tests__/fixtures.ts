import type { Roadm, RoadmInput } from '../types';

export const roadmInput: RoadmInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  systemId: '00000000-0000-4000-8000-000000000011',
  degrees: 11,
  colorless: true,
  directionless: true,
  contentionless: true,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'ROADM-0001',
  name: 'Roadm one',
  status: 'ACTIVE',
};

export const roadmFixture: Roadm = {
  ...roadmInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const roadmPage = {
  content: [roadmFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
