import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { POLICYDOCUMENT_PATH } from './types';
import type { PolicyDocument, PolicyDocumentFilter, PolicyDocumentInput, PolicyDocumentSummary, PolicyDocumentUpdateInput } from './types';
import { toPolicyDocumentQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPolicyDocuments(
  filter: PolicyDocumentFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PolicyDocument>> {
  return http.get<Page<PolicyDocument>>(POLICYDOCUMENT_PATH, { ...toPolicyDocumentQuery(filter), page, size, sort });
}

export function getPolicyDocument(id: string): Promise<PolicyDocument> {
  return http.get<PolicyDocument>(`${POLICYDOCUMENT_PATH}/${id}`);
}

export function getPolicyDocumentByCode(code: string): Promise<PolicyDocument> {
  return http.get<PolicyDocument>(`${POLICYDOCUMENT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPolicyDocumentSummaries(status?: LifecycleStatus): Promise<PolicyDocumentSummary[]> {
  return http.get<PolicyDocumentSummary[]>(`${POLICYDOCUMENT_PATH}/summaries`, { status });
}

export function createPolicyDocument(input: PolicyDocumentInput): Promise<PolicyDocument> {
  return http.post<PolicyDocument>(POLICYDOCUMENT_PATH, input);
}

export function updatePolicyDocument(id: string, input: PolicyDocumentUpdateInput): Promise<PolicyDocument> {
  return http.put<PolicyDocument>(`${POLICYDOCUMENT_PATH}/${id}`, input);
}

export function changePolicyDocumentStatus(id: string, status: LifecycleStatus): Promise<PolicyDocument> {
  return http.patch<PolicyDocument>(`${POLICYDOCUMENT_PATH}/${id}/status`, undefined, { status });
}

export function deletePolicyDocument(id: string): Promise<void> {
  return http.delete(`${POLICYDOCUMENT_PATH}/${id}`);
}

export function bulkCreatePolicyDocuments(inputs: PolicyDocumentInput[]): Promise<BulkResult<PolicyDocument>> {
  return http.post<BulkResult<PolicyDocument>>(`${POLICYDOCUMENT_PATH}/bulk`, inputs);
}

export function getPolicyDocumentStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${POLICYDOCUMENT_PATH}/stats/status`);
}

export function exportPolicyDocumentsCsv(filter: PolicyDocumentFilter): Promise<Blob> {
  return http.blob(`${POLICYDOCUMENT_PATH}/export`, toPolicyDocumentQuery(filter));
}
