import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VENDOR_PATH } from './types';
import type { Vendor, VendorFilter, VendorInput, VendorSummary, VendorUpdateInput } from './types';
import { toVendorQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVendors(
  filter: VendorFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Vendor>> {
  return http.get<Page<Vendor>>(VENDOR_PATH, { ...toVendorQuery(filter), page, size, sort });
}

export function getVendor(id: string): Promise<Vendor> {
  return http.get<Vendor>(`${VENDOR_PATH}/${id}`);
}

export function getVendorByCode(code: string): Promise<Vendor> {
  return http.get<Vendor>(`${VENDOR_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVendorSummaries(status?: LifecycleStatus): Promise<VendorSummary[]> {
  return http.get<VendorSummary[]>(`${VENDOR_PATH}/summaries`, { status });
}

export function createVendor(input: VendorInput): Promise<Vendor> {
  return http.post<Vendor>(VENDOR_PATH, input);
}

export function updateVendor(id: string, input: VendorUpdateInput): Promise<Vendor> {
  return http.put<Vendor>(`${VENDOR_PATH}/${id}`, input);
}

export function changeVendorStatus(id: string, status: LifecycleStatus): Promise<Vendor> {
  return http.patch<Vendor>(`${VENDOR_PATH}/${id}/status`, undefined, { status });
}

export function deleteVendor(id: string): Promise<void> {
  return http.delete(`${VENDOR_PATH}/${id}`);
}

export function bulkCreateVendors(inputs: VendorInput[]): Promise<BulkResult<Vendor>> {
  return http.post<BulkResult<Vendor>>(`${VENDOR_PATH}/bulk`, inputs);
}

export function getVendorStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VENDOR_PATH}/stats/status`);
}

export function exportVendorsCsv(filter: VendorFilter): Promise<Blob> {
  return http.blob(`${VENDOR_PATH}/export`, toVendorQuery(filter));
}
