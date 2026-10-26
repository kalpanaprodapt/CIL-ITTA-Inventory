import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { WARRANTY_PATH } from './types';
import type { Warranty, WarrantyFilter, WarrantyInput, WarrantySummary, WarrantyUpdateInput } from './types';
import { toWarrantyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchWarrantys(
  filter: WarrantyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Warranty>> {
  return http.get<Page<Warranty>>(WARRANTY_PATH, { ...toWarrantyQuery(filter), page, size, sort });
}

export function getWarranty(id: string): Promise<Warranty> {
  return http.get<Warranty>(`${WARRANTY_PATH}/${id}`);
}

export function getWarrantyByCode(code: string): Promise<Warranty> {
  return http.get<Warranty>(`${WARRANTY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listWarrantySummaries(status?: LifecycleStatus): Promise<WarrantySummary[]> {
  return http.get<WarrantySummary[]>(`${WARRANTY_PATH}/summaries`, { status });
}

export function createWarranty(input: WarrantyInput): Promise<Warranty> {
  return http.post<Warranty>(WARRANTY_PATH, input);
}

export function updateWarranty(id: string, input: WarrantyUpdateInput): Promise<Warranty> {
  return http.put<Warranty>(`${WARRANTY_PATH}/${id}`, input);
}

export function changeWarrantyStatus(id: string, status: LifecycleStatus): Promise<Warranty> {
  return http.patch<Warranty>(`${WARRANTY_PATH}/${id}/status`, undefined, { status });
}

export function deleteWarranty(id: string): Promise<void> {
  return http.delete(`${WARRANTY_PATH}/${id}`);
}

export function bulkCreateWarrantys(inputs: WarrantyInput[]): Promise<BulkResult<Warranty>> {
  return http.post<BulkResult<Warranty>>(`${WARRANTY_PATH}/bulk`, inputs);
}

export function getWarrantyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${WARRANTY_PATH}/stats/status`);
}

export function exportWarrantysCsv(filter: WarrantyFilter): Promise<Blob> {
  return http.blob(`${WARRANTY_PATH}/export`, toWarrantyQuery(filter));
}

export function listWarrantyByAssetId(assetId: string, page = 0, size = 20): Promise<Page<Warranty>> {
  return http.get<Page<Warranty>>(`${WARRANTY_PATH}/by-asset-id/${assetId}`, { page, size });
}
