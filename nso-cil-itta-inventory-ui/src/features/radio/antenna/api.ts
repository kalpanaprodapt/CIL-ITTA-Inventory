import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ANTENNA_PATH } from './types';
import type { Antenna, AntennaFilter, AntennaInput, AntennaSummary, AntennaUpdateInput } from './types';
import { toAntennaQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAntennas(
  filter: AntennaFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Antenna>> {
  return http.get<Page<Antenna>>(ANTENNA_PATH, { ...toAntennaQuery(filter), page, size, sort });
}

export function getAntenna(id: string): Promise<Antenna> {
  return http.get<Antenna>(`${ANTENNA_PATH}/${id}`);
}

export function getAntennaByCode(code: string): Promise<Antenna> {
  return http.get<Antenna>(`${ANTENNA_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAntennaSummaries(status?: LifecycleStatus): Promise<AntennaSummary[]> {
  return http.get<AntennaSummary[]>(`${ANTENNA_PATH}/summaries`, { status });
}

export function createAntenna(input: AntennaInput): Promise<Antenna> {
  return http.post<Antenna>(ANTENNA_PATH, input);
}

export function updateAntenna(id: string, input: AntennaUpdateInput): Promise<Antenna> {
  return http.put<Antenna>(`${ANTENNA_PATH}/${id}`, input);
}

export function changeAntennaStatus(id: string, status: LifecycleStatus): Promise<Antenna> {
  return http.patch<Antenna>(`${ANTENNA_PATH}/${id}/status`, undefined, { status });
}

export function deleteAntenna(id: string): Promise<void> {
  return http.delete(`${ANTENNA_PATH}/${id}`);
}

export function bulkCreateAntennas(inputs: AntennaInput[]): Promise<BulkResult<Antenna>> {
  return http.post<BulkResult<Antenna>>(`${ANTENNA_PATH}/bulk`, inputs);
}

export function getAntennaStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ANTENNA_PATH}/stats/status`);
}

export function exportAntennasCsv(filter: AntennaFilter): Promise<Blob> {
  return http.blob(`${ANTENNA_PATH}/export`, toAntennaQuery(filter));
}

export function listAntennaBySectorId(sectorId: string, page = 0, size = 20): Promise<Page<Antenna>> {
  return http.get<Page<Antenna>>(`${ANTENNA_PATH}/by-sector-id/${sectorId}`, { page, size });
}
