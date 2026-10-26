import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SKU_PATH } from './types';
import type { Sku, SkuFilter, SkuInput, SkuSummary, SkuUpdateInput } from './types';
import { toSkuQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSkus(
  filter: SkuFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Sku>> {
  return http.get<Page<Sku>>(SKU_PATH, { ...toSkuQuery(filter), page, size, sort });
}

export function getSku(id: string): Promise<Sku> {
  return http.get<Sku>(`${SKU_PATH}/${id}`);
}

export function getSkuByCode(code: string): Promise<Sku> {
  return http.get<Sku>(`${SKU_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSkuSummaries(status?: LifecycleStatus): Promise<SkuSummary[]> {
  return http.get<SkuSummary[]>(`${SKU_PATH}/summaries`, { status });
}

export function createSku(input: SkuInput): Promise<Sku> {
  return http.post<Sku>(SKU_PATH, input);
}

export function updateSku(id: string, input: SkuUpdateInput): Promise<Sku> {
  return http.put<Sku>(`${SKU_PATH}/${id}`, input);
}

export function changeSkuStatus(id: string, status: LifecycleStatus): Promise<Sku> {
  return http.patch<Sku>(`${SKU_PATH}/${id}/status`, undefined, { status });
}

export function deleteSku(id: string): Promise<void> {
  return http.delete(`${SKU_PATH}/${id}`);
}

export function bulkCreateSkus(inputs: SkuInput[]): Promise<BulkResult<Sku>> {
  return http.post<BulkResult<Sku>>(`${SKU_PATH}/bulk`, inputs);
}

export function getSkuStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SKU_PATH}/stats/status`);
}

export function exportSkusCsv(filter: SkuFilter): Promise<Blob> {
  return http.blob(`${SKU_PATH}/export`, toSkuQuery(filter));
}

export function listSkuByModelId(modelId: string, page = 0, size = 20): Promise<Page<Sku>> {
  return http.get<Page<Sku>>(`${SKU_PATH}/by-model-id/${modelId}`, { page, size });
}
