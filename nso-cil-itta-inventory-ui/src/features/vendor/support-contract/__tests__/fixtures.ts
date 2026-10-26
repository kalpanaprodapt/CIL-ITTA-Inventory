import type { SupportContract, SupportContractInput } from '../types';

export const supportContractInput: SupportContractInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  vendorId: '00000000-0000-4000-8000-000000000011',
  contractNumber: 'contractNumber-1',
  startDate: '2026-02-11',
  endDate: '2026-02-11',
  coverageLevel: 'BRONZE',
  annualCost: 13.5,
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'SUPPOR-0001',
  name: 'Support Contract one',
  status: 'ACTIVE',
};

export const supportContractFixture: SupportContract = {
  ...supportContractInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const supportContractPage = {
  content: [supportContractFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
