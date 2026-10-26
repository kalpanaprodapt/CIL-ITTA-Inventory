import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { DISCOVERYJOB_PATH } from './types';
import type { DiscoveryJob, DiscoveryJobFilter, DiscoveryJobInput, DiscoveryJobSummary, DiscoveryJobUpdateInput } from './types';
import { toDiscoveryJobQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchDiscoveryJobs(
  filter: DiscoveryJobFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<DiscoveryJob>> {
  return http.get<Page<DiscoveryJob>>(DISCOVERYJOB_PATH, { ...toDiscoveryJobQuery(filter), page, size, sort });
}

export function getDiscoveryJob(id: string): Promise<DiscoveryJob> {
  return http.get<DiscoveryJob>(`${DISCOVERYJOB_PATH}/${id}`);
}

export function getDiscoveryJobByCode(code: string): Promise<DiscoveryJob> {
  return http.get<DiscoveryJob>(`${DISCOVERYJOB_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listDiscoveryJobSummaries(status?: LifecycleStatus): Promise<DiscoveryJobSummary[]> {
  return http.get<DiscoveryJobSummary[]>(`${DISCOVERYJOB_PATH}/summaries`, { status });
}

export function createDiscoveryJob(input: DiscoveryJobInput): Promise<DiscoveryJob> {
  return http.post<DiscoveryJob>(DISCOVERYJOB_PATH, input);
}

export function updateDiscoveryJob(id: string, input: DiscoveryJobUpdateInput): Promise<DiscoveryJob> {
  return http.put<DiscoveryJob>(`${DISCOVERYJOB_PATH}/${id}`, input);
}

export function changeDiscoveryJobStatus(id: string, status: LifecycleStatus): Promise<DiscoveryJob> {
  return http.patch<DiscoveryJob>(`${DISCOVERYJOB_PATH}/${id}/status`, undefined, { status });
}

export function deleteDiscoveryJob(id: string): Promise<void> {
  return http.delete(`${DISCOVERYJOB_PATH}/${id}`);
}

export function bulkCreateDiscoveryJobs(inputs: DiscoveryJobInput[]): Promise<BulkResult<DiscoveryJob>> {
  return http.post<BulkResult<DiscoveryJob>>(`${DISCOVERYJOB_PATH}/bulk`, inputs);
}

export function getDiscoveryJobStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${DISCOVERYJOB_PATH}/stats/status`);
}

export function exportDiscoveryJobsCsv(filter: DiscoveryJobFilter): Promise<Blob> {
  return http.blob(`${DISCOVERYJOB_PATH}/export`, toDiscoveryJobQuery(filter));
}

export function listDiscoveryJobByProfileId(profileId: string, page = 0, size = 20): Promise<Page<DiscoveryJob>> {
  return http.get<Page<DiscoveryJob>>(`${DISCOVERYJOB_PATH}/by-profile-id/${profileId}`, { page, size });
}
