import type { IntegrationEndpoint, IntegrationEndpointInput } from '../types';

export const integrationEndpointInput: IntegrationEndpointInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  endpointName: 'endpointName-1',
  targetSystem: 'targetSystem-1',
  baseUrl: 'baseUrl-1',
  authType: 'BASIC',
  timeoutMs: 11,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'INTEGR-0001',
  name: 'Integration Endpoint one',
  status: 'ACTIVE',
};

export const integrationEndpointFixture: IntegrationEndpoint = {
  ...integrationEndpointInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const integrationEndpointPage = {
  content: [integrationEndpointFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
