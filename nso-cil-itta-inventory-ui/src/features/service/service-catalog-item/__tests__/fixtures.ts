import type { ServiceCatalogItem, ServiceCatalogItemInput } from '../types';

export const serviceCatalogItemInput: ServiceCatalogItemInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  itemName: 'itemName-1',
  family: 'CONNECTIVITY',
  basePrice: 13.5,
  billingUnit: 'billingUnit-1',
  orderable: true,
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'SERVIC-0001',
  name: 'Service Catalog Item one',
  status: 'ACTIVE',
};

export const serviceCatalogItemFixture: ServiceCatalogItem = {
  ...serviceCatalogItemInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const serviceCatalogItemPage = {
  content: [serviceCatalogItemFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
