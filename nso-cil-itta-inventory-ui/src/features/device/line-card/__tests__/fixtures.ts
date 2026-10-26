import type { LineCard, LineCardInput } from '../types';

export const lineCardInput: LineCardInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  chassisId: '00000000-0000-4000-8000-000000000011',
  slotNumber: 11,
  cardType: 'cardType-1',
  portCount: 11,
  serialNumber: 'serialNumber-1',
  firmware: 'firmware-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  code: 'LINECA-0001',
  name: 'Line Card one',
  status: 'ACTIVE',
};

export const lineCardFixture: LineCard = {
  ...lineCardInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const lineCardPage = {
  content: [lineCardFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
