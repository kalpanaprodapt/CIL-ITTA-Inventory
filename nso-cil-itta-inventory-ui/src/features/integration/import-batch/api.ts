import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { IMPORTBATCH_PATH } from './types';
import type { ImportBatch, ImportBatchFilter, ImportBatchInput, ImportBatchSummary, ImportBatchUpdateInput } from './types';
import { toImportBatchQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchImportBatchs(
  filter: ImportBatchFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ImportBatch>> {
  return http.get<Page<ImportBatch>>(IMPORTBATCH_PATH, { ...toImportBatchQuery(filter), page, size, sort });
}

export function getImportBatch(id: string): Promise<ImportBatch> {
  return http.get<ImportBatch>(`${IMPORTBATCH_PATH}/${id}`);
}

export function getImportBatchByCode(code: string): Promise<ImportBatch> {
  return http.get<ImportBatch>(`${IMPORTBATCH_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listImportBatchSummaries(status?: LifecycleStatus): Promise<ImportBatchSummary[]> {
  return http.get<ImportBatchSummary[]>(`${IMPORTBATCH_PATH}/summaries`, { status });
}

export function createImportBatch(input: ImportBatchInput): Promise<ImportBatch> {
  return http.post<ImportBatch>(IMPORTBATCH_PATH, input);
}

export function updateImportBatch(id: string, input: ImportBatchUpdateInput): Promise<ImportBatch> {
  return http.put<ImportBatch>(`${IMPORTBATCH_PATH}/${id}`, input);
}

export function changeImportBatchStatus(id: string, status: LifecycleStatus): Promise<ImportBatch> {
  return http.patch<ImportBatch>(`${IMPORTBATCH_PATH}/${id}/status`, undefined, { status });
}

export function deleteImportBatch(id: string): Promise<void> {
  return http.delete(`${IMPORTBATCH_PATH}/${id}`);
}

export function bulkCreateImportBatchs(inputs: ImportBatchInput[]): Promise<BulkResult<ImportBatch>> {
  return http.post<BulkResult<ImportBatch>>(`${IMPORTBATCH_PATH}/bulk`, inputs);
}

export function getImportBatchStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${IMPORTBATCH_PATH}/stats/status`);
}

export function exportImportBatchsCsv(filter: ImportBatchFilter): Promise<Blob> {
  return http.blob(`${IMPORTBATCH_PATH}/export`, toImportBatchQuery(filter));
}
