import type { ServiceDependency, ServiceDependencyInput } from '../types';

export const serviceDependencyInput: ServiceDependencyInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  serviceId: '00000000-0000-4000-8000-000000000011',
  dependsOnId: '00000000-0000-4000-8000-000000000011',
  dependencyType: 'HARD',
  impactScore: 13.5,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'SERVIC-0001',
  name: 'Service Dependency one',
  status: 'ACTIVE',
};

export const serviceDependencyFixture: ServiceDependency = {
  ...serviceDependencyInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const serviceDependencyPage = {
  content: [serviceDependencyFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
