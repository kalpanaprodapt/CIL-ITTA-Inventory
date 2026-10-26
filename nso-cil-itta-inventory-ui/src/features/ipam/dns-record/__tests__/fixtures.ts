import type { DnsRecord, DnsRecordInput } from '../types';

export const dnsRecordInput: DnsRecordInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  zoneName: 'zoneName-1',
  recordType: 'A',
  recordValue: 'recordValue-1',
  ttl: 11,
  sourceSystem: 'sourceSystem-1',
  region: 'region-1',
  owner: 'owner-1',
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  code: 'DNSREC-0001',
  name: 'Dns Record one',
  status: 'ACTIVE',
};

export const dnsRecordFixture: DnsRecord = {
  ...dnsRecordInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const dnsRecordPage = {
  content: [dnsRecordFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
