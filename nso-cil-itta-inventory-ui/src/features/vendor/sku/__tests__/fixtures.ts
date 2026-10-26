import type { Sku, SkuInput } from '../types';

export const skuInput: SkuInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  modelId: '00000000-0000-4000-8000-000000000011',
  skuCode: 'skuCode-1',
  listPrice: 13.5,
  currency: 'currency-1',
  leadTimeDays: 11,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'SKU-0001',
  name: 'Sku one',
  status: 'ACTIVE',
};

export const skuFixture: Sku = {
  ...skuInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const skuPage = {
  content: [skuFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
