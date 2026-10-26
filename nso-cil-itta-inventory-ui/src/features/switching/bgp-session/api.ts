import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { BGPSESSION_PATH } from './types';
import type { BgpSession, BgpSessionFilter, BgpSessionInput, BgpSessionSummary, BgpSessionUpdateInput } from './types';
import { toBgpSessionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchBgpSessions(
  filter: BgpSessionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<BgpSession>> {
  return http.get<Page<BgpSession>>(BGPSESSION_PATH, { ...toBgpSessionQuery(filter), page, size, sort });
}

export function getBgpSession(id: string): Promise<BgpSession> {
  return http.get<BgpSession>(`${BGPSESSION_PATH}/${id}`);
}

export function getBgpSessionByCode(code: string): Promise<BgpSession> {
  return http.get<BgpSession>(`${BGPSESSION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listBgpSessionSummaries(status?: LifecycleStatus): Promise<BgpSessionSummary[]> {
  return http.get<BgpSessionSummary[]>(`${BGPSESSION_PATH}/summaries`, { status });
}

export function createBgpSession(input: BgpSessionInput): Promise<BgpSession> {
  return http.post<BgpSession>(BGPSESSION_PATH, input);
}

export function updateBgpSession(id: string, input: BgpSessionUpdateInput): Promise<BgpSession> {
  return http.put<BgpSession>(`${BGPSESSION_PATH}/${id}`, input);
}

export function changeBgpSessionStatus(id: string, status: LifecycleStatus): Promise<BgpSession> {
  return http.patch<BgpSession>(`${BGPSESSION_PATH}/${id}/status`, undefined, { status });
}

export function deleteBgpSession(id: string): Promise<void> {
  return http.delete(`${BGPSESSION_PATH}/${id}`);
}

export function bulkCreateBgpSessions(inputs: BgpSessionInput[]): Promise<BulkResult<BgpSession>> {
  return http.post<BulkResult<BgpSession>>(`${BGPSESSION_PATH}/bulk`, inputs);
}

export function getBgpSessionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${BGPSESSION_PATH}/stats/status`);
}

export function exportBgpSessionsCsv(filter: BgpSessionFilter): Promise<Blob> {
  return http.blob(`${BGPSESSION_PATH}/export`, toBgpSessionQuery(filter));
}

export function listBgpSessionByLocalDeviceId(localDeviceId: string, page = 0, size = 20): Promise<Page<BgpSession>> {
  return http.get<Page<BgpSession>>(`${BGPSESSION_PATH}/by-local-device-id/${localDeviceId}`, { page, size });
}
