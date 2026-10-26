import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CIRCUITSEGMENT_PATH } from './types';
import type { CircuitSegment, CircuitSegmentFilter, CircuitSegmentInput, CircuitSegmentSummary, CircuitSegmentUpdateInput } from './types';
import { toCircuitSegmentQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCircuitSegments(
  filter: CircuitSegmentFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CircuitSegment>> {
  return http.get<Page<CircuitSegment>>(CIRCUITSEGMENT_PATH, { ...toCircuitSegmentQuery(filter), page, size, sort });
}

export function getCircuitSegment(id: string): Promise<CircuitSegment> {
  return http.get<CircuitSegment>(`${CIRCUITSEGMENT_PATH}/${id}`);
}

export function getCircuitSegmentByCode(code: string): Promise<CircuitSegment> {
  return http.get<CircuitSegment>(`${CIRCUITSEGMENT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCircuitSegmentSummaries(status?: LifecycleStatus): Promise<CircuitSegmentSummary[]> {
  return http.get<CircuitSegmentSummary[]>(`${CIRCUITSEGMENT_PATH}/summaries`, { status });
}

export function createCircuitSegment(input: CircuitSegmentInput): Promise<CircuitSegment> {
  return http.post<CircuitSegment>(CIRCUITSEGMENT_PATH, input);
}

export function updateCircuitSegment(id: string, input: CircuitSegmentUpdateInput): Promise<CircuitSegment> {
  return http.put<CircuitSegment>(`${CIRCUITSEGMENT_PATH}/${id}`, input);
}

export function changeCircuitSegmentStatus(id: string, status: LifecycleStatus): Promise<CircuitSegment> {
  return http.patch<CircuitSegment>(`${CIRCUITSEGMENT_PATH}/${id}/status`, undefined, { status });
}

export function deleteCircuitSegment(id: string): Promise<void> {
  return http.delete(`${CIRCUITSEGMENT_PATH}/${id}`);
}

export function bulkCreateCircuitSegments(inputs: CircuitSegmentInput[]): Promise<BulkResult<CircuitSegment>> {
  return http.post<BulkResult<CircuitSegment>>(`${CIRCUITSEGMENT_PATH}/bulk`, inputs);
}

export function getCircuitSegmentStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CIRCUITSEGMENT_PATH}/stats/status`);
}

export function exportCircuitSegmentsCsv(filter: CircuitSegmentFilter): Promise<Blob> {
  return http.blob(`${CIRCUITSEGMENT_PATH}/export`, toCircuitSegmentQuery(filter));
}

export function listCircuitSegmentByCircuitId(circuitId: string, page = 0, size = 20): Promise<Page<CircuitSegment>> {
  return http.get<Page<CircuitSegment>>(`${CIRCUITSEGMENT_PATH}/by-circuit-id/${circuitId}`, { page, size });
}

export function listCircuitSegmentByATerminationId(aTerminationId: string, page = 0, size = 20): Promise<Page<CircuitSegment>> {
  return http.get<Page<CircuitSegment>>(`${CIRCUITSEGMENT_PATH}/by-a-termination-id/${aTerminationId}`, { page, size });
}

export function listCircuitSegmentByZTerminationId(zTerminationId: string, page = 0, size = 20): Promise<Page<CircuitSegment>> {
  return http.get<Page<CircuitSegment>>(`${CIRCUITSEGMENT_PATH}/by-z-termination-id/${zTerminationId}`, { page, size });
}
