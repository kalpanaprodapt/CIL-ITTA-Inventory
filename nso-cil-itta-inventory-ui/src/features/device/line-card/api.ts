import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { LINECARD_PATH } from './types';
import type { LineCard, LineCardFilter, LineCardInput, LineCardSummary, LineCardUpdateInput } from './types';
import { toLineCardQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchLineCards(
  filter: LineCardFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<LineCard>> {
  return http.get<Page<LineCard>>(LINECARD_PATH, { ...toLineCardQuery(filter), page, size, sort });
}

export function getLineCard(id: string): Promise<LineCard> {
  return http.get<LineCard>(`${LINECARD_PATH}/${id}`);
}

export function getLineCardByCode(code: string): Promise<LineCard> {
  return http.get<LineCard>(`${LINECARD_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listLineCardSummaries(status?: LifecycleStatus): Promise<LineCardSummary[]> {
  return http.get<LineCardSummary[]>(`${LINECARD_PATH}/summaries`, { status });
}

export function createLineCard(input: LineCardInput): Promise<LineCard> {
  return http.post<LineCard>(LINECARD_PATH, input);
}

export function updateLineCard(id: string, input: LineCardUpdateInput): Promise<LineCard> {
  return http.put<LineCard>(`${LINECARD_PATH}/${id}`, input);
}

export function changeLineCardStatus(id: string, status: LifecycleStatus): Promise<LineCard> {
  return http.patch<LineCard>(`${LINECARD_PATH}/${id}/status`, undefined, { status });
}

export function deleteLineCard(id: string): Promise<void> {
  return http.delete(`${LINECARD_PATH}/${id}`);
}

export function bulkCreateLineCards(inputs: LineCardInput[]): Promise<BulkResult<LineCard>> {
  return http.post<BulkResult<LineCard>>(`${LINECARD_PATH}/bulk`, inputs);
}

export function getLineCardStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${LINECARD_PATH}/stats/status`);
}

export function exportLineCardsCsv(filter: LineCardFilter): Promise<Blob> {
  return http.blob(`${LINECARD_PATH}/export`, toLineCardQuery(filter));
}

export function listLineCardByChassisId(chassisId: string, page = 0, size = 20): Promise<Page<LineCard>> {
  return http.get<Page<LineCard>>(`${LINECARD_PATH}/by-chassis-id/${chassisId}`, { page, size });
}
