import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { LICENSEKEY_PATH } from './types';
import type { LicenseKey, LicenseKeyFilter, LicenseKeyInput, LicenseKeySummary, LicenseKeyUpdateInput } from './types';
import { toLicenseKeyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchLicenseKeys(
  filter: LicenseKeyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<LicenseKey>> {
  return http.get<Page<LicenseKey>>(LICENSEKEY_PATH, { ...toLicenseKeyQuery(filter), page, size, sort });
}

export function getLicenseKey(id: string): Promise<LicenseKey> {
  return http.get<LicenseKey>(`${LICENSEKEY_PATH}/${id}`);
}

export function getLicenseKeyByCode(code: string): Promise<LicenseKey> {
  return http.get<LicenseKey>(`${LICENSEKEY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listLicenseKeySummaries(status?: LifecycleStatus): Promise<LicenseKeySummary[]> {
  return http.get<LicenseKeySummary[]>(`${LICENSEKEY_PATH}/summaries`, { status });
}

export function createLicenseKey(input: LicenseKeyInput): Promise<LicenseKey> {
  return http.post<LicenseKey>(LICENSEKEY_PATH, input);
}

export function updateLicenseKey(id: string, input: LicenseKeyUpdateInput): Promise<LicenseKey> {
  return http.put<LicenseKey>(`${LICENSEKEY_PATH}/${id}`, input);
}

export function changeLicenseKeyStatus(id: string, status: LifecycleStatus): Promise<LicenseKey> {
  return http.patch<LicenseKey>(`${LICENSEKEY_PATH}/${id}/status`, undefined, { status });
}

export function deleteLicenseKey(id: string): Promise<void> {
  return http.delete(`${LICENSEKEY_PATH}/${id}`);
}

export function bulkCreateLicenseKeys(inputs: LicenseKeyInput[]): Promise<BulkResult<LicenseKey>> {
  return http.post<BulkResult<LicenseKey>>(`${LICENSEKEY_PATH}/bulk`, inputs);
}

export function getLicenseKeyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${LICENSEKEY_PATH}/stats/status`);
}

export function exportLicenseKeysCsv(filter: LicenseKeyFilter): Promise<Blob> {
  return http.blob(`${LICENSEKEY_PATH}/export`, toLicenseKeyQuery(filter));
}

export function listLicenseKeyByPoolId(poolId: string, page = 0, size = 20): Promise<Page<LicenseKey>> {
  return http.get<Page<LicenseKey>>(`${LICENSEKEY_PATH}/by-pool-id/${poolId}`, { page, size });
}

export function listLicenseKeyByAssignedDeviceId(assignedDeviceId: string, page = 0, size = 20): Promise<Page<LicenseKey>> {
  return http.get<Page<LicenseKey>>(`${LICENSEKEY_PATH}/by-assigned-device-id/${assignedDeviceId}`, { page, size });
}
