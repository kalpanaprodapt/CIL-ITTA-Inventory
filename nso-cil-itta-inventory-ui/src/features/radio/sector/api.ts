import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SECTOR_PATH } from './types';
import type { Sector, SectorFilter, SectorInput, SectorSummary, SectorUpdateInput } from './types';
import { toSectorQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSectors(
  filter: SectorFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Sector>> {
  return http.get<Page<Sector>>(SECTOR_PATH, { ...toSectorQuery(filter), page, size, sort });
}

export function getSector(id: string): Promise<Sector> {
  return http.get<Sector>(`${SECTOR_PATH}/${id}`);
}

export function getSectorByCode(code: string): Promise<Sector> {
  return http.get<Sector>(`${SECTOR_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSectorSummaries(status?: LifecycleStatus): Promise<SectorSummary[]> {
  return http.get<SectorSummary[]>(`${SECTOR_PATH}/summaries`, { status });
}

export function createSector(input: SectorInput): Promise<Sector> {
  return http.post<Sector>(SECTOR_PATH, input);
}

export function updateSector(id: string, input: SectorUpdateInput): Promise<Sector> {
  return http.put<Sector>(`${SECTOR_PATH}/${id}`, input);
}

export function changeSectorStatus(id: string, status: LifecycleStatus): Promise<Sector> {
  return http.patch<Sector>(`${SECTOR_PATH}/${id}/status`, undefined, { status });
}

export function deleteSector(id: string): Promise<void> {
  return http.delete(`${SECTOR_PATH}/${id}`);
}

export function bulkCreateSectors(inputs: SectorInput[]): Promise<BulkResult<Sector>> {
  return http.post<BulkResult<Sector>>(`${SECTOR_PATH}/bulk`, inputs);
}

export function getSectorStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SECTOR_PATH}/stats/status`);
}

export function exportSectorsCsv(filter: SectorFilter): Promise<Blob> {
  return http.blob(`${SECTOR_PATH}/export`, toSectorQuery(filter));
}

export function listSectorByBaseStationId(baseStationId: string, page = 0, size = 20): Promise<Page<Sector>> {
  return http.get<Page<Sector>>(`${SECTOR_PATH}/by-base-station-id/${baseStationId}`, { page, size });
}
