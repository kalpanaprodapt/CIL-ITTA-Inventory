import type { Technician, TechnicianInput } from '../types';

export const technicianInput: TechnicianInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  employeeId: 'employeeId-1',
  fullName: 'fullName-1',
  skillSet: 'skillSet-1',
  homeSiteId: '00000000-0000-4000-8000-000000000011',
  certificationLevel: 11,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'TECHNI-0001',
  name: 'Technician one',
  status: 'ACTIVE',
};

export const technicianFixture: Technician = {
  ...technicianInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const technicianPage = {
  content: [technicianFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
