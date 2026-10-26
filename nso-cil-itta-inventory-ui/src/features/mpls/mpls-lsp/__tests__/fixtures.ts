import type { MplsLsp, MplsLspInput } from '../types';

export const mplsLspInput: MplsLspInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  lspName: 'lspName-1',
  headEndId: '00000000-0000-4000-8000-000000000011',
  tailEndId: '00000000-0000-4000-8000-000000000011',
  bandwidthMbps: 1001,
  setupPriority: 11,
  pathType: 'PRIMARY',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'MPLSLS-0001',
  name: 'Mpls Lsp one',
  status: 'ACTIVE',
};

export const mplsLspFixture: MplsLsp = {
  ...mplsLspInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const mplsLspPage = {
  content: [mplsLspFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
