import type { LicenseKey, LicenseKeyInput } from '../types';

export const licenseKeyInput: LicenseKeyInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  poolId: '00000000-0000-4000-8000-000000000011',
  keyValue: 'keyValue-1',
  feature: 'feature-1',
  expiresOn: '2026-02-11',
  assignedDeviceId: '00000000-0000-4000-8000-000000000011',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  code: 'LICENS-0001',
  name: 'License Key one',
  status: 'ACTIVE',
};

export const licenseKeyFixture: LicenseKey = {
  ...licenseKeyInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const licenseKeyPage = {
  content: [licenseKeyFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
