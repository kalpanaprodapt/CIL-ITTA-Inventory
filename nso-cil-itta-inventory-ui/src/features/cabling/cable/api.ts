import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CABLE_PATH } from './types';
import type { Cable, CableFilter, CableInput, CableSummary, CableUpdateInput } from './types';
import { toCableQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCables(
  filter: CableFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Cable>> {
  return http.get<Page<Cable>>(CABLE_PATH, { ...toCableQuery(filter), page, size, sort });
}

export function getCable(id: string): Promise<Cable> {
  return http.get<Cable>(`${CABLE_PATH}/${id}`);
}

export function getCableByCode(code: string): Promise<Cable> {
  return http.get<Cable>(`${CABLE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCableSummaries(status?: LifecycleStatus): Promise<CableSummary[]> {
  return http.get<CableSummary[]>(`${CABLE_PATH}/summaries`, { status });
}

export function createCable(input: CableInput): Promise<Cable> {
  return http.post<Cable>(CABLE_PATH, input);
}

export function updateCable(id: string, input: CableUpdateInput): Promise<Cable> {
  return http.put<Cable>(`${CABLE_PATH}/${id}`, input);
}

export function changeCableStatus(id: string, status: LifecycleStatus): Promise<Cable> {
  return http.patch<Cable>(`${CABLE_PATH}/${id}/status`, undefined, { status });
}

export function deleteCable(id: string): Promise<void> {
  return http.delete(`${CABLE_PATH}/${id}`);
}

export function bulkCreateCables(inputs: CableInput[]): Promise<BulkResult<Cable>> {
  return http.post<BulkResult<Cable>>(`${CABLE_PATH}/bulk`, inputs);
}

export function getCableStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CABLE_PATH}/stats/status`);
}

export function exportCablesCsv(filter: CableFilter): Promise<Blob> {
  return http.blob(`${CABLE_PATH}/export`, toCableQuery(filter));
}

export function listCableByAEndPortId(aEndPortId: string, page = 0, size = 20): Promise<Page<Cable>> {
  return http.get<Page<Cable>>(`${CABLE_PATH}/by-a-end-port-id/${aEndPortId}`, { page, size });
}

export function listCableByZEndPortId(zEndPortId: string, page = 0, size = 20): Promise<Page<Cable>> {
  return http.get<Page<Cable>>(`${CABLE_PATH}/by-z-end-port-id/${zEndPortId}`, { page, size });
}
