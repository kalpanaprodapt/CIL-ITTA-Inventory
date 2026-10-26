import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { THRESHOLDPOLICY_PATH } from './types';
import type { ThresholdPolicy, ThresholdPolicyFilter, ThresholdPolicyInput, ThresholdPolicySummary, ThresholdPolicyUpdateInput } from './types';
import { toThresholdPolicyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchThresholdPolicys(
  filter: ThresholdPolicyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ThresholdPolicy>> {
  return http.get<Page<ThresholdPolicy>>(THRESHOLDPOLICY_PATH, { ...toThresholdPolicyQuery(filter), page, size, sort });
}

export function getThresholdPolicy(id: string): Promise<ThresholdPolicy> {
  return http.get<ThresholdPolicy>(`${THRESHOLDPOLICY_PATH}/${id}`);
}

export function getThresholdPolicyByCode(code: string): Promise<ThresholdPolicy> {
  return http.get<ThresholdPolicy>(`${THRESHOLDPOLICY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listThresholdPolicySummaries(status?: LifecycleStatus): Promise<ThresholdPolicySummary[]> {
  return http.get<ThresholdPolicySummary[]>(`${THRESHOLDPOLICY_PATH}/summaries`, { status });
}

export function createThresholdPolicy(input: ThresholdPolicyInput): Promise<ThresholdPolicy> {
  return http.post<ThresholdPolicy>(THRESHOLDPOLICY_PATH, input);
}

export function updateThresholdPolicy(id: string, input: ThresholdPolicyUpdateInput): Promise<ThresholdPolicy> {
  return http.put<ThresholdPolicy>(`${THRESHOLDPOLICY_PATH}/${id}`, input);
}

export function changeThresholdPolicyStatus(id: string, status: LifecycleStatus): Promise<ThresholdPolicy> {
  return http.patch<ThresholdPolicy>(`${THRESHOLDPOLICY_PATH}/${id}/status`, undefined, { status });
}

export function deleteThresholdPolicy(id: string): Promise<void> {
  return http.delete(`${THRESHOLDPOLICY_PATH}/${id}`);
}

export function bulkCreateThresholdPolicys(inputs: ThresholdPolicyInput[]): Promise<BulkResult<ThresholdPolicy>> {
  return http.post<BulkResult<ThresholdPolicy>>(`${THRESHOLDPOLICY_PATH}/bulk`, inputs);
}

export function getThresholdPolicyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${THRESHOLDPOLICY_PATH}/stats/status`);
}

export function exportThresholdPolicysCsv(filter: ThresholdPolicyFilter): Promise<Blob> {
  return http.blob(`${THRESHOLDPOLICY_PATH}/export`, toThresholdPolicyQuery(filter));
}
