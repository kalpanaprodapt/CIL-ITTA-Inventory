import type { CustomerContact, CustomerContactInput } from '../types';

export const customerContactInput: CustomerContactInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  customerId: '00000000-0000-4000-8000-000000000011',
  fullName: 'fullName-1',
  email: 'email-1',
  phone: 'phone-1',
  contactRole: 'TECHNICAL',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'CUSTOM-0001',
  name: 'Customer Contact one',
  status: 'ACTIVE',
};

export const customerContactFixture: CustomerContact = {
  ...customerContactInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const customerContactPage = {
  content: [customerContactFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
