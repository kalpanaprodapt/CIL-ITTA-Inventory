import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { DNSRECORD_PATH } from './types';
import type { DnsRecord, DnsRecordFilter, DnsRecordInput, DnsRecordSummary, DnsRecordUpdateInput } from './types';
import { toDnsRecordQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchDnsRecords(
  filter: DnsRecordFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<DnsRecord>> {
  return http.get<Page<DnsRecord>>(DNSRECORD_PATH, { ...toDnsRecordQuery(filter), page, size, sort });
}

export function getDnsRecord(id: string): Promise<DnsRecord> {
  return http.get<DnsRecord>(`${DNSRECORD_PATH}/${id}`);
}

export function getDnsRecordByCode(code: string): Promise<DnsRecord> {
  return http.get<DnsRecord>(`${DNSRECORD_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listDnsRecordSummaries(status?: LifecycleStatus): Promise<DnsRecordSummary[]> {
  return http.get<DnsRecordSummary[]>(`${DNSRECORD_PATH}/summaries`, { status });
}

export function createDnsRecord(input: DnsRecordInput): Promise<DnsRecord> {
  return http.post<DnsRecord>(DNSRECORD_PATH, input);
}

export function updateDnsRecord(id: string, input: DnsRecordUpdateInput): Promise<DnsRecord> {
  return http.put<DnsRecord>(`${DNSRECORD_PATH}/${id}`, input);
}

export function changeDnsRecordStatus(id: string, status: LifecycleStatus): Promise<DnsRecord> {
  return http.patch<DnsRecord>(`${DNSRECORD_PATH}/${id}/status`, undefined, { status });
}

export function deleteDnsRecord(id: string): Promise<void> {
  return http.delete(`${DNSRECORD_PATH}/${id}`);
}

export function bulkCreateDnsRecords(inputs: DnsRecordInput[]): Promise<BulkResult<DnsRecord>> {
  return http.post<BulkResult<DnsRecord>>(`${DNSRECORD_PATH}/bulk`, inputs);
}

export function getDnsRecordStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${DNSRECORD_PATH}/stats/status`);
}

export function exportDnsRecordsCsv(filter: DnsRecordFilter): Promise<Blob> {
  return http.blob(`${DNSRECORD_PATH}/export`, toDnsRecordQuery(filter));
}
