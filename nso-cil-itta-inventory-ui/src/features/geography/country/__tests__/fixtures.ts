import type { Country, CountryInput } from '../types';

export const countryInput: CountryInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  isoCode: 'isoCode-1',
  isoNumeric: 11,
  dialCode: 'dialCode-1',
  currency: 'currency-1',
  regulatoryBody: 'regulatoryBody-1',
  timezone: 'timezone-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'COUNTR-0001',
  name: 'Country one',
  status: 'ACTIVE',
};

export const countryFixture: Country = {
  ...countryInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const countryPage = {
  content: [countryFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
