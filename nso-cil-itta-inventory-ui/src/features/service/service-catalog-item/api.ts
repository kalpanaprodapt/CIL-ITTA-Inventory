import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SERVICECATALOGITEM_PATH } from './types';
import type { ServiceCatalogItem, ServiceCatalogItemFilter, ServiceCatalogItemInput, ServiceCatalogItemSummary, ServiceCatalogItemUpdateInput } from './types';
import { toServiceCatalogItemQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchServiceCatalogItems(
  filter: ServiceCatalogItemFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ServiceCatalogItem>> {
  return http.get<Page<ServiceCatalogItem>>(SERVICECATALOGITEM_PATH, { ...toServiceCatalogItemQuery(filter), page, size, sort });
}

export function getServiceCatalogItem(id: string): Promise<ServiceCatalogItem> {
  return http.get<ServiceCatalogItem>(`${SERVICECATALOGITEM_PATH}/${id}`);
}

export function getServiceCatalogItemByCode(code: string): Promise<ServiceCatalogItem> {
  return http.get<ServiceCatalogItem>(`${SERVICECATALOGITEM_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listServiceCatalogItemSummaries(status?: LifecycleStatus): Promise<ServiceCatalogItemSummary[]> {
  return http.get<ServiceCatalogItemSummary[]>(`${SERVICECATALOGITEM_PATH}/summaries`, { status });
}

export function createServiceCatalogItem(input: ServiceCatalogItemInput): Promise<ServiceCatalogItem> {
  return http.post<ServiceCatalogItem>(SERVICECATALOGITEM_PATH, input);
}

export function updateServiceCatalogItem(id: string, input: ServiceCatalogItemUpdateInput): Promise<ServiceCatalogItem> {
  return http.put<ServiceCatalogItem>(`${SERVICECATALOGITEM_PATH}/${id}`, input);
}

export function changeServiceCatalogItemStatus(id: string, status: LifecycleStatus): Promise<ServiceCatalogItem> {
  return http.patch<ServiceCatalogItem>(`${SERVICECATALOGITEM_PATH}/${id}/status`, undefined, { status });
}

export function deleteServiceCatalogItem(id: string): Promise<void> {
  return http.delete(`${SERVICECATALOGITEM_PATH}/${id}`);
}

export function bulkCreateServiceCatalogItems(inputs: ServiceCatalogItemInput[]): Promise<BulkResult<ServiceCatalogItem>> {
  return http.post<BulkResult<ServiceCatalogItem>>(`${SERVICECATALOGITEM_PATH}/bulk`, inputs);
}

export function getServiceCatalogItemStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SERVICECATALOGITEM_PATH}/stats/status`);
}

export function exportServiceCatalogItemsCsv(filter: ServiceCatalogItemFilter): Promise<Blob> {
  return http.blob(`${SERVICECATALOGITEM_PATH}/export`, toServiceCatalogItemQuery(filter));
}
