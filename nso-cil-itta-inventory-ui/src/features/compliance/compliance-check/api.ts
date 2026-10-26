import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { COMPLIANCECHECK_PATH } from './types';
import type { ComplianceCheck, ComplianceCheckFilter, ComplianceCheckInput, ComplianceCheckSummary, ComplianceCheckUpdateInput } from './types';
import { toComplianceCheckQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchComplianceChecks(
  filter: ComplianceCheckFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ComplianceCheck>> {
  return http.get<Page<ComplianceCheck>>(COMPLIANCECHECK_PATH, { ...toComplianceCheckQuery(filter), page, size, sort });
}

export function getComplianceCheck(id: string): Promise<ComplianceCheck> {
  return http.get<ComplianceCheck>(`${COMPLIANCECHECK_PATH}/${id}`);
}

export function getComplianceCheckByCode(code: string): Promise<ComplianceCheck> {
  return http.get<ComplianceCheck>(`${COMPLIANCECHECK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listComplianceCheckSummaries(status?: LifecycleStatus): Promise<ComplianceCheckSummary[]> {
  return http.get<ComplianceCheckSummary[]>(`${COMPLIANCECHECK_PATH}/summaries`, { status });
}

export function createComplianceCheck(input: ComplianceCheckInput): Promise<ComplianceCheck> {
  return http.post<ComplianceCheck>(COMPLIANCECHECK_PATH, input);
}

export function updateComplianceCheck(id: string, input: ComplianceCheckUpdateInput): Promise<ComplianceCheck> {
  return http.put<ComplianceCheck>(`${COMPLIANCECHECK_PATH}/${id}`, input);
}

export function changeComplianceCheckStatus(id: string, status: LifecycleStatus): Promise<ComplianceCheck> {
  return http.patch<ComplianceCheck>(`${COMPLIANCECHECK_PATH}/${id}/status`, undefined, { status });
}

export function deleteComplianceCheck(id: string): Promise<void> {
  return http.delete(`${COMPLIANCECHECK_PATH}/${id}`);
}

export function bulkCreateComplianceChecks(inputs: ComplianceCheckInput[]): Promise<BulkResult<ComplianceCheck>> {
  return http.post<BulkResult<ComplianceCheck>>(`${COMPLIANCECHECK_PATH}/bulk`, inputs);
}

export function getComplianceCheckStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${COMPLIANCECHECK_PATH}/stats/status`);
}

export function exportComplianceChecksCsv(filter: ComplianceCheckFilter): Promise<Blob> {
  return http.blob(`${COMPLIANCECHECK_PATH}/export`, toComplianceCheckQuery(filter));
}

export function listComplianceCheckByRuleId(ruleId: string, page = 0, size = 20): Promise<Page<ComplianceCheck>> {
  return http.get<Page<ComplianceCheck>>(`${COMPLIANCECHECK_PATH}/by-rule-id/${ruleId}`, { page, size });
}
