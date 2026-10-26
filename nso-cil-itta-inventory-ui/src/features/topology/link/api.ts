import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { LINK_PATH } from './types';
import type { Link, LinkFilter, LinkInput, LinkSummary, LinkUpdateInput } from './types';
import { toLinkQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchLinks(
  filter: LinkFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Link>> {
  return http.get<Page<Link>>(LINK_PATH, { ...toLinkQuery(filter), page, size, sort });
}

export function getLink(id: string): Promise<Link> {
  return http.get<Link>(`${LINK_PATH}/${id}`);
}

export function getLinkByCode(code: string): Promise<Link> {
  return http.get<Link>(`${LINK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listLinkSummaries(status?: LifecycleStatus): Promise<LinkSummary[]> {
  return http.get<LinkSummary[]>(`${LINK_PATH}/summaries`, { status });
}

export function createLink(input: LinkInput): Promise<Link> {
  return http.post<Link>(LINK_PATH, input);
}

export function updateLink(id: string, input: LinkUpdateInput): Promise<Link> {
  return http.put<Link>(`${LINK_PATH}/${id}`, input);
}

export function changeLinkStatus(id: string, status: LifecycleStatus): Promise<Link> {
  return http.patch<Link>(`${LINK_PATH}/${id}/status`, undefined, { status });
}

export function deleteLink(id: string): Promise<void> {
  return http.delete(`${LINK_PATH}/${id}`);
}

export function bulkCreateLinks(inputs: LinkInput[]): Promise<BulkResult<Link>> {
  return http.post<BulkResult<Link>>(`${LINK_PATH}/bulk`, inputs);
}

export function getLinkStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${LINK_PATH}/stats/status`);
}

export function exportLinksCsv(filter: LinkFilter): Promise<Blob> {
  return http.blob(`${LINK_PATH}/export`, toLinkQuery(filter));
}

export function listLinkByAInterfaceId(aInterfaceId: string, page = 0, size = 20): Promise<Page<Link>> {
  return http.get<Page<Link>>(`${LINK_PATH}/by-a-interface-id/${aInterfaceId}`, { page, size });
}

export function listLinkByZInterfaceId(zInterfaceId: string, page = 0, size = 20): Promise<Page<Link>> {
  return http.get<Page<Link>>(`${LINK_PATH}/by-z-interface-id/${zInterfaceId}`, { page, size });
}
