import type { OpticalChannel, OpticalChannelInput } from '../types';

export const opticalChannelInput: OpticalChannelInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  wavelengthNm: 13.5,
  channelNumber: 11,
  gridType: 'FIXED',
  bitrateGbps: 13.5,
  systemId: '00000000-0000-4000-8000-000000000011',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'OPTICA-0001',
  name: 'Optical Channel one',
  status: 'ACTIVE',
};

export const opticalChannelFixture: OpticalChannel = {
  ...opticalChannelInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const opticalChannelPage = {
  content: [opticalChannelFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
