import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { UTILIZATIONSAMPLE_PATH } from './types';
import type { UtilizationSample, UtilizationSampleFilter, UtilizationSampleInput, UtilizationSampleSummary, UtilizationSampleUpdateInput } from './types';
import { toUtilizationSampleQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchUtilizationSamples(
  filter: UtilizationSampleFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<UtilizationSample>> {
  return http.get<Page<UtilizationSample>>(UTILIZATIONSAMPLE_PATH, { ...toUtilizationSampleQuery(filter), page, size, sort });
}

export function getUtilizationSample(id: string): Promise<UtilizationSample> {
  return http.get<UtilizationSample>(`${UTILIZATIONSAMPLE_PATH}/${id}`);
}

export function getUtilizationSampleByCode(code: string): Promise<UtilizationSample> {
  return http.get<UtilizationSample>(`${UTILIZATIONSAMPLE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listUtilizationSampleSummaries(status?: LifecycleStatus): Promise<UtilizationSampleSummary[]> {
  return http.get<UtilizationSampleSummary[]>(`${UTILIZATIONSAMPLE_PATH}/summaries`, { status });
}

export function createUtilizationSample(input: UtilizationSampleInput): Promise<UtilizationSample> {
  return http.post<UtilizationSample>(UTILIZATIONSAMPLE_PATH, input);
}

export function updateUtilizationSample(id: string, input: UtilizationSampleUpdateInput): Promise<UtilizationSample> {
  return http.put<UtilizationSample>(`${UTILIZATIONSAMPLE_PATH}/${id}`, input);
}

export function changeUtilizationSampleStatus(id: string, status: LifecycleStatus): Promise<UtilizationSample> {
  return http.patch<UtilizationSample>(`${UTILIZATIONSAMPLE_PATH}/${id}/status`, undefined, { status });
}

export function deleteUtilizationSample(id: string): Promise<void> {
  return http.delete(`${UTILIZATIONSAMPLE_PATH}/${id}`);
}

export function bulkCreateUtilizationSamples(inputs: UtilizationSampleInput[]): Promise<BulkResult<UtilizationSample>> {
  return http.post<BulkResult<UtilizationSample>>(`${UTILIZATIONSAMPLE_PATH}/bulk`, inputs);
}

export function getUtilizationSampleStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${UTILIZATIONSAMPLE_PATH}/stats/status`);
}

export function exportUtilizationSamplesCsv(filter: UtilizationSampleFilter): Promise<Blob> {
  return http.blob(`${UTILIZATIONSAMPLE_PATH}/export`, toUtilizationSampleQuery(filter));
}
