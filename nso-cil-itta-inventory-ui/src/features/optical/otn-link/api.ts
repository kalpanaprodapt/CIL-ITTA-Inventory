import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { OTNLINK_PATH } from './types';
import type { OtnLink, OtnLinkFilter, OtnLinkInput, OtnLinkSummary, OtnLinkUpdateInput } from './types';
import { toOtnLinkQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchOtnLinks(
  filter: OtnLinkFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<OtnLink>> {
  return http.get<Page<OtnLink>>(OTNLINK_PATH, { ...toOtnLinkQuery(filter), page, size, sort });
}

export function getOtnLink(id: string): Promise<OtnLink> {
  return http.get<OtnLink>(`${OTNLINK_PATH}/${id}`);
}

export function getOtnLinkByCode(code: string): Promise<OtnLink> {
  return http.get<OtnLink>(`${OTNLINK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listOtnLinkSummaries(status?: LifecycleStatus): Promise<OtnLinkSummary[]> {
  return http.get<OtnLinkSummary[]>(`${OTNLINK_PATH}/summaries`, { status });
}

export function createOtnLink(input: OtnLinkInput): Promise<OtnLink> {
  return http.post<OtnLink>(OTNLINK_PATH, input);
}

export function updateOtnLink(id: string, input: OtnLinkUpdateInput): Promise<OtnLink> {
  return http.put<OtnLink>(`${OTNLINK_PATH}/${id}`, input);
}

export function changeOtnLinkStatus(id: string, status: LifecycleStatus): Promise<OtnLink> {
  return http.patch<OtnLink>(`${OTNLINK_PATH}/${id}/status`, undefined, { status });
}

export function deleteOtnLink(id: string): Promise<void> {
  return http.delete(`${OTNLINK_PATH}/${id}`);
}

export function bulkCreateOtnLinks(inputs: OtnLinkInput[]): Promise<BulkResult<OtnLink>> {
  return http.post<BulkResult<OtnLink>>(`${OTNLINK_PATH}/bulk`, inputs);
}

export function getOtnLinkStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${OTNLINK_PATH}/stats/status`);
}

export function exportOtnLinksCsv(filter: OtnLinkFilter): Promise<Blob> {
  return http.blob(`${OTNLINK_PATH}/export`, toOtnLinkQuery(filter));
}

export function listOtnLinkByANodeId(aNodeId: string, page = 0, size = 20): Promise<Page<OtnLink>> {
  return http.get<Page<OtnLink>>(`${OTNLINK_PATH}/by-a-node-id/${aNodeId}`, { page, size });
}

export function listOtnLinkByZNodeId(zNodeId: string, page = 0, size = 20): Promise<Page<OtnLink>> {
  return http.get<Page<OtnLink>>(`${OTNLINK_PATH}/by-z-node-id/${zNodeId}`, { page, size });
}
