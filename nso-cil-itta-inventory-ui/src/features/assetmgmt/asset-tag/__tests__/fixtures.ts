import type { AssetTag, AssetTagInput } from '../types';

export const assetTagInput: AssetTagInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  assetId: '00000000-0000-4000-8000-000000000011',
  tagValue: 'tagValue-1',
  tagType: 'BARCODE',
  printedOn: '2026-02-11',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'ASSETT-0001',
  name: 'Asset Tag one',
  status: 'ACTIVE',
};

export const assetTagFixture: AssetTag = {
  ...assetTagInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const assetTagPage = {
  content: [assetTagFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
