import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CIRCUIT_PATH } from './types';
import type { Circuit, CircuitFilter, CircuitInput, CircuitSummary, CircuitUpdateInput } from './types';
import { toCircuitQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCircuits(
  filter: CircuitFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Circuit>> {
  return http.get<Page<Circuit>>(CIRCUIT_PATH, { ...toCircuitQuery(filter), page, size, sort });
}

export function getCircuit(id: string): Promise<Circuit> {
  return http.get<Circuit>(`${CIRCUIT_PATH}/${id}`);
}

export function getCircuitByCode(code: string): Promise<Circuit> {
  return http.get<Circuit>(`${CIRCUIT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCircuitSummaries(status?: LifecycleStatus): Promise<CircuitSummary[]> {
  return http.get<CircuitSummary[]>(`${CIRCUIT_PATH}/summaries`, { status });
}

export function createCircuit(input: CircuitInput): Promise<Circuit> {
  return http.post<Circuit>(CIRCUIT_PATH, input);
}

export function updateCircuit(id: string, input: CircuitUpdateInput): Promise<Circuit> {
  return http.put<Circuit>(`${CIRCUIT_PATH}/${id}`, input);
}

export function changeCircuitStatus(id: string, status: LifecycleStatus): Promise<Circuit> {
  return http.patch<Circuit>(`${CIRCUIT_PATH}/${id}/status`, undefined, { status });
}

export function deleteCircuit(id: string): Promise<void> {
  return http.delete(`${CIRCUIT_PATH}/${id}`);
}

export function bulkCreateCircuits(inputs: CircuitInput[]): Promise<BulkResult<Circuit>> {
  return http.post<BulkResult<Circuit>>(`${CIRCUIT_PATH}/bulk`, inputs);
}

export function getCircuitStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CIRCUIT_PATH}/stats/status`);
}

export function exportCircuitsCsv(filter: CircuitFilter): Promise<Blob> {
  return http.blob(`${CIRCUIT_PATH}/export`, toCircuitQuery(filter));
}

export function listCircuitByProviderId(providerId: string, page = 0, size = 20): Promise<Page<Circuit>> {
  return http.get<Page<Circuit>>(`${CIRCUIT_PATH}/by-provider-id/${providerId}`, { page, size });
}

export function listCircuitByASiteId(aSiteId: string, page = 0, size = 20): Promise<Page<Circuit>> {
  return http.get<Page<Circuit>>(`${CIRCUIT_PATH}/by-a-site-id/${aSiteId}`, { page, size });
}

export function listCircuitByZSiteId(zSiteId: string, page = 0, size = 20): Promise<Page<Circuit>> {
  return http.get<Page<Circuit>>(`${CIRCUIT_PATH}/by-z-site-id/${zSiteId}`, { page, size });
}
