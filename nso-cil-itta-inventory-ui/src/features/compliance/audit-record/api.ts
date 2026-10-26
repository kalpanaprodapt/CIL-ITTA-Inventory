import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { AUDITRECORD_PATH } from './types';
import type { AuditRecord, AuditRecordFilter, AuditRecordInput, AuditRecordSummary, AuditRecordUpdateInput } from './types';
import { toAuditRecordQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAuditRecords(
  filter: AuditRecordFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<AuditRecord>> {
  return http.get<Page<AuditRecord>>(AUDITRECORD_PATH, { ...toAuditRecordQuery(filter), page, size, sort });
}

export function getAuditRecord(id: string): Promise<AuditRecord> {
  return http.get<AuditRecord>(`${AUDITRECORD_PATH}/${id}`);
}

export function getAuditRecordByCode(code: string): Promise<AuditRecord> {
  return http.get<AuditRecord>(`${AUDITRECORD_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAuditRecordSummaries(status?: LifecycleStatus): Promise<AuditRecordSummary[]> {
  return http.get<AuditRecordSummary[]>(`${AUDITRECORD_PATH}/summaries`, { status });
}

export function createAuditRecord(input: AuditRecordInput): Promise<AuditRecord> {
  return http.post<AuditRecord>(AUDITRECORD_PATH, input);
}

export function updateAuditRecord(id: string, input: AuditRecordUpdateInput): Promise<AuditRecord> {
  return http.put<AuditRecord>(`${AUDITRECORD_PATH}/${id}`, input);
}

export function changeAuditRecordStatus(id: string, status: LifecycleStatus): Promise<AuditRecord> {
  return http.patch<AuditRecord>(`${AUDITRECORD_PATH}/${id}/status`, undefined, { status });
}

export function deleteAuditRecord(id: string): Promise<void> {
  return http.delete(`${AUDITRECORD_PATH}/${id}`);
}

export function bulkCreateAuditRecords(inputs: AuditRecordInput[]): Promise<BulkResult<AuditRecord>> {
  return http.post<BulkResult<AuditRecord>>(`${AUDITRECORD_PATH}/bulk`, inputs);
}

export function getAuditRecordStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${AUDITRECORD_PATH}/stats/status`);
}

export function exportAuditRecordsCsv(filter: AuditRecordFilter): Promise<Blob> {
  return http.blob(`${AUDITRECORD_PATH}/export`, toAuditRecordQuery(filter));
}
