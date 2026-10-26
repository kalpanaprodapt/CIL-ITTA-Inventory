import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TICKETLINK_PATH } from './types';
import type { TicketLink, TicketLinkFilter, TicketLinkInput, TicketLinkSummary, TicketLinkUpdateInput } from './types';
import { toTicketLinkQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTicketLinks(
  filter: TicketLinkFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<TicketLink>> {
  return http.get<Page<TicketLink>>(TICKETLINK_PATH, { ...toTicketLinkQuery(filter), page, size, sort });
}

export function getTicketLink(id: string): Promise<TicketLink> {
  return http.get<TicketLink>(`${TICKETLINK_PATH}/${id}`);
}

export function getTicketLinkByCode(code: string): Promise<TicketLink> {
  return http.get<TicketLink>(`${TICKETLINK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTicketLinkSummaries(status?: LifecycleStatus): Promise<TicketLinkSummary[]> {
  return http.get<TicketLinkSummary[]>(`${TICKETLINK_PATH}/summaries`, { status });
}

export function createTicketLink(input: TicketLinkInput): Promise<TicketLink> {
  return http.post<TicketLink>(TICKETLINK_PATH, input);
}

export function updateTicketLink(id: string, input: TicketLinkUpdateInput): Promise<TicketLink> {
  return http.put<TicketLink>(`${TICKETLINK_PATH}/${id}`, input);
}

export function changeTicketLinkStatus(id: string, status: LifecycleStatus): Promise<TicketLink> {
  return http.patch<TicketLink>(`${TICKETLINK_PATH}/${id}/status`, undefined, { status });
}

export function deleteTicketLink(id: string): Promise<void> {
  return http.delete(`${TICKETLINK_PATH}/${id}`);
}

export function bulkCreateTicketLinks(inputs: TicketLinkInput[]): Promise<BulkResult<TicketLink>> {
  return http.post<BulkResult<TicketLink>>(`${TICKETLINK_PATH}/bulk`, inputs);
}

export function getTicketLinkStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TICKETLINK_PATH}/stats/status`);
}

export function exportTicketLinksCsv(filter: TicketLinkFilter): Promise<Blob> {
  return http.blob(`${TICKETLINK_PATH}/export`, toTicketLinkQuery(filter));
}

export function listTicketLinkByAlarmId(alarmId: string, page = 0, size = 20): Promise<Page<TicketLink>> {
  return http.get<Page<TicketLink>>(`${TICKETLINK_PATH}/by-alarm-id/${alarmId}`, { page, size });
}
