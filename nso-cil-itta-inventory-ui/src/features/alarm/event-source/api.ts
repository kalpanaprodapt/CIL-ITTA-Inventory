import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { EVENTSOURCE_PATH } from './types';
import type { EventSource, EventSourceFilter, EventSourceInput, EventSourceSummary, EventSourceUpdateInput } from './types';
import { toEventSourceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchEventSources(
  filter: EventSourceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<EventSource>> {
  return http.get<Page<EventSource>>(EVENTSOURCE_PATH, { ...toEventSourceQuery(filter), page, size, sort });
}

export function getEventSource(id: string): Promise<EventSource> {
  return http.get<EventSource>(`${EVENTSOURCE_PATH}/${id}`);
}

export function getEventSourceByCode(code: string): Promise<EventSource> {
  return http.get<EventSource>(`${EVENTSOURCE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listEventSourceSummaries(status?: LifecycleStatus): Promise<EventSourceSummary[]> {
  return http.get<EventSourceSummary[]>(`${EVENTSOURCE_PATH}/summaries`, { status });
}

export function createEventSource(input: EventSourceInput): Promise<EventSource> {
  return http.post<EventSource>(EVENTSOURCE_PATH, input);
}

export function updateEventSource(id: string, input: EventSourceUpdateInput): Promise<EventSource> {
  return http.put<EventSource>(`${EVENTSOURCE_PATH}/${id}`, input);
}

export function changeEventSourceStatus(id: string, status: LifecycleStatus): Promise<EventSource> {
  return http.patch<EventSource>(`${EVENTSOURCE_PATH}/${id}/status`, undefined, { status });
}

export function deleteEventSource(id: string): Promise<void> {
  return http.delete(`${EVENTSOURCE_PATH}/${id}`);
}

export function bulkCreateEventSources(inputs: EventSourceInput[]): Promise<BulkResult<EventSource>> {
  return http.post<BulkResult<EventSource>>(`${EVENTSOURCE_PATH}/bulk`, inputs);
}

export function getEventSourceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${EVENTSOURCE_PATH}/stats/status`);
}

export function exportEventSourcesCsv(filter: EventSourceFilter): Promise<Blob> {
  return http.blob(`${EVENTSOURCE_PATH}/export`, toEventSourceQuery(filter));
}
