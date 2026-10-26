import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CIRCUITTERMINATION_PATH } from './types';
import type { CircuitTermination, CircuitTerminationFilter, CircuitTerminationInput, CircuitTerminationSummary, CircuitTerminationUpdateInput } from './types';
import { toCircuitTerminationQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCircuitTerminations(
  filter: CircuitTerminationFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CircuitTermination>> {
  return http.get<Page<CircuitTermination>>(CIRCUITTERMINATION_PATH, { ...toCircuitTerminationQuery(filter), page, size, sort });
}

export function getCircuitTermination(id: string): Promise<CircuitTermination> {
  return http.get<CircuitTermination>(`${CIRCUITTERMINATION_PATH}/${id}`);
}

export function getCircuitTerminationByCode(code: string): Promise<CircuitTermination> {
  return http.get<CircuitTermination>(`${CIRCUITTERMINATION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCircuitTerminationSummaries(status?: LifecycleStatus): Promise<CircuitTerminationSummary[]> {
  return http.get<CircuitTerminationSummary[]>(`${CIRCUITTERMINATION_PATH}/summaries`, { status });
}

export function createCircuitTermination(input: CircuitTerminationInput): Promise<CircuitTermination> {
  return http.post<CircuitTermination>(CIRCUITTERMINATION_PATH, input);
}

export function updateCircuitTermination(id: string, input: CircuitTerminationUpdateInput): Promise<CircuitTermination> {
  return http.put<CircuitTermination>(`${CIRCUITTERMINATION_PATH}/${id}`, input);
}

export function changeCircuitTerminationStatus(id: string, status: LifecycleStatus): Promise<CircuitTermination> {
  return http.patch<CircuitTermination>(`${CIRCUITTERMINATION_PATH}/${id}/status`, undefined, { status });
}

export function deleteCircuitTermination(id: string): Promise<void> {
  return http.delete(`${CIRCUITTERMINATION_PATH}/${id}`);
}

export function bulkCreateCircuitTerminations(inputs: CircuitTerminationInput[]): Promise<BulkResult<CircuitTermination>> {
  return http.post<BulkResult<CircuitTermination>>(`${CIRCUITTERMINATION_PATH}/bulk`, inputs);
}

export function getCircuitTerminationStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CIRCUITTERMINATION_PATH}/stats/status`);
}

export function exportCircuitTerminationsCsv(filter: CircuitTerminationFilter): Promise<Blob> {
  return http.blob(`${CIRCUITTERMINATION_PATH}/export`, toCircuitTerminationQuery(filter));
}

export function listCircuitTerminationByCircuitId(circuitId: string, page = 0, size = 20): Promise<Page<CircuitTermination>> {
  return http.get<Page<CircuitTermination>>(`${CIRCUITTERMINATION_PATH}/by-circuit-id/${circuitId}`, { page, size });
}

export function listCircuitTerminationByPortId(portId: string, page = 0, size = 20): Promise<Page<CircuitTermination>> {
  return http.get<Page<CircuitTermination>>(`${CIRCUITTERMINATION_PATH}/by-port-id/${portId}`, { page, size });
}
