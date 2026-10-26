import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { FANTRAY_PATH } from './types';
import type { FanTray, FanTrayFilter, FanTrayInput, FanTraySummary, FanTrayUpdateInput } from './types';
import { toFanTrayQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchFanTrays(
  filter: FanTrayFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<FanTray>> {
  return http.get<Page<FanTray>>(FANTRAY_PATH, { ...toFanTrayQuery(filter), page, size, sort });
}

export function getFanTray(id: string): Promise<FanTray> {
  return http.get<FanTray>(`${FANTRAY_PATH}/${id}`);
}

export function getFanTrayByCode(code: string): Promise<FanTray> {
  return http.get<FanTray>(`${FANTRAY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listFanTraySummaries(status?: LifecycleStatus): Promise<FanTraySummary[]> {
  return http.get<FanTraySummary[]>(`${FANTRAY_PATH}/summaries`, { status });
}

export function createFanTray(input: FanTrayInput): Promise<FanTray> {
  return http.post<FanTray>(FANTRAY_PATH, input);
}

export function updateFanTray(id: string, input: FanTrayUpdateInput): Promise<FanTray> {
  return http.put<FanTray>(`${FANTRAY_PATH}/${id}`, input);
}

export function changeFanTrayStatus(id: string, status: LifecycleStatus): Promise<FanTray> {
  return http.patch<FanTray>(`${FANTRAY_PATH}/${id}/status`, undefined, { status });
}

export function deleteFanTray(id: string): Promise<void> {
  return http.delete(`${FANTRAY_PATH}/${id}`);
}

export function bulkCreateFanTrays(inputs: FanTrayInput[]): Promise<BulkResult<FanTray>> {
  return http.post<BulkResult<FanTray>>(`${FANTRAY_PATH}/bulk`, inputs);
}

export function getFanTrayStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${FANTRAY_PATH}/stats/status`);
}

export function exportFanTraysCsv(filter: FanTrayFilter): Promise<Blob> {
  return http.blob(`${FANTRAY_PATH}/export`, toFanTrayQuery(filter));
}

export function listFanTrayByChassisId(chassisId: string, page = 0, size = 20): Promise<Page<FanTray>> {
  return http.get<Page<FanTray>>(`${FANTRAY_PATH}/by-chassis-id/${chassisId}`, { page, size });
}
