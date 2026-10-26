import type { Transceiver, TransceiverInput } from '../types';

export const transceiverInput: TransceiverInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  portId: '00000000-0000-4000-8000-000000000011',
  formFactor: 'SFP',
  wavelengthNm: 11,
  reachKm: 13.5,
  serialNumber: 'serialNumber-1',
  vendorPart: 'vendorPart-1',
  labels: 'labels-1',
  lifecycleStage: 'PLAN',
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  code: 'TRANSC-0001',
  name: 'Transceiver one',
  status: 'ACTIVE',
};

export const transceiverFixture: Transceiver = {
  ...transceiverInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const transceiverPage = {
  content: [transceiverFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
