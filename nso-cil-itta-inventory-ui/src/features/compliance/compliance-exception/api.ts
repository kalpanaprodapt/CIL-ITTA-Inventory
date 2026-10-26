import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { COMPLIANCEEXCEPTION_PATH } from './types';
import type { ComplianceException, ComplianceExceptionFilter, ComplianceExceptionInput, ComplianceExceptionSummary, ComplianceExceptionUpdateInput } from './types';
import { toComplianceExceptionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchComplianceExceptions(
  filter: ComplianceExceptionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ComplianceException>> {
  return http.get<Page<ComplianceException>>(COMPLIANCEEXCEPTION_PATH, { ...toComplianceExceptionQuery(filter), page, size, sort });
}

export function getComplianceException(id: string): Promise<ComplianceException> {
  return http.get<ComplianceException>(`${COMPLIANCEEXCEPTION_PATH}/${id}`);
}

export function getComplianceExceptionByCode(code: string): Promise<ComplianceException> {
  return http.get<ComplianceException>(`${COMPLIANCEEXCEPTION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listComplianceExceptionSummaries(status?: LifecycleStatus): Promise<ComplianceExceptionSummary[]> {
  return http.get<ComplianceExceptionSummary[]>(`${COMPLIANCEEXCEPTION_PATH}/summaries`, { status });
}

export function createComplianceException(input: ComplianceExceptionInput): Promise<ComplianceException> {
  return http.post<ComplianceException>(COMPLIANCEEXCEPTION_PATH, input);
}

export function updateComplianceException(id: string, input: ComplianceExceptionUpdateInput): Promise<ComplianceException> {
  return http.put<ComplianceException>(`${COMPLIANCEEXCEPTION_PATH}/${id}`, input);
}

export function changeComplianceExceptionStatus(id: string, status: LifecycleStatus): Promise<ComplianceException> {
  return http.patch<ComplianceException>(`${COMPLIANCEEXCEPTION_PATH}/${id}/status`, undefined, { status });
}

export function deleteComplianceException(id: string): Promise<void> {
  return http.delete(`${COMPLIANCEEXCEPTION_PATH}/${id}`);
}

export function bulkCreateComplianceExceptions(inputs: ComplianceExceptionInput[]): Promise<BulkResult<ComplianceException>> {
  return http.post<BulkResult<ComplianceException>>(`${COMPLIANCEEXCEPTION_PATH}/bulk`, inputs);
}

export function getComplianceExceptionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${COMPLIANCEEXCEPTION_PATH}/stats/status`);
}

export function exportComplianceExceptionsCsv(filter: ComplianceExceptionFilter): Promise<Blob> {
  return http.blob(`${COMPLIANCEEXCEPTION_PATH}/export`, toComplianceExceptionQuery(filter));
}

export function listComplianceExceptionByRuleId(ruleId: string, page = 0, size = 20): Promise<Page<ComplianceException>> {
  return http.get<Page<ComplianceException>>(`${COMPLIANCEEXCEPTION_PATH}/by-rule-id/${ruleId}`, { page, size });
}
