import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SERVICEINSTANCE_PATH } from './types';
import type { ServiceInstance, ServiceInstanceFilter, ServiceInstanceInput, ServiceInstanceSummary, ServiceInstanceUpdateInput } from './types';
import { toServiceInstanceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchServiceInstances(
  filter: ServiceInstanceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ServiceInstance>> {
  return http.get<Page<ServiceInstance>>(SERVICEINSTANCE_PATH, { ...toServiceInstanceQuery(filter), page, size, sort });
}

export function getServiceInstance(id: string): Promise<ServiceInstance> {
  return http.get<ServiceInstance>(`${SERVICEINSTANCE_PATH}/${id}`);
}

export function getServiceInstanceByCode(code: string): Promise<ServiceInstance> {
  return http.get<ServiceInstance>(`${SERVICEINSTANCE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listServiceInstanceSummaries(status?: LifecycleStatus): Promise<ServiceInstanceSummary[]> {
  return http.get<ServiceInstanceSummary[]>(`${SERVICEINSTANCE_PATH}/summaries`, { status });
}

export function createServiceInstance(input: ServiceInstanceInput): Promise<ServiceInstance> {
  return http.post<ServiceInstance>(SERVICEINSTANCE_PATH, input);
}

export function updateServiceInstance(id: string, input: ServiceInstanceUpdateInput): Promise<ServiceInstance> {
  return http.put<ServiceInstance>(`${SERVICEINSTANCE_PATH}/${id}`, input);
}

export function changeServiceInstanceStatus(id: string, status: LifecycleStatus): Promise<ServiceInstance> {
  return http.patch<ServiceInstance>(`${SERVICEINSTANCE_PATH}/${id}/status`, undefined, { status });
}

export function deleteServiceInstance(id: string): Promise<void> {
  return http.delete(`${SERVICEINSTANCE_PATH}/${id}`);
}

export function bulkCreateServiceInstances(inputs: ServiceInstanceInput[]): Promise<BulkResult<ServiceInstance>> {
  return http.post<BulkResult<ServiceInstance>>(`${SERVICEINSTANCE_PATH}/bulk`, inputs);
}

export function getServiceInstanceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SERVICEINSTANCE_PATH}/stats/status`);
}

export function exportServiceInstancesCsv(filter: ServiceInstanceFilter): Promise<Blob> {
  return http.blob(`${SERVICEINSTANCE_PATH}/export`, toServiceInstanceQuery(filter));
}

export function listServiceInstanceByCatalogItemId(catalogItemId: string, page = 0, size = 20): Promise<Page<ServiceInstance>> {
  return http.get<Page<ServiceInstance>>(`${SERVICEINSTANCE_PATH}/by-catalog-item-id/${catalogItemId}`, { page, size });
}

export function listServiceInstanceByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<ServiceInstance>> {
  return http.get<Page<ServiceInstance>>(`${SERVICEINSTANCE_PATH}/by-customer-id/${customerId}`, { page, size });
}
