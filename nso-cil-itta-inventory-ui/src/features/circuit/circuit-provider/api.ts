import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CIRCUITPROVIDER_PATH } from './types';
import type { CircuitProvider, CircuitProviderFilter, CircuitProviderInput, CircuitProviderSummary, CircuitProviderUpdateInput } from './types';
import { toCircuitProviderQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCircuitProviders(
  filter: CircuitProviderFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CircuitProvider>> {
  return http.get<Page<CircuitProvider>>(CIRCUITPROVIDER_PATH, { ...toCircuitProviderQuery(filter), page, size, sort });
}

export function getCircuitProvider(id: string): Promise<CircuitProvider> {
  return http.get<CircuitProvider>(`${CIRCUITPROVIDER_PATH}/${id}`);
}

export function getCircuitProviderByCode(code: string): Promise<CircuitProvider> {
  return http.get<CircuitProvider>(`${CIRCUITPROVIDER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCircuitProviderSummaries(status?: LifecycleStatus): Promise<CircuitProviderSummary[]> {
  return http.get<CircuitProviderSummary[]>(`${CIRCUITPROVIDER_PATH}/summaries`, { status });
}

export function createCircuitProvider(input: CircuitProviderInput): Promise<CircuitProvider> {
  return http.post<CircuitProvider>(CIRCUITPROVIDER_PATH, input);
}

export function updateCircuitProvider(id: string, input: CircuitProviderUpdateInput): Promise<CircuitProvider> {
  return http.put<CircuitProvider>(`${CIRCUITPROVIDER_PATH}/${id}`, input);
}

export function changeCircuitProviderStatus(id: string, status: LifecycleStatus): Promise<CircuitProvider> {
  return http.patch<CircuitProvider>(`${CIRCUITPROVIDER_PATH}/${id}/status`, undefined, { status });
}

export function deleteCircuitProvider(id: string): Promise<void> {
  return http.delete(`${CIRCUITPROVIDER_PATH}/${id}`);
}

export function bulkCreateCircuitProviders(inputs: CircuitProviderInput[]): Promise<BulkResult<CircuitProvider>> {
  return http.post<BulkResult<CircuitProvider>>(`${CIRCUITPROVIDER_PATH}/bulk`, inputs);
}

export function getCircuitProviderStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CIRCUITPROVIDER_PATH}/stats/status`);
}

export function exportCircuitProvidersCsv(filter: CircuitProviderFilter): Promise<Blob> {
  return http.blob(`${CIRCUITPROVIDER_PATH}/export`, toCircuitProviderQuery(filter));
}
