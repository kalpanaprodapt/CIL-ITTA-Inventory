import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CIRCUITORDER_PATH } from './types';
import type { CircuitOrder, CircuitOrderFilter, CircuitOrderInput, CircuitOrderSummary, CircuitOrderUpdateInput } from './types';
import { toCircuitOrderQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCircuitOrders(
  filter: CircuitOrderFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CircuitOrder>> {
  return http.get<Page<CircuitOrder>>(CIRCUITORDER_PATH, { ...toCircuitOrderQuery(filter), page, size, sort });
}

export function getCircuitOrder(id: string): Promise<CircuitOrder> {
  return http.get<CircuitOrder>(`${CIRCUITORDER_PATH}/${id}`);
}

export function getCircuitOrderByCode(code: string): Promise<CircuitOrder> {
  return http.get<CircuitOrder>(`${CIRCUITORDER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCircuitOrderSummaries(status?: LifecycleStatus): Promise<CircuitOrderSummary[]> {
  return http.get<CircuitOrderSummary[]>(`${CIRCUITORDER_PATH}/summaries`, { status });
}

export function createCircuitOrder(input: CircuitOrderInput): Promise<CircuitOrder> {
  return http.post<CircuitOrder>(CIRCUITORDER_PATH, input);
}

export function updateCircuitOrder(id: string, input: CircuitOrderUpdateInput): Promise<CircuitOrder> {
  return http.put<CircuitOrder>(`${CIRCUITORDER_PATH}/${id}`, input);
}

export function changeCircuitOrderStatus(id: string, status: LifecycleStatus): Promise<CircuitOrder> {
  return http.patch<CircuitOrder>(`${CIRCUITORDER_PATH}/${id}/status`, undefined, { status });
}

export function deleteCircuitOrder(id: string): Promise<void> {
  return http.delete(`${CIRCUITORDER_PATH}/${id}`);
}

export function bulkCreateCircuitOrders(inputs: CircuitOrderInput[]): Promise<BulkResult<CircuitOrder>> {
  return http.post<BulkResult<CircuitOrder>>(`${CIRCUITORDER_PATH}/bulk`, inputs);
}

export function getCircuitOrderStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CIRCUITORDER_PATH}/stats/status`);
}

export function exportCircuitOrdersCsv(filter: CircuitOrderFilter): Promise<Blob> {
  return http.blob(`${CIRCUITORDER_PATH}/export`, toCircuitOrderQuery(filter));
}

export function listCircuitOrderByCircuitId(circuitId: string, page = 0, size = 20): Promise<Page<CircuitOrder>> {
  return http.get<Page<CircuitOrder>>(`${CIRCUITORDER_PATH}/by-circuit-id/${circuitId}`, { page, size });
}
