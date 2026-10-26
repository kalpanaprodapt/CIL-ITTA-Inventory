import type { TunnelEndpoint, TunnelEndpointInput } from '../types';

export const tunnelEndpointInput: TunnelEndpointInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  deviceId: '00000000-0000-4000-8000-000000000011',
  tunnelName: 'tunnelName-1',
  tunnelType: 'GRE',
  sourceIp: 'sourceIp-1',
  destinationIp: 'destinationIp-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'TUNNEL-0001',
  name: 'Tunnel Endpoint one',
  status: 'ACTIVE',
};

export const tunnelEndpointFixture: TunnelEndpoint = {
  ...tunnelEndpointInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const tunnelEndpointPage = {
  content: [tunnelEndpointFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
