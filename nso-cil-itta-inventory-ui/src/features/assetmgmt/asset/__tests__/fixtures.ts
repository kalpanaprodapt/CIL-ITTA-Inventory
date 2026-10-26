import type { Asset, AssetInput } from '../types';

export const assetInput: AssetInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  assetTag: 'assetTag-1',
  assetClass: 'assetClass-1',
  acquiredOn: '2026-02-11',
  purchasePrice: 13.5,
  currentValue: 13.5,
  custodian: 'custodian-1',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'ASSET-0001',
  name: 'Asset one',
  status: 'ACTIVE',
};

export const assetFixture: Asset = {
  ...assetInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const assetPage = {
  content: [assetFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
