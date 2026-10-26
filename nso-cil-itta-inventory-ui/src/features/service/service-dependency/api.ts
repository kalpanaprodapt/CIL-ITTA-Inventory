import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SERVICEDEPENDENCY_PATH } from './types';
import type { ServiceDependency, ServiceDependencyFilter, ServiceDependencyInput, ServiceDependencySummary, ServiceDependencyUpdateInput } from './types';
import { toServiceDependencyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchServiceDependencys(
  filter: ServiceDependencyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ServiceDependency>> {
  return http.get<Page<ServiceDependency>>(SERVICEDEPENDENCY_PATH, { ...toServiceDependencyQuery(filter), page, size, sort });
}

export function getServiceDependency(id: string): Promise<ServiceDependency> {
  return http.get<ServiceDependency>(`${SERVICEDEPENDENCY_PATH}/${id}`);
}

export function getServiceDependencyByCode(code: string): Promise<ServiceDependency> {
  return http.get<ServiceDependency>(`${SERVICEDEPENDENCY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listServiceDependencySummaries(status?: LifecycleStatus): Promise<ServiceDependencySummary[]> {
  return http.get<ServiceDependencySummary[]>(`${SERVICEDEPENDENCY_PATH}/summaries`, { status });
}

export function createServiceDependency(input: ServiceDependencyInput): Promise<ServiceDependency> {
  return http.post<ServiceDependency>(SERVICEDEPENDENCY_PATH, input);
}

export function updateServiceDependency(id: string, input: ServiceDependencyUpdateInput): Promise<ServiceDependency> {
  return http.put<ServiceDependency>(`${SERVICEDEPENDENCY_PATH}/${id}`, input);
}

export function changeServiceDependencyStatus(id: string, status: LifecycleStatus): Promise<ServiceDependency> {
  return http.patch<ServiceDependency>(`${SERVICEDEPENDENCY_PATH}/${id}/status`, undefined, { status });
}

export function deleteServiceDependency(id: string): Promise<void> {
  return http.delete(`${SERVICEDEPENDENCY_PATH}/${id}`);
}

export function bulkCreateServiceDependencys(inputs: ServiceDependencyInput[]): Promise<BulkResult<ServiceDependency>> {
  return http.post<BulkResult<ServiceDependency>>(`${SERVICEDEPENDENCY_PATH}/bulk`, inputs);
}

export function getServiceDependencyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SERVICEDEPENDENCY_PATH}/stats/status`);
}

export function exportServiceDependencysCsv(filter: ServiceDependencyFilter): Promise<Blob> {
  return http.blob(`${SERVICEDEPENDENCY_PATH}/export`, toServiceDependencyQuery(filter));
}

export function listServiceDependencyByServiceId(serviceId: string, page = 0, size = 20): Promise<Page<ServiceDependency>> {
  return http.get<Page<ServiceDependency>>(`${SERVICEDEPENDENCY_PATH}/by-service-id/${serviceId}`, { page, size });
}

export function listServiceDependencyByDependsOnId(dependsOnId: string, page = 0, size = 20): Promise<Page<ServiceDependency>> {
  return http.get<Page<ServiceDependency>>(`${SERVICEDEPENDENCY_PATH}/by-depends-on-id/${dependsOnId}`, { page, size });
}
