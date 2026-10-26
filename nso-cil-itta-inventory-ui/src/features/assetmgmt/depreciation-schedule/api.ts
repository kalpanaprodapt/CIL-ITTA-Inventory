import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { DEPRECIATIONSCHEDULE_PATH } from './types';
import type { DepreciationSchedule, DepreciationScheduleFilter, DepreciationScheduleInput, DepreciationScheduleSummary, DepreciationScheduleUpdateInput } from './types';
import { toDepreciationScheduleQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchDepreciationSchedules(
  filter: DepreciationScheduleFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<DepreciationSchedule>> {
  return http.get<Page<DepreciationSchedule>>(DEPRECIATIONSCHEDULE_PATH, { ...toDepreciationScheduleQuery(filter), page, size, sort });
}

export function getDepreciationSchedule(id: string): Promise<DepreciationSchedule> {
  return http.get<DepreciationSchedule>(`${DEPRECIATIONSCHEDULE_PATH}/${id}`);
}

export function getDepreciationScheduleByCode(code: string): Promise<DepreciationSchedule> {
  return http.get<DepreciationSchedule>(`${DEPRECIATIONSCHEDULE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listDepreciationScheduleSummaries(status?: LifecycleStatus): Promise<DepreciationScheduleSummary[]> {
  return http.get<DepreciationScheduleSummary[]>(`${DEPRECIATIONSCHEDULE_PATH}/summaries`, { status });
}

export function createDepreciationSchedule(input: DepreciationScheduleInput): Promise<DepreciationSchedule> {
  return http.post<DepreciationSchedule>(DEPRECIATIONSCHEDULE_PATH, input);
}

export function updateDepreciationSchedule(id: string, input: DepreciationScheduleUpdateInput): Promise<DepreciationSchedule> {
  return http.put<DepreciationSchedule>(`${DEPRECIATIONSCHEDULE_PATH}/${id}`, input);
}

export function changeDepreciationScheduleStatus(id: string, status: LifecycleStatus): Promise<DepreciationSchedule> {
  return http.patch<DepreciationSchedule>(`${DEPRECIATIONSCHEDULE_PATH}/${id}/status`, undefined, { status });
}

export function deleteDepreciationSchedule(id: string): Promise<void> {
  return http.delete(`${DEPRECIATIONSCHEDULE_PATH}/${id}`);
}

export function bulkCreateDepreciationSchedules(inputs: DepreciationScheduleInput[]): Promise<BulkResult<DepreciationSchedule>> {
  return http.post<BulkResult<DepreciationSchedule>>(`${DEPRECIATIONSCHEDULE_PATH}/bulk`, inputs);
}

export function getDepreciationScheduleStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${DEPRECIATIONSCHEDULE_PATH}/stats/status`);
}

export function exportDepreciationSchedulesCsv(filter: DepreciationScheduleFilter): Promise<Blob> {
  return http.blob(`${DEPRECIATIONSCHEDULE_PATH}/export`, toDepreciationScheduleQuery(filter));
}

export function listDepreciationScheduleByAssetId(assetId: string, page = 0, size = 20): Promise<Page<DepreciationSchedule>> {
  return http.get<Page<DepreciationSchedule>>(`${DEPRECIATIONSCHEDULE_PATH}/by-asset-id/${assetId}`, { page, size });
}
