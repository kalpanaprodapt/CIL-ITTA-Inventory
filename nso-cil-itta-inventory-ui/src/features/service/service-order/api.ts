import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SERVICEORDER_PATH } from './types';
import type { ServiceOrder, ServiceOrderFilter, ServiceOrderInput, ServiceOrderSummary, ServiceOrderUpdateInput } from './types';
import { toServiceOrderQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchServiceOrders(
  filter: ServiceOrderFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ServiceOrder>> {
  return http.get<Page<ServiceOrder>>(SERVICEORDER_PATH, { ...toServiceOrderQuery(filter), page, size, sort });
}

export function getServiceOrder(id: string): Promise<ServiceOrder> {
  return http.get<ServiceOrder>(`${SERVICEORDER_PATH}/${id}`);
}

export function getServiceOrderByCode(code: string): Promise<ServiceOrder> {
  return http.get<ServiceOrder>(`${SERVICEORDER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listServiceOrderSummaries(status?: LifecycleStatus): Promise<ServiceOrderSummary[]> {
  return http.get<ServiceOrderSummary[]>(`${SERVICEORDER_PATH}/summaries`, { status });
}

export function createServiceOrder(input: ServiceOrderInput): Promise<ServiceOrder> {
  return http.post<ServiceOrder>(SERVICEORDER_PATH, input);
}

export function updateServiceOrder(id: string, input: ServiceOrderUpdateInput): Promise<ServiceOrder> {
  return http.put<ServiceOrder>(`${SERVICEORDER_PATH}/${id}`, input);
}

export function changeServiceOrderStatus(id: string, status: LifecycleStatus): Promise<ServiceOrder> {
  return http.patch<ServiceOrder>(`${SERVICEORDER_PATH}/${id}/status`, undefined, { status });
}

export function deleteServiceOrder(id: string): Promise<void> {
  return http.delete(`${SERVICEORDER_PATH}/${id}`);
}

export function bulkCreateServiceOrders(inputs: ServiceOrderInput[]): Promise<BulkResult<ServiceOrder>> {
  return http.post<BulkResult<ServiceOrder>>(`${SERVICEORDER_PATH}/bulk`, inputs);
}

export function getServiceOrderStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SERVICEORDER_PATH}/stats/status`);
}

export function exportServiceOrdersCsv(filter: ServiceOrderFilter): Promise<Blob> {
  return http.blob(`${SERVICEORDER_PATH}/export`, toServiceOrderQuery(filter));
}

export function listServiceOrderByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<ServiceOrder>> {
  return http.get<Page<ServiceOrder>>(`${SERVICEORDER_PATH}/by-customer-id/${customerId}`, { page, size });
}
