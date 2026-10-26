import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { FIBERSTRAND_PATH } from './types';
import type { FiberStrand, FiberStrandFilter, FiberStrandInput, FiberStrandSummary, FiberStrandUpdateInput } from './types';
import { toFiberStrandQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchFiberStrands(
  filter: FiberStrandFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<FiberStrand>> {
  return http.get<Page<FiberStrand>>(FIBERSTRAND_PATH, { ...toFiberStrandQuery(filter), page, size, sort });
}

export function getFiberStrand(id: string): Promise<FiberStrand> {
  return http.get<FiberStrand>(`${FIBERSTRAND_PATH}/${id}`);
}

export function getFiberStrandByCode(code: string): Promise<FiberStrand> {
  return http.get<FiberStrand>(`${FIBERSTRAND_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listFiberStrandSummaries(status?: LifecycleStatus): Promise<FiberStrandSummary[]> {
  return http.get<FiberStrandSummary[]>(`${FIBERSTRAND_PATH}/summaries`, { status });
}

export function createFiberStrand(input: FiberStrandInput): Promise<FiberStrand> {
  return http.post<FiberStrand>(FIBERSTRAND_PATH, input);
}

export function updateFiberStrand(id: string, input: FiberStrandUpdateInput): Promise<FiberStrand> {
  return http.put<FiberStrand>(`${FIBERSTRAND_PATH}/${id}`, input);
}

export function changeFiberStrandStatus(id: string, status: LifecycleStatus): Promise<FiberStrand> {
  return http.patch<FiberStrand>(`${FIBERSTRAND_PATH}/${id}/status`, undefined, { status });
}

export function deleteFiberStrand(id: string): Promise<void> {
  return http.delete(`${FIBERSTRAND_PATH}/${id}`);
}

export function bulkCreateFiberStrands(inputs: FiberStrandInput[]): Promise<BulkResult<FiberStrand>> {
  return http.post<BulkResult<FiberStrand>>(`${FIBERSTRAND_PATH}/bulk`, inputs);
}

export function getFiberStrandStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${FIBERSTRAND_PATH}/stats/status`);
}

export function exportFiberStrandsCsv(filter: FiberStrandFilter): Promise<Blob> {
  return http.blob(`${FIBERSTRAND_PATH}/export`, toFiberStrandQuery(filter));
}

export function listFiberStrandByCableId(cableId: string, page = 0, size = 20): Promise<Page<FiberStrand>> {
  return http.get<Page<FiberStrand>>(`${FIBERSTRAND_PATH}/by-cable-id/${cableId}`, { page, size });
}
