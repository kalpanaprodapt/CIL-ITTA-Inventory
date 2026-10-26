import type { SlaProfile, SlaProfileInput } from '../types';

export const slaProfileInput: SlaProfileInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  profileName: 'profileName-1',
  availabilityPct: 13.5,
  mttrMinutes: 11,
  latencyMs: 13.5,
  jitterMs: 13.5,
  creditPolicy: 'creditPolicy-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  code: 'SLAPRO-0001',
  name: 'Sla Profile one',
  status: 'ACTIVE',
};

export const slaProfileFixture: SlaProfile = {
  ...slaProfileInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const slaProfilePage = {
  content: [slaProfileFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
