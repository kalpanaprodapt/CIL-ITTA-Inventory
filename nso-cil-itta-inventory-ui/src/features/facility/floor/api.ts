import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { FLOOR_PATH } from './types';
import type { Floor, FloorFilter, FloorInput, FloorSummary, FloorUpdateInput } from './types';
import { toFloorQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchFloors(
  filter: FloorFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Floor>> {
  return http.get<Page<Floor>>(FLOOR_PATH, { ...toFloorQuery(filter), page, size, sort });
}

export function getFloor(id: string): Promise<Floor> {
  return http.get<Floor>(`${FLOOR_PATH}/${id}`);
}

export function getFloorByCode(code: string): Promise<Floor> {
  return http.get<Floor>(`${FLOOR_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listFloorSummaries(status?: LifecycleStatus): Promise<FloorSummary[]> {
  return http.get<FloorSummary[]>(`${FLOOR_PATH}/summaries`, { status });
}

export function createFloor(input: FloorInput): Promise<Floor> {
  return http.post<Floor>(FLOOR_PATH, input);
}

export function updateFloor(id: string, input: FloorUpdateInput): Promise<Floor> {
  return http.put<Floor>(`${FLOOR_PATH}/${id}`, input);
}

export function changeFloorStatus(id: string, status: LifecycleStatus): Promise<Floor> {
  return http.patch<Floor>(`${FLOOR_PATH}/${id}/status`, undefined, { status });
}

export function deleteFloor(id: string): Promise<void> {
  return http.delete(`${FLOOR_PATH}/${id}`);
}

export function bulkCreateFloors(inputs: FloorInput[]): Promise<BulkResult<Floor>> {
  return http.post<BulkResult<Floor>>(`${FLOOR_PATH}/bulk`, inputs);
}

export function getFloorStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${FLOOR_PATH}/stats/status`);
}

export function exportFloorsCsv(filter: FloorFilter): Promise<Blob> {
  return http.blob(`${FLOOR_PATH}/export`, toFloorQuery(filter));
}

export function listFloorByBuildingId(buildingId: string, page = 0, size = 20): Promise<Page<Floor>> {
  return http.get<Page<Floor>>(`${FLOOR_PATH}/by-building-id/${buildingId}`, { page, size });
}
