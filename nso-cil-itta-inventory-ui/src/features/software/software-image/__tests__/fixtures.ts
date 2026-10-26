import type { SoftwareImage, SoftwareImageInput } from '../types';

export const softwareImageInput: SoftwareImageInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vendorId: '00000000-0000-4000-8000-000000000011',
  imageName: 'imageName-1',
  sizeMb: 1001,
  checksum: 'checksum-1',
  platform: 'platform-1',
  storagePath: 'storagePath-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  code: 'SOFTWA-0001',
  name: 'Software Image one',
  status: 'ACTIVE',
};

export const softwareImageFixture: SoftwareImage = {
  ...softwareImageInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const softwareImagePage = {
  content: [softwareImageFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
