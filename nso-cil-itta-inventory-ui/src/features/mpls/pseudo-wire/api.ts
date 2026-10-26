import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PSEUDOWIRE_PATH } from './types';
import type { PseudoWire, PseudoWireFilter, PseudoWireInput, PseudoWireSummary, PseudoWireUpdateInput } from './types';
import { toPseudoWireQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPseudoWires(
  filter: PseudoWireFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PseudoWire>> {
  return http.get<Page<PseudoWire>>(PSEUDOWIRE_PATH, { ...toPseudoWireQuery(filter), page, size, sort });
}

export function getPseudoWire(id: string): Promise<PseudoWire> {
  return http.get<PseudoWire>(`${PSEUDOWIRE_PATH}/${id}`);
}

export function getPseudoWireByCode(code: string): Promise<PseudoWire> {
  return http.get<PseudoWire>(`${PSEUDOWIRE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPseudoWireSummaries(status?: LifecycleStatus): Promise<PseudoWireSummary[]> {
  return http.get<PseudoWireSummary[]>(`${PSEUDOWIRE_PATH}/summaries`, { status });
}

export function createPseudoWire(input: PseudoWireInput): Promise<PseudoWire> {
  return http.post<PseudoWire>(PSEUDOWIRE_PATH, input);
}

export function updatePseudoWire(id: string, input: PseudoWireUpdateInput): Promise<PseudoWire> {
  return http.put<PseudoWire>(`${PSEUDOWIRE_PATH}/${id}`, input);
}

export function changePseudoWireStatus(id: string, status: LifecycleStatus): Promise<PseudoWire> {
  return http.patch<PseudoWire>(`${PSEUDOWIRE_PATH}/${id}/status`, undefined, { status });
}

export function deletePseudoWire(id: string): Promise<void> {
  return http.delete(`${PSEUDOWIRE_PATH}/${id}`);
}

export function bulkCreatePseudoWires(inputs: PseudoWireInput[]): Promise<BulkResult<PseudoWire>> {
  return http.post<BulkResult<PseudoWire>>(`${PSEUDOWIRE_PATH}/bulk`, inputs);
}

export function getPseudoWireStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PSEUDOWIRE_PATH}/stats/status`);
}

export function exportPseudoWiresCsv(filter: PseudoWireFilter): Promise<Blob> {
  return http.blob(`${PSEUDOWIRE_PATH}/export`, toPseudoWireQuery(filter));
}

export function listPseudoWireByAEndId(aEndId: string, page = 0, size = 20): Promise<Page<PseudoWire>> {
  return http.get<Page<PseudoWire>>(`${PSEUDOWIRE_PATH}/by-a-end-id/${aEndId}`, { page, size });
}

export function listPseudoWireByZEndId(zEndId: string, page = 0, size = 20): Promise<Page<PseudoWire>> {
  return http.get<Page<PseudoWire>>(`${PSEUDOWIRE_PATH}/by-z-end-id/${zEndId}`, { page, size });
}
