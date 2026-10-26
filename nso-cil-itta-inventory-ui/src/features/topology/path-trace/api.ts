import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PATHTRACE_PATH } from './types';
import type { PathTrace, PathTraceFilter, PathTraceInput, PathTraceSummary, PathTraceUpdateInput } from './types';
import { toPathTraceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPathTraces(
  filter: PathTraceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PathTrace>> {
  return http.get<Page<PathTrace>>(PATHTRACE_PATH, { ...toPathTraceQuery(filter), page, size, sort });
}

export function getPathTrace(id: string): Promise<PathTrace> {
  return http.get<PathTrace>(`${PATHTRACE_PATH}/${id}`);
}

export function getPathTraceByCode(code: string): Promise<PathTrace> {
  return http.get<PathTrace>(`${PATHTRACE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPathTraceSummaries(status?: LifecycleStatus): Promise<PathTraceSummary[]> {
  return http.get<PathTraceSummary[]>(`${PATHTRACE_PATH}/summaries`, { status });
}

export function createPathTrace(input: PathTraceInput): Promise<PathTrace> {
  return http.post<PathTrace>(PATHTRACE_PATH, input);
}

export function updatePathTrace(id: string, input: PathTraceUpdateInput): Promise<PathTrace> {
  return http.put<PathTrace>(`${PATHTRACE_PATH}/${id}`, input);
}

export function changePathTraceStatus(id: string, status: LifecycleStatus): Promise<PathTrace> {
  return http.patch<PathTrace>(`${PATHTRACE_PATH}/${id}/status`, undefined, { status });
}

export function deletePathTrace(id: string): Promise<void> {
  return http.delete(`${PATHTRACE_PATH}/${id}`);
}

export function bulkCreatePathTraces(inputs: PathTraceInput[]): Promise<BulkResult<PathTrace>> {
  return http.post<BulkResult<PathTrace>>(`${PATHTRACE_PATH}/bulk`, inputs);
}

export function getPathTraceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PATHTRACE_PATH}/stats/status`);
}

export function exportPathTracesCsv(filter: PathTraceFilter): Promise<Blob> {
  return http.blob(`${PATHTRACE_PATH}/export`, toPathTraceQuery(filter));
}
