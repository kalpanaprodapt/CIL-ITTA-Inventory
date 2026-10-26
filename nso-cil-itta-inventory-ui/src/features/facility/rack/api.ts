import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RACK_PATH } from './types';
import type { Rack, RackFilter, RackInput, RackSummary, RackUpdateInput } from './types';
import { toRackQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRacks(
  filter: RackFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Rack>> {
  return http.get<Page<Rack>>(RACK_PATH, { ...toRackQuery(filter), page, size, sort });
}

export function getRack(id: string): Promise<Rack> {
  return http.get<Rack>(`${RACK_PATH}/${id}`);
}

export function getRackByCode(code: string): Promise<Rack> {
  return http.get<Rack>(`${RACK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRackSummaries(status?: LifecycleStatus): Promise<RackSummary[]> {
  return http.get<RackSummary[]>(`${RACK_PATH}/summaries`, { status });
}

export function createRack(input: RackInput): Promise<Rack> {
  return http.post<Rack>(RACK_PATH, input);
}

export function updateRack(id: string, input: RackUpdateInput): Promise<Rack> {
  return http.put<Rack>(`${RACK_PATH}/${id}`, input);
}

export function changeRackStatus(id: string, status: LifecycleStatus): Promise<Rack> {
  return http.patch<Rack>(`${RACK_PATH}/${id}/status`, undefined, { status });
}

export function deleteRack(id: string): Promise<void> {
  return http.delete(`${RACK_PATH}/${id}`);
}

export function bulkCreateRacks(inputs: RackInput[]): Promise<BulkResult<Rack>> {
  return http.post<BulkResult<Rack>>(`${RACK_PATH}/bulk`, inputs);
}

export function getRackStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RACK_PATH}/stats/status`);
}

export function exportRacksCsv(filter: RackFilter): Promise<Blob> {
  return http.blob(`${RACK_PATH}/export`, toRackQuery(filter));
}

export function listRackByRoomId(roomId: string, page = 0, size = 20): Promise<Page<Rack>> {
  return http.get<Page<Rack>>(`${RACK_PATH}/by-room-id/${roomId}`, { page, size });
}
