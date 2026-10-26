import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { INTEGRATIONENDPOINT_PATH } from './types';
import type { IntegrationEndpoint, IntegrationEndpointFilter, IntegrationEndpointInput, IntegrationEndpointSummary, IntegrationEndpointUpdateInput } from './types';
import { toIntegrationEndpointQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchIntegrationEndpoints(
  filter: IntegrationEndpointFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<IntegrationEndpoint>> {
  return http.get<Page<IntegrationEndpoint>>(INTEGRATIONENDPOINT_PATH, { ...toIntegrationEndpointQuery(filter), page, size, sort });
}

export function getIntegrationEndpoint(id: string): Promise<IntegrationEndpoint> {
  return http.get<IntegrationEndpoint>(`${INTEGRATIONENDPOINT_PATH}/${id}`);
}

export function getIntegrationEndpointByCode(code: string): Promise<IntegrationEndpoint> {
  return http.get<IntegrationEndpoint>(`${INTEGRATIONENDPOINT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listIntegrationEndpointSummaries(status?: LifecycleStatus): Promise<IntegrationEndpointSummary[]> {
  return http.get<IntegrationEndpointSummary[]>(`${INTEGRATIONENDPOINT_PATH}/summaries`, { status });
}

export function createIntegrationEndpoint(input: IntegrationEndpointInput): Promise<IntegrationEndpoint> {
  return http.post<IntegrationEndpoint>(INTEGRATIONENDPOINT_PATH, input);
}

export function updateIntegrationEndpoint(id: string, input: IntegrationEndpointUpdateInput): Promise<IntegrationEndpoint> {
  return http.put<IntegrationEndpoint>(`${INTEGRATIONENDPOINT_PATH}/${id}`, input);
}

export function changeIntegrationEndpointStatus(id: string, status: LifecycleStatus): Promise<IntegrationEndpoint> {
  return http.patch<IntegrationEndpoint>(`${INTEGRATIONENDPOINT_PATH}/${id}/status`, undefined, { status });
}

export function deleteIntegrationEndpoint(id: string): Promise<void> {
  return http.delete(`${INTEGRATIONENDPOINT_PATH}/${id}`);
}

export function bulkCreateIntegrationEndpoints(inputs: IntegrationEndpointInput[]): Promise<BulkResult<IntegrationEndpoint>> {
  return http.post<BulkResult<IntegrationEndpoint>>(`${INTEGRATIONENDPOINT_PATH}/bulk`, inputs);
}

export function getIntegrationEndpointStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${INTEGRATIONENDPOINT_PATH}/stats/status`);
}

export function exportIntegrationEndpointsCsv(filter: IntegrationEndpointFilter): Promise<Blob> {
  return http.blob(`${INTEGRATIONENDPOINT_PATH}/export`, toIntegrationEndpointQuery(filter));
}
