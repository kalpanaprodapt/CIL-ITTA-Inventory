import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { DISCOVERYPROFILE_PATH } from './types';
import type { DiscoveryProfile, DiscoveryProfileFilter, DiscoveryProfileInput, DiscoveryProfileSummary, DiscoveryProfileUpdateInput } from './types';
import { toDiscoveryProfileQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchDiscoveryProfiles(
  filter: DiscoveryProfileFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<DiscoveryProfile>> {
  return http.get<Page<DiscoveryProfile>>(DISCOVERYPROFILE_PATH, { ...toDiscoveryProfileQuery(filter), page, size, sort });
}

export function getDiscoveryProfile(id: string): Promise<DiscoveryProfile> {
  return http.get<DiscoveryProfile>(`${DISCOVERYPROFILE_PATH}/${id}`);
}

export function getDiscoveryProfileByCode(code: string): Promise<DiscoveryProfile> {
  return http.get<DiscoveryProfile>(`${DISCOVERYPROFILE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listDiscoveryProfileSummaries(status?: LifecycleStatus): Promise<DiscoveryProfileSummary[]> {
  return http.get<DiscoveryProfileSummary[]>(`${DISCOVERYPROFILE_PATH}/summaries`, { status });
}

export function createDiscoveryProfile(input: DiscoveryProfileInput): Promise<DiscoveryProfile> {
  return http.post<DiscoveryProfile>(DISCOVERYPROFILE_PATH, input);
}

export function updateDiscoveryProfile(id: string, input: DiscoveryProfileUpdateInput): Promise<DiscoveryProfile> {
  return http.put<DiscoveryProfile>(`${DISCOVERYPROFILE_PATH}/${id}`, input);
}

export function changeDiscoveryProfileStatus(id: string, status: LifecycleStatus): Promise<DiscoveryProfile> {
  return http.patch<DiscoveryProfile>(`${DISCOVERYPROFILE_PATH}/${id}/status`, undefined, { status });
}

export function deleteDiscoveryProfile(id: string): Promise<void> {
  return http.delete(`${DISCOVERYPROFILE_PATH}/${id}`);
}

export function bulkCreateDiscoveryProfiles(inputs: DiscoveryProfileInput[]): Promise<BulkResult<DiscoveryProfile>> {
  return http.post<BulkResult<DiscoveryProfile>>(`${DISCOVERYPROFILE_PATH}/bulk`, inputs);
}

export function getDiscoveryProfileStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${DISCOVERYPROFILE_PATH}/stats/status`);
}

export function exportDiscoveryProfilesCsv(filter: DiscoveryProfileFilter): Promise<Blob> {
  return http.blob(`${DISCOVERYPROFILE_PATH}/export`, toDiscoveryProfileQuery(filter));
}
