import type { EventSource, EventSourceInput } from '../types';

export const eventSourceInput: EventSourceInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  sourceName: 'sourceName-1',
  protocol: 'SNMP_TRAP',
  endpoint: 'endpoint-1',
  heartbeatSeconds: 11,
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'EVENTS-0001',
  name: 'Event Source one',
  status: 'ACTIVE',
};

export const eventSourceFixture: EventSource = {
  ...eventSourceInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const eventSourcePage = {
  content: [eventSourceFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
