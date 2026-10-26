import type { SoftwareVersion, SoftwareVersionInput } from '../types';

export const softwareVersionInput: SoftwareVersionInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  imageId: '00000000-0000-4000-8000-000000000011',
  versionString: 'versionString-1',
  releaseDate: '2026-02-11',
  endOfSupport: '2026-02-11',
  releaseTrain: 'GA',
  cveCount: 11,
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'SOFTWA-0001',
  name: 'Software Version one',
  status: 'ACTIVE',
};

export const softwareVersionFixture: SoftwareVersion = {
  ...softwareVersionInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const softwareVersionPage = {
  content: [softwareVersionFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
