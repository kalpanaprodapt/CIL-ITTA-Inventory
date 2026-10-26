import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SYNCMAPPING_PATH } from './types';
import type { SyncMapping, SyncMappingFilter, SyncMappingInput, SyncMappingSummary, SyncMappingUpdateInput } from './types';
import { toSyncMappingQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSyncMappings(
  filter: SyncMappingFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SyncMapping>> {
  return http.get<Page<SyncMapping>>(SYNCMAPPING_PATH, { ...toSyncMappingQuery(filter), page, size, sort });
}

export function getSyncMapping(id: string): Promise<SyncMapping> {
  return http.get<SyncMapping>(`${SYNCMAPPING_PATH}/${id}`);
}

export function getSyncMappingByCode(code: string): Promise<SyncMapping> {
  return http.get<SyncMapping>(`${SYNCMAPPING_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSyncMappingSummaries(status?: LifecycleStatus): Promise<SyncMappingSummary[]> {
  return http.get<SyncMappingSummary[]>(`${SYNCMAPPING_PATH}/summaries`, { status });
}

export function createSyncMapping(input: SyncMappingInput): Promise<SyncMapping> {
  return http.post<SyncMapping>(SYNCMAPPING_PATH, input);
}

export function updateSyncMapping(id: string, input: SyncMappingUpdateInput): Promise<SyncMapping> {
  return http.put<SyncMapping>(`${SYNCMAPPING_PATH}/${id}`, input);
}

export function changeSyncMappingStatus(id: string, status: LifecycleStatus): Promise<SyncMapping> {
  return http.patch<SyncMapping>(`${SYNCMAPPING_PATH}/${id}/status`, undefined, { status });
}

export function deleteSyncMapping(id: string): Promise<void> {
  return http.delete(`${SYNCMAPPING_PATH}/${id}`);
}

export function bulkCreateSyncMappings(inputs: SyncMappingInput[]): Promise<BulkResult<SyncMapping>> {
  return http.post<BulkResult<SyncMapping>>(`${SYNCMAPPING_PATH}/bulk`, inputs);
}

export function getSyncMappingStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SYNCMAPPING_PATH}/stats/status`);
}

export function exportSyncMappingsCsv(filter: SyncMappingFilter): Promise<Blob> {
  return http.blob(`${SYNCMAPPING_PATH}/export`, toSyncMappingQuery(filter));
}

export function listSyncMappingByJobId(jobId: string, page = 0, size = 20): Promise<Page<SyncMapping>> {
  return http.get<Page<SyncMapping>>(`${SYNCMAPPING_PATH}/by-job-id/${jobId}`, { page, size });
}
