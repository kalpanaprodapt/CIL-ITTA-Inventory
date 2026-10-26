import type { Antenna, AntennaInput } from '../types';

export const antennaInput: AntennaInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  sectorId: '00000000-0000-4000-8000-000000000011',
  model: 'model-1',
  heightM: 13.5,
  azimuthDeg: 13.5,
  tiltDeg: 13.5,
  gainDbi: 13.5,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'ANTENN-0001',
  name: 'Antenna one',
  status: 'ACTIVE',
};

export const antennaFixture: Antenna = {
  ...antennaInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const antennaPage = {
  content: [antennaFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
