import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SPLICE_PATH } from './types';
import type { Splice, SpliceFilter, SpliceInput, SpliceSummary, SpliceUpdateInput } from './types';
import { toSpliceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSplices(
  filter: SpliceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Splice>> {
  return http.get<Page<Splice>>(SPLICE_PATH, { ...toSpliceQuery(filter), page, size, sort });
}

export function getSplice(id: string): Promise<Splice> {
  return http.get<Splice>(`${SPLICE_PATH}/${id}`);
}

export function getSpliceByCode(code: string): Promise<Splice> {
  return http.get<Splice>(`${SPLICE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSpliceSummaries(status?: LifecycleStatus): Promise<SpliceSummary[]> {
  return http.get<SpliceSummary[]>(`${SPLICE_PATH}/summaries`, { status });
}

export function createSplice(input: SpliceInput): Promise<Splice> {
  return http.post<Splice>(SPLICE_PATH, input);
}

export function updateSplice(id: string, input: SpliceUpdateInput): Promise<Splice> {
  return http.put<Splice>(`${SPLICE_PATH}/${id}`, input);
}

export function changeSpliceStatus(id: string, status: LifecycleStatus): Promise<Splice> {
  return http.patch<Splice>(`${SPLICE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSplice(id: string): Promise<void> {
  return http.delete(`${SPLICE_PATH}/${id}`);
}

export function bulkCreateSplices(inputs: SpliceInput[]): Promise<BulkResult<Splice>> {
  return http.post<BulkResult<Splice>>(`${SPLICE_PATH}/bulk`, inputs);
}

export function getSpliceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SPLICE_PATH}/stats/status`);
}

export function exportSplicesCsv(filter: SpliceFilter): Promise<Blob> {
  return http.blob(`${SPLICE_PATH}/export`, toSpliceQuery(filter));
}

export function listSpliceByAStrandId(aStrandId: string, page = 0, size = 20): Promise<Page<Splice>> {
  return http.get<Page<Splice>>(`${SPLICE_PATH}/by-a-strand-id/${aStrandId}`, { page, size });
}

export function listSpliceByZStrandId(zStrandId: string, page = 0, size = 20): Promise<Page<Splice>> {
  return http.get<Page<Splice>>(`${SPLICE_PATH}/by-z-strand-id/${zStrandId}`, { page, size });
}
