import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RECONCILIATIONRULE_PATH } from './types';
import type { ReconciliationRule, ReconciliationRuleFilter, ReconciliationRuleInput, ReconciliationRuleSummary, ReconciliationRuleUpdateInput } from './types';
import { toReconciliationRuleQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchReconciliationRules(
  filter: ReconciliationRuleFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ReconciliationRule>> {
  return http.get<Page<ReconciliationRule>>(RECONCILIATIONRULE_PATH, { ...toReconciliationRuleQuery(filter), page, size, sort });
}

export function getReconciliationRule(id: string): Promise<ReconciliationRule> {
  return http.get<ReconciliationRule>(`${RECONCILIATIONRULE_PATH}/${id}`);
}

export function getReconciliationRuleByCode(code: string): Promise<ReconciliationRule> {
  return http.get<ReconciliationRule>(`${RECONCILIATIONRULE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listReconciliationRuleSummaries(status?: LifecycleStatus): Promise<ReconciliationRuleSummary[]> {
  return http.get<ReconciliationRuleSummary[]>(`${RECONCILIATIONRULE_PATH}/summaries`, { status });
}

export function createReconciliationRule(input: ReconciliationRuleInput): Promise<ReconciliationRule> {
  return http.post<ReconciliationRule>(RECONCILIATIONRULE_PATH, input);
}

export function updateReconciliationRule(id: string, input: ReconciliationRuleUpdateInput): Promise<ReconciliationRule> {
  return http.put<ReconciliationRule>(`${RECONCILIATIONRULE_PATH}/${id}`, input);
}

export function changeReconciliationRuleStatus(id: string, status: LifecycleStatus): Promise<ReconciliationRule> {
  return http.patch<ReconciliationRule>(`${RECONCILIATIONRULE_PATH}/${id}/status`, undefined, { status });
}

export function deleteReconciliationRule(id: string): Promise<void> {
  return http.delete(`${RECONCILIATIONRULE_PATH}/${id}`);
}

export function bulkCreateReconciliationRules(inputs: ReconciliationRuleInput[]): Promise<BulkResult<ReconciliationRule>> {
  return http.post<BulkResult<ReconciliationRule>>(`${RECONCILIATIONRULE_PATH}/bulk`, inputs);
}

export function getReconciliationRuleStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RECONCILIATIONRULE_PATH}/stats/status`);
}

export function exportReconciliationRulesCsv(filter: ReconciliationRuleFilter): Promise<Blob> {
  return http.blob(`${RECONCILIATIONRULE_PATH}/export`, toReconciliationRuleQuery(filter));
}
