import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TUNNELENDPOINT_PATH } from './types';
import type { TunnelEndpoint, TunnelEndpointFilter, TunnelEndpointInput, TunnelEndpointSummary, TunnelEndpointUpdateInput } from './types';
import { toTunnelEndpointQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTunnelEndpoints(
  filter: TunnelEndpointFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<TunnelEndpoint>> {
  return http.get<Page<TunnelEndpoint>>(TUNNELENDPOINT_PATH, { ...toTunnelEndpointQuery(filter), page, size, sort });
}

export function getTunnelEndpoint(id: string): Promise<TunnelEndpoint> {
  return http.get<TunnelEndpoint>(`${TUNNELENDPOINT_PATH}/${id}`);
}

export function getTunnelEndpointByCode(code: string): Promise<TunnelEndpoint> {
  return http.get<TunnelEndpoint>(`${TUNNELENDPOINT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTunnelEndpointSummaries(status?: LifecycleStatus): Promise<TunnelEndpointSummary[]> {
  return http.get<TunnelEndpointSummary[]>(`${TUNNELENDPOINT_PATH}/summaries`, { status });
}

export function createTunnelEndpoint(input: TunnelEndpointInput): Promise<TunnelEndpoint> {
  return http.post<TunnelEndpoint>(TUNNELENDPOINT_PATH, input);
}

export function updateTunnelEndpoint(id: string, input: TunnelEndpointUpdateInput): Promise<TunnelEndpoint> {
  return http.put<TunnelEndpoint>(`${TUNNELENDPOINT_PATH}/${id}`, input);
}

export function changeTunnelEndpointStatus(id: string, status: LifecycleStatus): Promise<TunnelEndpoint> {
  return http.patch<TunnelEndpoint>(`${TUNNELENDPOINT_PATH}/${id}/status`, undefined, { status });
}

export function deleteTunnelEndpoint(id: string): Promise<void> {
  return http.delete(`${TUNNELENDPOINT_PATH}/${id}`);
}

export function bulkCreateTunnelEndpoints(inputs: TunnelEndpointInput[]): Promise<BulkResult<TunnelEndpoint>> {
  return http.post<BulkResult<TunnelEndpoint>>(`${TUNNELENDPOINT_PATH}/bulk`, inputs);
}

export function getTunnelEndpointStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TUNNELENDPOINT_PATH}/stats/status`);
}

export function exportTunnelEndpointsCsv(filter: TunnelEndpointFilter): Promise<Blob> {
  return http.blob(`${TUNNELENDPOINT_PATH}/export`, toTunnelEndpointQuery(filter));
}

export function listTunnelEndpointByDeviceId(deviceId: string, page = 0, size = 20): Promise<Page<TunnelEndpoint>> {
  return http.get<Page<TunnelEndpoint>>(`${TUNNELENDPOINT_PATH}/by-device-id/${deviceId}`, { page, size });
}
