import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CHANGEREQUEST_PATH } from './types';
import type { ChangeRequest, ChangeRequestFilter, ChangeRequestInput, ChangeRequestSummary, ChangeRequestUpdateInput } from './types';
import { toChangeRequestQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchChangeRequests(
  filter: ChangeRequestFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ChangeRequest>> {
  return http.get<Page<ChangeRequest>>(CHANGEREQUEST_PATH, { ...toChangeRequestQuery(filter), page, size, sort });
}

export function getChangeRequest(id: string): Promise<ChangeRequest> {
  return http.get<ChangeRequest>(`${CHANGEREQUEST_PATH}/${id}`);
}

export function getChangeRequestByCode(code: string): Promise<ChangeRequest> {
  return http.get<ChangeRequest>(`${CHANGEREQUEST_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listChangeRequestSummaries(status?: LifecycleStatus): Promise<ChangeRequestSummary[]> {
  return http.get<ChangeRequestSummary[]>(`${CHANGEREQUEST_PATH}/summaries`, { status });
}

export function createChangeRequest(input: ChangeRequestInput): Promise<ChangeRequest> {
  return http.post<ChangeRequest>(CHANGEREQUEST_PATH, input);
}

export function updateChangeRequest(id: string, input: ChangeRequestUpdateInput): Promise<ChangeRequest> {
  return http.put<ChangeRequest>(`${CHANGEREQUEST_PATH}/${id}`, input);
}

export function changeChangeRequestStatus(id: string, status: LifecycleStatus): Promise<ChangeRequest> {
  return http.patch<ChangeRequest>(`${CHANGEREQUEST_PATH}/${id}/status`, undefined, { status });
}

export function deleteChangeRequest(id: string): Promise<void> {
  return http.delete(`${CHANGEREQUEST_PATH}/${id}`);
}

export function bulkCreateChangeRequests(inputs: ChangeRequestInput[]): Promise<BulkResult<ChangeRequest>> {
  return http.post<BulkResult<ChangeRequest>>(`${CHANGEREQUEST_PATH}/bulk`, inputs);
}

export function getChangeRequestStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CHANGEREQUEST_PATH}/stats/status`);
}

export function exportChangeRequestsCsv(filter: ChangeRequestFilter): Promise<Blob> {
  return http.blob(`${CHANGEREQUEST_PATH}/export`, toChangeRequestQuery(filter));
}
