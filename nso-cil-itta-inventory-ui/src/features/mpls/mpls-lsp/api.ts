import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { MPLSLSP_PATH } from './types';
import type { MplsLsp, MplsLspFilter, MplsLspInput, MplsLspSummary, MplsLspUpdateInput } from './types';
import { toMplsLspQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchMplsLsps(
  filter: MplsLspFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<MplsLsp>> {
  return http.get<Page<MplsLsp>>(MPLSLSP_PATH, { ...toMplsLspQuery(filter), page, size, sort });
}

export function getMplsLsp(id: string): Promise<MplsLsp> {
  return http.get<MplsLsp>(`${MPLSLSP_PATH}/${id}`);
}

export function getMplsLspByCode(code: string): Promise<MplsLsp> {
  return http.get<MplsLsp>(`${MPLSLSP_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listMplsLspSummaries(status?: LifecycleStatus): Promise<MplsLspSummary[]> {
  return http.get<MplsLspSummary[]>(`${MPLSLSP_PATH}/summaries`, { status });
}

export function createMplsLsp(input: MplsLspInput): Promise<MplsLsp> {
  return http.post<MplsLsp>(MPLSLSP_PATH, input);
}

export function updateMplsLsp(id: string, input: MplsLspUpdateInput): Promise<MplsLsp> {
  return http.put<MplsLsp>(`${MPLSLSP_PATH}/${id}`, input);
}

export function changeMplsLspStatus(id: string, status: LifecycleStatus): Promise<MplsLsp> {
  return http.patch<MplsLsp>(`${MPLSLSP_PATH}/${id}/status`, undefined, { status });
}

export function deleteMplsLsp(id: string): Promise<void> {
  return http.delete(`${MPLSLSP_PATH}/${id}`);
}

export function bulkCreateMplsLsps(inputs: MplsLspInput[]): Promise<BulkResult<MplsLsp>> {
  return http.post<BulkResult<MplsLsp>>(`${MPLSLSP_PATH}/bulk`, inputs);
}

export function getMplsLspStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${MPLSLSP_PATH}/stats/status`);
}

export function exportMplsLspsCsv(filter: MplsLspFilter): Promise<Blob> {
  return http.blob(`${MPLSLSP_PATH}/export`, toMplsLspQuery(filter));
}

export function listMplsLspByHeadEndId(headEndId: string, page = 0, size = 20): Promise<Page<MplsLsp>> {
  return http.get<Page<MplsLsp>>(`${MPLSLSP_PATH}/by-head-end-id/${headEndId}`, { page, size });
}

export function listMplsLspByTailEndId(tailEndId: string, page = 0, size = 20): Promise<Page<MplsLsp>> {
  return http.get<Page<MplsLsp>>(`${MPLSLSP_PATH}/by-tail-end-id/${tailEndId}`, { page, size });
}
