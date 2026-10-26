import type { Site, SiteInput } from '../types';

export const siteInput: SiteInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  cityId: '00000000-0000-4000-8000-000000000011',
  siteType: 'CO',
  address: 'address-1',
  latitude: 13.5,
  longitude: 13.5,
  powerKw: 13.5,
  accessHours: 'accessHours-1',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  code: 'SITE-0001',
  name: 'Site one',
  status: 'ACTIVE',
};

export const siteFixture: Site = {
  ...siteInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const sitePage = {
  content: [siteFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
