import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SYNCJOB_PATH } from './types';
import type { SyncJob, SyncJobFilter, SyncJobInput, SyncJobSummary, SyncJobUpdateInput } from './types';
import { toSyncJobQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSyncJobs(
  filter: SyncJobFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SyncJob>> {
  return http.get<Page<SyncJob>>(SYNCJOB_PATH, { ...toSyncJobQuery(filter), page, size, sort });
}

export function getSyncJob(id: string): Promise<SyncJob> {
  return http.get<SyncJob>(`${SYNCJOB_PATH}/${id}`);
}

export function getSyncJobByCode(code: string): Promise<SyncJob> {
  return http.get<SyncJob>(`${SYNCJOB_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSyncJobSummaries(status?: LifecycleStatus): Promise<SyncJobSummary[]> {
  return http.get<SyncJobSummary[]>(`${SYNCJOB_PATH}/summaries`, { status });
}

export function createSyncJob(input: SyncJobInput): Promise<SyncJob> {
  return http.post<SyncJob>(SYNCJOB_PATH, input);
}

export function updateSyncJob(id: string, input: SyncJobUpdateInput): Promise<SyncJob> {
  return http.put<SyncJob>(`${SYNCJOB_PATH}/${id}`, input);
}

export function changeSyncJobStatus(id: string, status: LifecycleStatus): Promise<SyncJob> {
  return http.patch<SyncJob>(`${SYNCJOB_PATH}/${id}/status`, undefined, { status });
}

export function deleteSyncJob(id: string): Promise<void> {
  return http.delete(`${SYNCJOB_PATH}/${id}`);
}

export function bulkCreateSyncJobs(inputs: SyncJobInput[]): Promise<BulkResult<SyncJob>> {
  return http.post<BulkResult<SyncJob>>(`${SYNCJOB_PATH}/bulk`, inputs);
}

export function getSyncJobStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SYNCJOB_PATH}/stats/status`);
}

export function exportSyncJobsCsv(filter: SyncJobFilter): Promise<Blob> {
  return http.blob(`${SYNCJOB_PATH}/export`, toSyncJobQuery(filter));
}

export function listSyncJobByEndpointId(endpointId: string, page = 0, size = 20): Promise<Page<SyncJob>> {
  return http.get<Page<SyncJob>>(`${SYNCJOB_PATH}/by-endpoint-id/${endpointId}`, { page, size });
}
