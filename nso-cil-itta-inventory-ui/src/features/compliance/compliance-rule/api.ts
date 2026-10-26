import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { COMPLIANCERULE_PATH } from './types';
import type { ComplianceRule, ComplianceRuleFilter, ComplianceRuleInput, ComplianceRuleSummary, ComplianceRuleUpdateInput } from './types';
import { toComplianceRuleQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchComplianceRules(
  filter: ComplianceRuleFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ComplianceRule>> {
  return http.get<Page<ComplianceRule>>(COMPLIANCERULE_PATH, { ...toComplianceRuleQuery(filter), page, size, sort });
}

export function getComplianceRule(id: string): Promise<ComplianceRule> {
  return http.get<ComplianceRule>(`${COMPLIANCERULE_PATH}/${id}`);
}

export function getComplianceRuleByCode(code: string): Promise<ComplianceRule> {
  return http.get<ComplianceRule>(`${COMPLIANCERULE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listComplianceRuleSummaries(status?: LifecycleStatus): Promise<ComplianceRuleSummary[]> {
  return http.get<ComplianceRuleSummary[]>(`${COMPLIANCERULE_PATH}/summaries`, { status });
}

export function createComplianceRule(input: ComplianceRuleInput): Promise<ComplianceRule> {
  return http.post<ComplianceRule>(COMPLIANCERULE_PATH, input);
}

export function updateComplianceRule(id: string, input: ComplianceRuleUpdateInput): Promise<ComplianceRule> {
  return http.put<ComplianceRule>(`${COMPLIANCERULE_PATH}/${id}`, input);
}

export function changeComplianceRuleStatus(id: string, status: LifecycleStatus): Promise<ComplianceRule> {
  return http.patch<ComplianceRule>(`${COMPLIANCERULE_PATH}/${id}/status`, undefined, { status });
}

export function deleteComplianceRule(id: string): Promise<void> {
  return http.delete(`${COMPLIANCERULE_PATH}/${id}`);
}

export function bulkCreateComplianceRules(inputs: ComplianceRuleInput[]): Promise<BulkResult<ComplianceRule>> {
  return http.post<BulkResult<ComplianceRule>>(`${COMPLIANCERULE_PATH}/bulk`, inputs);
}

export function getComplianceRuleStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${COMPLIANCERULE_PATH}/stats/status`);
}

export function exportComplianceRulesCsv(filter: ComplianceRuleFilter): Promise<Blob> {
  return http.blob(`${COMPLIANCERULE_PATH}/export`, toComplianceRuleQuery(filter));
}
