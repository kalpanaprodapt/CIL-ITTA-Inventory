import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SLAPROFILE_PATH } from './types';
import type { SlaProfile, SlaProfileFilter, SlaProfileInput, SlaProfileSummary, SlaProfileUpdateInput } from './types';
import { toSlaProfileQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSlaProfiles(
  filter: SlaProfileFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SlaProfile>> {
  return http.get<Page<SlaProfile>>(SLAPROFILE_PATH, { ...toSlaProfileQuery(filter), page, size, sort });
}

export function getSlaProfile(id: string): Promise<SlaProfile> {
  return http.get<SlaProfile>(`${SLAPROFILE_PATH}/${id}`);
}

export function getSlaProfileByCode(code: string): Promise<SlaProfile> {
  return http.get<SlaProfile>(`${SLAPROFILE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSlaProfileSummaries(status?: LifecycleStatus): Promise<SlaProfileSummary[]> {
  return http.get<SlaProfileSummary[]>(`${SLAPROFILE_PATH}/summaries`, { status });
}

export function createSlaProfile(input: SlaProfileInput): Promise<SlaProfile> {
  return http.post<SlaProfile>(SLAPROFILE_PATH, input);
}

export function updateSlaProfile(id: string, input: SlaProfileUpdateInput): Promise<SlaProfile> {
  return http.put<SlaProfile>(`${SLAPROFILE_PATH}/${id}`, input);
}

export function changeSlaProfileStatus(id: string, status: LifecycleStatus): Promise<SlaProfile> {
  return http.patch<SlaProfile>(`${SLAPROFILE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSlaProfile(id: string): Promise<void> {
  return http.delete(`${SLAPROFILE_PATH}/${id}`);
}

export function bulkCreateSlaProfiles(inputs: SlaProfileInput[]): Promise<BulkResult<SlaProfile>> {
  return http.post<BulkResult<SlaProfile>>(`${SLAPROFILE_PATH}/bulk`, inputs);
}

export function getSlaProfileStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SLAPROFILE_PATH}/stats/status`);
}

export function exportSlaProfilesCsv(filter: SlaProfileFilter): Promise<Blob> {
  return http.blob(`${SLAPROFILE_PATH}/export`, toSlaProfileQuery(filter));
}
