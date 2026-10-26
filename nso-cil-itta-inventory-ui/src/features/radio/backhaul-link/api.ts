import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { BACKHAULLINK_PATH } from './types';
import type { BackhaulLink, BackhaulLinkFilter, BackhaulLinkInput, BackhaulLinkSummary, BackhaulLinkUpdateInput } from './types';
import { toBackhaulLinkQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchBackhaulLinks(
  filter: BackhaulLinkFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<BackhaulLink>> {
  return http.get<Page<BackhaulLink>>(BACKHAULLINK_PATH, { ...toBackhaulLinkQuery(filter), page, size, sort });
}

export function getBackhaulLink(id: string): Promise<BackhaulLink> {
  return http.get<BackhaulLink>(`${BACKHAULLINK_PATH}/${id}`);
}

export function getBackhaulLinkByCode(code: string): Promise<BackhaulLink> {
  return http.get<BackhaulLink>(`${BACKHAULLINK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listBackhaulLinkSummaries(status?: LifecycleStatus): Promise<BackhaulLinkSummary[]> {
  return http.get<BackhaulLinkSummary[]>(`${BACKHAULLINK_PATH}/summaries`, { status });
}

export function createBackhaulLink(input: BackhaulLinkInput): Promise<BackhaulLink> {
  return http.post<BackhaulLink>(BACKHAULLINK_PATH, input);
}

export function updateBackhaulLink(id: string, input: BackhaulLinkUpdateInput): Promise<BackhaulLink> {
  return http.put<BackhaulLink>(`${BACKHAULLINK_PATH}/${id}`, input);
}

export function changeBackhaulLinkStatus(id: string, status: LifecycleStatus): Promise<BackhaulLink> {
  return http.patch<BackhaulLink>(`${BACKHAULLINK_PATH}/${id}/status`, undefined, { status });
}

export function deleteBackhaulLink(id: string): Promise<void> {
  return http.delete(`${BACKHAULLINK_PATH}/${id}`);
}

export function bulkCreateBackhaulLinks(inputs: BackhaulLinkInput[]): Promise<BulkResult<BackhaulLink>> {
  return http.post<BulkResult<BackhaulLink>>(`${BACKHAULLINK_PATH}/bulk`, inputs);
}

export function getBackhaulLinkStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${BACKHAULLINK_PATH}/stats/status`);
}

export function exportBackhaulLinksCsv(filter: BackhaulLinkFilter): Promise<Blob> {
  return http.blob(`${BACKHAULLINK_PATH}/export`, toBackhaulLinkQuery(filter));
}

export function listBackhaulLinkByBaseStationId(baseStationId: string, page = 0, size = 20): Promise<Page<BackhaulLink>> {
  return http.get<Page<BackhaulLink>>(`${BACKHAULLINK_PATH}/by-base-station-id/${baseStationId}`, { page, size });
}
