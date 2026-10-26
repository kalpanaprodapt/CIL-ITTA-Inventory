import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { BASESTATION_PATH } from './types';
import type { BaseStation, BaseStationFilter, BaseStationInput, BaseStationSummary, BaseStationUpdateInput } from './types';
import { toBaseStationQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchBaseStations(
  filter: BaseStationFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<BaseStation>> {
  return http.get<Page<BaseStation>>(BASESTATION_PATH, { ...toBaseStationQuery(filter), page, size, sort });
}

export function getBaseStation(id: string): Promise<BaseStation> {
  return http.get<BaseStation>(`${BASESTATION_PATH}/${id}`);
}

export function getBaseStationByCode(code: string): Promise<BaseStation> {
  return http.get<BaseStation>(`${BASESTATION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listBaseStationSummaries(status?: LifecycleStatus): Promise<BaseStationSummary[]> {
  return http.get<BaseStationSummary[]>(`${BASESTATION_PATH}/summaries`, { status });
}

export function createBaseStation(input: BaseStationInput): Promise<BaseStation> {
  return http.post<BaseStation>(BASESTATION_PATH, input);
}

export function updateBaseStation(id: string, input: BaseStationUpdateInput): Promise<BaseStation> {
  return http.put<BaseStation>(`${BASESTATION_PATH}/${id}`, input);
}

export function changeBaseStationStatus(id: string, status: LifecycleStatus): Promise<BaseStation> {
  return http.patch<BaseStation>(`${BASESTATION_PATH}/${id}/status`, undefined, { status });
}

export function deleteBaseStation(id: string): Promise<void> {
  return http.delete(`${BASESTATION_PATH}/${id}`);
}

export function bulkCreateBaseStations(inputs: BaseStationInput[]): Promise<BulkResult<BaseStation>> {
  return http.post<BulkResult<BaseStation>>(`${BASESTATION_PATH}/bulk`, inputs);
}

export function getBaseStationStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${BASESTATION_PATH}/stats/status`);
}

export function exportBaseStationsCsv(filter: BaseStationFilter): Promise<Blob> {
  return http.blob(`${BASESTATION_PATH}/export`, toBaseStationQuery(filter));
}

export function listBaseStationBySiteId(siteId: string, page = 0, size = 20): Promise<Page<BaseStation>> {
  return http.get<Page<BaseStation>>(`${BASESTATION_PATH}/by-site-id/${siteId}`, { page, size });
}
