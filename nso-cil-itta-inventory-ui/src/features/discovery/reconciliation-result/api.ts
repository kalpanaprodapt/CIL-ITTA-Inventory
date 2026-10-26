import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RECONCILIATIONRESULT_PATH } from './types';
import type { ReconciliationResult, ReconciliationResultFilter, ReconciliationResultInput, ReconciliationResultSummary, ReconciliationResultUpdateInput } from './types';
import { toReconciliationResultQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchReconciliationResults(
  filter: ReconciliationResultFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ReconciliationResult>> {
  return http.get<Page<ReconciliationResult>>(RECONCILIATIONRESULT_PATH, { ...toReconciliationResultQuery(filter), page, size, sort });
}

export function getReconciliationResult(id: string): Promise<ReconciliationResult> {
  return http.get<ReconciliationResult>(`${RECONCILIATIONRESULT_PATH}/${id}`);
}

export function getReconciliationResultByCode(code: string): Promise<ReconciliationResult> {
  return http.get<ReconciliationResult>(`${RECONCILIATIONRESULT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listReconciliationResultSummaries(status?: LifecycleStatus): Promise<ReconciliationResultSummary[]> {
  return http.get<ReconciliationResultSummary[]>(`${RECONCILIATIONRESULT_PATH}/summaries`, { status });
}

export function createReconciliationResult(input: ReconciliationResultInput): Promise<ReconciliationResult> {
  return http.post<ReconciliationResult>(RECONCILIATIONRESULT_PATH, input);
}

export function updateReconciliationResult(id: string, input: ReconciliationResultUpdateInput): Promise<ReconciliationResult> {
  return http.put<ReconciliationResult>(`${RECONCILIATIONRESULT_PATH}/${id}`, input);
}

export function changeReconciliationResultStatus(id: string, status: LifecycleStatus): Promise<ReconciliationResult> {
  return http.patch<ReconciliationResult>(`${RECONCILIATIONRESULT_PATH}/${id}/status`, undefined, { status });
}

export function deleteReconciliationResult(id: string): Promise<void> {
  return http.delete(`${RECONCILIATIONRESULT_PATH}/${id}`);
}

export function bulkCreateReconciliationResults(inputs: ReconciliationResultInput[]): Promise<BulkResult<ReconciliationResult>> {
  return http.post<BulkResult<ReconciliationResult>>(`${RECONCILIATIONRESULT_PATH}/bulk`, inputs);
}

export function getReconciliationResultStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RECONCILIATIONRESULT_PATH}/stats/status`);
}

export function exportReconciliationResultsCsv(filter: ReconciliationResultFilter): Promise<Blob> {
  return http.blob(`${RECONCILIATIONRESULT_PATH}/export`, toReconciliationResultQuery(filter));
}

export function listReconciliationResultByJobId(jobId: string, page = 0, size = 20): Promise<Page<ReconciliationResult>> {
  return http.get<Page<ReconciliationResult>>(`${RECONCILIATIONRESULT_PATH}/by-job-id/${jobId}`, { page, size });
}

export function listReconciliationResultByRuleId(ruleId: string, page = 0, size = 20): Promise<Page<ReconciliationResult>> {
  return http.get<Page<ReconciliationResult>>(`${RECONCILIATIONRESULT_PATH}/by-rule-id/${ruleId}`, { page, size });
}
