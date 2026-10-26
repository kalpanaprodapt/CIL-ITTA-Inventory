import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { LICENSEPOOL_PATH } from './types';
import type { LicensePool, LicensePoolFilter, LicensePoolInput, LicensePoolSummary, LicensePoolUpdateInput } from './types';
import { toLicensePoolQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchLicensePools(
  filter: LicensePoolFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<LicensePool>> {
  return http.get<Page<LicensePool>>(LICENSEPOOL_PATH, { ...toLicensePoolQuery(filter), page, size, sort });
}

export function getLicensePool(id: string): Promise<LicensePool> {
  return http.get<LicensePool>(`${LICENSEPOOL_PATH}/${id}`);
}

export function getLicensePoolByCode(code: string): Promise<LicensePool> {
  return http.get<LicensePool>(`${LICENSEPOOL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listLicensePoolSummaries(status?: LifecycleStatus): Promise<LicensePoolSummary[]> {
  return http.get<LicensePoolSummary[]>(`${LICENSEPOOL_PATH}/summaries`, { status });
}

export function createLicensePool(input: LicensePoolInput): Promise<LicensePool> {
  return http.post<LicensePool>(LICENSEPOOL_PATH, input);
}

export function updateLicensePool(id: string, input: LicensePoolUpdateInput): Promise<LicensePool> {
  return http.put<LicensePool>(`${LICENSEPOOL_PATH}/${id}`, input);
}

export function changeLicensePoolStatus(id: string, status: LifecycleStatus): Promise<LicensePool> {
  return http.patch<LicensePool>(`${LICENSEPOOL_PATH}/${id}/status`, undefined, { status });
}

export function deleteLicensePool(id: string): Promise<void> {
  return http.delete(`${LICENSEPOOL_PATH}/${id}`);
}

export function bulkCreateLicensePools(inputs: LicensePoolInput[]): Promise<BulkResult<LicensePool>> {
  return http.post<BulkResult<LicensePool>>(`${LICENSEPOOL_PATH}/bulk`, inputs);
}

export function getLicensePoolStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${LICENSEPOOL_PATH}/stats/status`);
}

export function exportLicensePoolsCsv(filter: LicensePoolFilter): Promise<Blob> {
  return http.blob(`${LICENSEPOOL_PATH}/export`, toLicensePoolQuery(filter));
}

export function listLicensePoolByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<LicensePool>> {
  return http.get<Page<LicensePool>>(`${LICENSEPOOL_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
