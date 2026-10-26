import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { REGION_PATH } from './types';
import type { Region, RegionFilter, RegionInput, RegionSummary, RegionUpdateInput } from './types';
import { toRegionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRegions(
  filter: RegionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Region>> {
  return http.get<Page<Region>>(REGION_PATH, { ...toRegionQuery(filter), page, size, sort });
}

export function getRegion(id: string): Promise<Region> {
  return http.get<Region>(`${REGION_PATH}/${id}`);
}

export function getRegionByCode(code: string): Promise<Region> {
  return http.get<Region>(`${REGION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRegionSummaries(status?: LifecycleStatus): Promise<RegionSummary[]> {
  return http.get<RegionSummary[]>(`${REGION_PATH}/summaries`, { status });
}

export function createRegion(input: RegionInput): Promise<Region> {
  return http.post<Region>(REGION_PATH, input);
}

export function updateRegion(id: string, input: RegionUpdateInput): Promise<Region> {
  return http.put<Region>(`${REGION_PATH}/${id}`, input);
}

export function changeRegionStatus(id: string, status: LifecycleStatus): Promise<Region> {
  return http.patch<Region>(`${REGION_PATH}/${id}/status`, undefined, { status });
}

export function deleteRegion(id: string): Promise<void> {
  return http.delete(`${REGION_PATH}/${id}`);
}

export function bulkCreateRegions(inputs: RegionInput[]): Promise<BulkResult<Region>> {
  return http.post<BulkResult<Region>>(`${REGION_PATH}/bulk`, inputs);
}

export function getRegionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${REGION_PATH}/stats/status`);
}

export function exportRegionsCsv(filter: RegionFilter): Promise<Blob> {
  return http.blob(`${REGION_PATH}/export`, toRegionQuery(filter));
}

export function listRegionByCountryId(countryId: string, page = 0, size = 20): Promise<Page<Region>> {
  return http.get<Page<Region>>(`${REGION_PATH}/by-country-id/${countryId}`, { page, size });
}
