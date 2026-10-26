import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SOFTWAREVERSION_PATH } from './types';
import type { SoftwareVersion, SoftwareVersionFilter, SoftwareVersionInput, SoftwareVersionSummary, SoftwareVersionUpdateInput } from './types';
import { toSoftwareVersionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSoftwareVersions(
  filter: SoftwareVersionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SoftwareVersion>> {
  return http.get<Page<SoftwareVersion>>(SOFTWAREVERSION_PATH, { ...toSoftwareVersionQuery(filter), page, size, sort });
}

export function getSoftwareVersion(id: string): Promise<SoftwareVersion> {
  return http.get<SoftwareVersion>(`${SOFTWAREVERSION_PATH}/${id}`);
}

export function getSoftwareVersionByCode(code: string): Promise<SoftwareVersion> {
  return http.get<SoftwareVersion>(`${SOFTWAREVERSION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSoftwareVersionSummaries(status?: LifecycleStatus): Promise<SoftwareVersionSummary[]> {
  return http.get<SoftwareVersionSummary[]>(`${SOFTWAREVERSION_PATH}/summaries`, { status });
}

export function createSoftwareVersion(input: SoftwareVersionInput): Promise<SoftwareVersion> {
  return http.post<SoftwareVersion>(SOFTWAREVERSION_PATH, input);
}

export function updateSoftwareVersion(id: string, input: SoftwareVersionUpdateInput): Promise<SoftwareVersion> {
  return http.put<SoftwareVersion>(`${SOFTWAREVERSION_PATH}/${id}`, input);
}

export function changeSoftwareVersionStatus(id: string, status: LifecycleStatus): Promise<SoftwareVersion> {
  return http.patch<SoftwareVersion>(`${SOFTWAREVERSION_PATH}/${id}/status`, undefined, { status });
}

export function deleteSoftwareVersion(id: string): Promise<void> {
  return http.delete(`${SOFTWAREVERSION_PATH}/${id}`);
}

export function bulkCreateSoftwareVersions(inputs: SoftwareVersionInput[]): Promise<BulkResult<SoftwareVersion>> {
  return http.post<BulkResult<SoftwareVersion>>(`${SOFTWAREVERSION_PATH}/bulk`, inputs);
}

export function getSoftwareVersionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SOFTWAREVERSION_PATH}/stats/status`);
}

export function exportSoftwareVersionsCsv(filter: SoftwareVersionFilter): Promise<Blob> {
  return http.blob(`${SOFTWAREVERSION_PATH}/export`, toSoftwareVersionQuery(filter));
}

export function listSoftwareVersionByImageId(imageId: string, page = 0, size = 20): Promise<Page<SoftwareVersion>> {
  return http.get<Page<SoftwareVersion>>(`${SOFTWAREVERSION_PATH}/by-image-id/${imageId}`, { page, size });
}
