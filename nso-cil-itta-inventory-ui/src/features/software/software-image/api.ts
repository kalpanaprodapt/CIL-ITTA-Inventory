import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SOFTWAREIMAGE_PATH } from './types';
import type { SoftwareImage, SoftwareImageFilter, SoftwareImageInput, SoftwareImageSummary, SoftwareImageUpdateInput } from './types';
import { toSoftwareImageQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSoftwareImages(
  filter: SoftwareImageFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SoftwareImage>> {
  return http.get<Page<SoftwareImage>>(SOFTWAREIMAGE_PATH, { ...toSoftwareImageQuery(filter), page, size, sort });
}

export function getSoftwareImage(id: string): Promise<SoftwareImage> {
  return http.get<SoftwareImage>(`${SOFTWAREIMAGE_PATH}/${id}`);
}

export function getSoftwareImageByCode(code: string): Promise<SoftwareImage> {
  return http.get<SoftwareImage>(`${SOFTWAREIMAGE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSoftwareImageSummaries(status?: LifecycleStatus): Promise<SoftwareImageSummary[]> {
  return http.get<SoftwareImageSummary[]>(`${SOFTWAREIMAGE_PATH}/summaries`, { status });
}

export function createSoftwareImage(input: SoftwareImageInput): Promise<SoftwareImage> {
  return http.post<SoftwareImage>(SOFTWAREIMAGE_PATH, input);
}

export function updateSoftwareImage(id: string, input: SoftwareImageUpdateInput): Promise<SoftwareImage> {
  return http.put<SoftwareImage>(`${SOFTWAREIMAGE_PATH}/${id}`, input);
}

export function changeSoftwareImageStatus(id: string, status: LifecycleStatus): Promise<SoftwareImage> {
  return http.patch<SoftwareImage>(`${SOFTWAREIMAGE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSoftwareImage(id: string): Promise<void> {
  return http.delete(`${SOFTWAREIMAGE_PATH}/${id}`);
}

export function bulkCreateSoftwareImages(inputs: SoftwareImageInput[]): Promise<BulkResult<SoftwareImage>> {
  return http.post<BulkResult<SoftwareImage>>(`${SOFTWAREIMAGE_PATH}/bulk`, inputs);
}

export function getSoftwareImageStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SOFTWAREIMAGE_PATH}/stats/status`);
}

export function exportSoftwareImagesCsv(filter: SoftwareImageFilter): Promise<Blob> {
  return http.blob(`${SOFTWAREIMAGE_PATH}/export`, toSoftwareImageQuery(filter));
}

export function listSoftwareImageByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<SoftwareImage>> {
  return http.get<Page<SoftwareImage>>(`${SOFTWAREIMAGE_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
