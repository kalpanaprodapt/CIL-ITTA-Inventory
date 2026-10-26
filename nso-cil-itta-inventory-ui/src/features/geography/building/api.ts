import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { BUILDING_PATH } from './types';
import type { Building, BuildingFilter, BuildingInput, BuildingSummary, BuildingUpdateInput } from './types';
import { toBuildingQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchBuildings(
  filter: BuildingFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Building>> {
  return http.get<Page<Building>>(BUILDING_PATH, { ...toBuildingQuery(filter), page, size, sort });
}

export function getBuilding(id: string): Promise<Building> {
  return http.get<Building>(`${BUILDING_PATH}/${id}`);
}

export function getBuildingByCode(code: string): Promise<Building> {
  return http.get<Building>(`${BUILDING_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listBuildingSummaries(status?: LifecycleStatus): Promise<BuildingSummary[]> {
  return http.get<BuildingSummary[]>(`${BUILDING_PATH}/summaries`, { status });
}

export function createBuilding(input: BuildingInput): Promise<Building> {
  return http.post<Building>(BUILDING_PATH, input);
}

export function updateBuilding(id: string, input: BuildingUpdateInput): Promise<Building> {
  return http.put<Building>(`${BUILDING_PATH}/${id}`, input);
}

export function changeBuildingStatus(id: string, status: LifecycleStatus): Promise<Building> {
  return http.patch<Building>(`${BUILDING_PATH}/${id}/status`, undefined, { status });
}

export function deleteBuilding(id: string): Promise<void> {
  return http.delete(`${BUILDING_PATH}/${id}`);
}

export function bulkCreateBuildings(inputs: BuildingInput[]): Promise<BulkResult<Building>> {
  return http.post<BulkResult<Building>>(`${BUILDING_PATH}/bulk`, inputs);
}

export function getBuildingStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${BUILDING_PATH}/stats/status`);
}

export function exportBuildingsCsv(filter: BuildingFilter): Promise<Blob> {
  return http.blob(`${BUILDING_PATH}/export`, toBuildingQuery(filter));
}

export function listBuildingBySiteId(siteId: string, page = 0, size = 20): Promise<Page<Building>> {
  return http.get<Page<Building>>(`${BUILDING_PATH}/by-site-id/${siteId}`, { page, size });
}
