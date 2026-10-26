import type { OtnLink, OtnLinkInput } from '../types';

export const otnLinkInput: OtnLinkInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  aNodeId: '00000000-0000-4000-8000-000000000011',
  zNodeId: '00000000-0000-4000-8000-000000000011',
  odu: 'ODU0',
  latencyUs: 13.5,
  protection: 'protection-1',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'OTNLIN-0001',
  name: 'Otn Link one',
  status: 'ACTIVE',
};

export const otnLinkFixture: OtnLink = {
  ...otnLinkInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const otnLinkPage = {
  content: [otnLinkFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
