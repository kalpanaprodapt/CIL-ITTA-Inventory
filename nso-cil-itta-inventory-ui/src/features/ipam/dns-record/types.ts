import type { LifecycleStatus, SortState } from '@/shared/types';

export const DNS_RECORD_RECORD_TYPE_VALUES = ['A', 'AAAA', 'CNAME', 'PTR', 'MX', 'TXT'] as const;
export type DnsRecordRecordType = (typeof DNS_RECORD_RECORD_TYPE_VALUES)[number];
export const DNS_RECORD_RECORD_TYPE_LABELS: Record<DnsRecordRecordType, string> = {
  A: 'A',
  AAAA: 'Aaaa',
  CNAME: 'Cname',
  PTR: 'Ptr',
  MX: 'Mx',
  TXT: 'Txt',
};
export const DNS_RECORD_RECORD_TYPE_OPTIONS = DNS_RECORD_RECORD_TYPE_VALUES.map((value) => ({ value, label: DNS_RECORD_RECORD_TYPE_LABELS[value] }));

export const DNS_RECORD_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type DnsRecordCriticality = (typeof DNS_RECORD_CRITICALITY_VALUES)[number];
export const DNS_RECORD_CRITICALITY_LABELS: Record<DnsRecordCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const DNS_RECORD_CRITICALITY_OPTIONS = DNS_RECORD_CRITICALITY_VALUES.map((value) => ({ value, label: DNS_RECORD_CRITICALITY_LABELS[value] }));

export interface DnsRecord {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  zoneName: string;
  recordType?: DnsRecordRecordType;
  recordValue: string;
  ttl?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: DnsRecordCriticality;
  costCenter?: string;
}

export interface DnsRecordInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  zoneName: string;
  recordType?: DnsRecordRecordType;
  recordValue: string;
  ttl?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: DnsRecordCriticality;
  costCenter?: string;
}

export type DnsRecordFormValues = Partial<DnsRecordInput>;

export interface DnsRecordUpdateInput extends Partial<DnsRecordInput> {
  expectedVersion?: number;
}

export interface DnsRecordFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  zoneName?: string;
  recordType?: DnsRecordRecordType[];
  recordValue?: string;
  ttlMin?: number;
  ttlMax?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: DnsRecordCriticality[];
  costCenter?: string;
}

export type DnsRecordSort = SortState<keyof DnsRecord & string>;

export interface DnsRecordSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const DNSRECORD_PATH = '/api/v1/dns-records';
export const DNSRECORD_TITLE = 'Dns Record';
export const DNSRECORD_TITLE_PLURAL = 'Dns Records';

export const DNSRECORD_DEFAULTS: DnsRecordFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  zoneName: '',
  recordValue: '',
};
