import type { DiscoveredAsset, DiscoveredAssetInput } from '../types';

export const discoveredAssetInput: DiscoveredAssetInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  jobId: '00000000-0000-4000-8000-000000000011',
  assetType: 'assetType-1',
  fingerprint: 'fingerprint-1',
  rawPayload: 'rawPayload-1',
  matchedAssetId: '00000000-0000-4000-8000-000000000011',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  code: 'DISCOV-0001',
  name: 'Discovered Asset one',
  status: 'ACTIVE',
};

export const discoveredAssetFixture: DiscoveredAsset = {
  ...discoveredAssetInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const discoveredAssetPage = {
  content: [discoveredAssetFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
