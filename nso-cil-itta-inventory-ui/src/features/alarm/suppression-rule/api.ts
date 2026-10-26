import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SUPPRESSIONRULE_PATH } from './types';
import type { SuppressionRule, SuppressionRuleFilter, SuppressionRuleInput, SuppressionRuleSummary, SuppressionRuleUpdateInput } from './types';
import { toSuppressionRuleQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSuppressionRules(
  filter: SuppressionRuleFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SuppressionRule>> {
  return http.get<Page<SuppressionRule>>(SUPPRESSIONRULE_PATH, { ...toSuppressionRuleQuery(filter), page, size, sort });
}

export function getSuppressionRule(id: string): Promise<SuppressionRule> {
  return http.get<SuppressionRule>(`${SUPPRESSIONRULE_PATH}/${id}`);
}

export function getSuppressionRuleByCode(code: string): Promise<SuppressionRule> {
  return http.get<SuppressionRule>(`${SUPPRESSIONRULE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSuppressionRuleSummaries(status?: LifecycleStatus): Promise<SuppressionRuleSummary[]> {
  return http.get<SuppressionRuleSummary[]>(`${SUPPRESSIONRULE_PATH}/summaries`, { status });
}

export function createSuppressionRule(input: SuppressionRuleInput): Promise<SuppressionRule> {
  return http.post<SuppressionRule>(SUPPRESSIONRULE_PATH, input);
}

export function updateSuppressionRule(id: string, input: SuppressionRuleUpdateInput): Promise<SuppressionRule> {
  return http.put<SuppressionRule>(`${SUPPRESSIONRULE_PATH}/${id}`, input);
}

export function changeSuppressionRuleStatus(id: string, status: LifecycleStatus): Promise<SuppressionRule> {
  return http.patch<SuppressionRule>(`${SUPPRESSIONRULE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSuppressionRule(id: string): Promise<void> {
  return http.delete(`${SUPPRESSIONRULE_PATH}/${id}`);
}

export function bulkCreateSuppressionRules(inputs: SuppressionRuleInput[]): Promise<BulkResult<SuppressionRule>> {
  return http.post<BulkResult<SuppressionRule>>(`${SUPPRESSIONRULE_PATH}/bulk`, inputs);
}

export function getSuppressionRuleStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SUPPRESSIONRULE_PATH}/stats/status`);
}

export function exportSuppressionRulesCsv(filter: SuppressionRuleFilter): Promise<Blob> {
  return http.blob(`${SUPPRESSIONRULE_PATH}/export`, toSuppressionRuleQuery(filter));
}
