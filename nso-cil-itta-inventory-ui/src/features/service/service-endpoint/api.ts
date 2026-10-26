import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SERVICEENDPOINT_PATH } from './types';
import type { ServiceEndpoint, ServiceEndpointFilter, ServiceEndpointInput, ServiceEndpointSummary, ServiceEndpointUpdateInput } from './types';
import { toServiceEndpointQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchServiceEndpoints(
  filter: ServiceEndpointFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ServiceEndpoint>> {
  return http.get<Page<ServiceEndpoint>>(SERVICEENDPOINT_PATH, { ...toServiceEndpointQuery(filter), page, size, sort });
}

export function getServiceEndpoint(id: string): Promise<ServiceEndpoint> {
  return http.get<ServiceEndpoint>(`${SERVICEENDPOINT_PATH}/${id}`);
}

export function getServiceEndpointByCode(code: string): Promise<ServiceEndpoint> {
  return http.get<ServiceEndpoint>(`${SERVICEENDPOINT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listServiceEndpointSummaries(status?: LifecycleStatus): Promise<ServiceEndpointSummary[]> {
  return http.get<ServiceEndpointSummary[]>(`${SERVICEENDPOINT_PATH}/summaries`, { status });
}

export function createServiceEndpoint(input: ServiceEndpointInput): Promise<ServiceEndpoint> {
  return http.post<ServiceEndpoint>(SERVICEENDPOINT_PATH, input);
}

export function updateServiceEndpoint(id: string, input: ServiceEndpointUpdateInput): Promise<ServiceEndpoint> {
  return http.put<ServiceEndpoint>(`${SERVICEENDPOINT_PATH}/${id}`, input);
}

export function changeServiceEndpointStatus(id: string, status: LifecycleStatus): Promise<ServiceEndpoint> {
  return http.patch<ServiceEndpoint>(`${SERVICEENDPOINT_PATH}/${id}/status`, undefined, { status });
}

export function deleteServiceEndpoint(id: string): Promise<void> {
  return http.delete(`${SERVICEENDPOINT_PATH}/${id}`);
}

export function bulkCreateServiceEndpoints(inputs: ServiceEndpointInput[]): Promise<BulkResult<ServiceEndpoint>> {
  return http.post<BulkResult<ServiceEndpoint>>(`${SERVICEENDPOINT_PATH}/bulk`, inputs);
}

export function getServiceEndpointStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SERVICEENDPOINT_PATH}/stats/status`);
}

export function exportServiceEndpointsCsv(filter: ServiceEndpointFilter): Promise<Blob> {
  return http.blob(`${SERVICEENDPOINT_PATH}/export`, toServiceEndpointQuery(filter));
}

export function listServiceEndpointByServiceId(serviceId: string, page = 0, size = 20): Promise<Page<ServiceEndpoint>> {
  return http.get<Page<ServiceEndpoint>>(`${SERVICEENDPOINT_PATH}/by-service-id/${serviceId}`, { page, size });
}

export function listServiceEndpointBySiteId(siteId: string, page = 0, size = 20): Promise<Page<ServiceEndpoint>> {
  return http.get<Page<ServiceEndpoint>>(`${SERVICEENDPOINT_PATH}/by-site-id/${siteId}`, { page, size });
}

export function listServiceEndpointByPortId(portId: string, page = 0, size = 20): Promise<Page<ServiceEndpoint>> {
  return http.get<Page<ServiceEndpoint>>(`${SERVICEENDPOINT_PATH}/by-port-id/${portId}`, { page, size });
}
