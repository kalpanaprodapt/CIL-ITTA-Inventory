import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { POWERFEED_PATH } from './types';
import type { PowerFeed, PowerFeedFilter, PowerFeedInput, PowerFeedSummary, PowerFeedUpdateInput } from './types';
import { toPowerFeedQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPowerFeeds(
  filter: PowerFeedFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PowerFeed>> {
  return http.get<Page<PowerFeed>>(POWERFEED_PATH, { ...toPowerFeedQuery(filter), page, size, sort });
}

export function getPowerFeed(id: string): Promise<PowerFeed> {
  return http.get<PowerFeed>(`${POWERFEED_PATH}/${id}`);
}

export function getPowerFeedByCode(code: string): Promise<PowerFeed> {
  return http.get<PowerFeed>(`${POWERFEED_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPowerFeedSummaries(status?: LifecycleStatus): Promise<PowerFeedSummary[]> {
  return http.get<PowerFeedSummary[]>(`${POWERFEED_PATH}/summaries`, { status });
}

export function createPowerFeed(input: PowerFeedInput): Promise<PowerFeed> {
  return http.post<PowerFeed>(POWERFEED_PATH, input);
}

export function updatePowerFeed(id: string, input: PowerFeedUpdateInput): Promise<PowerFeed> {
  return http.put<PowerFeed>(`${POWERFEED_PATH}/${id}`, input);
}

export function changePowerFeedStatus(id: string, status: LifecycleStatus): Promise<PowerFeed> {
  return http.patch<PowerFeed>(`${POWERFEED_PATH}/${id}/status`, undefined, { status });
}

export function deletePowerFeed(id: string): Promise<void> {
  return http.delete(`${POWERFEED_PATH}/${id}`);
}

export function bulkCreatePowerFeeds(inputs: PowerFeedInput[]): Promise<BulkResult<PowerFeed>> {
  return http.post<BulkResult<PowerFeed>>(`${POWERFEED_PATH}/bulk`, inputs);
}

export function getPowerFeedStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${POWERFEED_PATH}/stats/status`);
}

export function exportPowerFeedsCsv(filter: PowerFeedFilter): Promise<Blob> {
  return http.blob(`${POWERFEED_PATH}/export`, toPowerFeedQuery(filter));
}

export function listPowerFeedByRoomId(roomId: string, page = 0, size = 20): Promise<Page<PowerFeed>> {
  return http.get<Page<PowerFeed>>(`${POWERFEED_PATH}/by-room-id/${roomId}`, { page, size });
}
