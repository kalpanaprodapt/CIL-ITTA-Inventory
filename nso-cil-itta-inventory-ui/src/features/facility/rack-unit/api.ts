import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { RACKUNIT_PATH } from './types';
import type { RackUnit, RackUnitFilter, RackUnitInput, RackUnitSummary, RackUnitUpdateInput } from './types';
import { toRackUnitQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRackUnits(
  filter: RackUnitFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<RackUnit>> {
  return http.get<Page<RackUnit>>(RACKUNIT_PATH, { ...toRackUnitQuery(filter), page, size, sort });
}

export function getRackUnit(id: string): Promise<RackUnit> {
  return http.get<RackUnit>(`${RACKUNIT_PATH}/${id}`);
}

export function getRackUnitByCode(code: string): Promise<RackUnit> {
  return http.get<RackUnit>(`${RACKUNIT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRackUnitSummaries(status?: LifecycleStatus): Promise<RackUnitSummary[]> {
  return http.get<RackUnitSummary[]>(`${RACKUNIT_PATH}/summaries`, { status });
}

export function createRackUnit(input: RackUnitInput): Promise<RackUnit> {
  return http.post<RackUnit>(RACKUNIT_PATH, input);
}

export function updateRackUnit(id: string, input: RackUnitUpdateInput): Promise<RackUnit> {
  return http.put<RackUnit>(`${RACKUNIT_PATH}/${id}`, input);
}

export function changeRackUnitStatus(id: string, status: LifecycleStatus): Promise<RackUnit> {
  return http.patch<RackUnit>(`${RACKUNIT_PATH}/${id}/status`, undefined, { status });
}

export function deleteRackUnit(id: string): Promise<void> {
  return http.delete(`${RACKUNIT_PATH}/${id}`);
}

export function bulkCreateRackUnits(inputs: RackUnitInput[]): Promise<BulkResult<RackUnit>> {
  return http.post<BulkResult<RackUnit>>(`${RACKUNIT_PATH}/bulk`, inputs);
}

export function getRackUnitStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${RACKUNIT_PATH}/stats/status`);
}

export function exportRackUnitsCsv(filter: RackUnitFilter): Promise<Blob> {
  return http.blob(`${RACKUNIT_PATH}/export`, toRackUnitQuery(filter));
}

export function listRackUnitByRackId(rackId: string, page = 0, size = 20): Promise<Page<RackUnit>> {
  return http.get<Page<RackUnit>>(`${RACKUNIT_PATH}/by-rack-id/${rackId}`, { page, size });
}

export function listRackUnitByMountedDeviceId(mountedDeviceId: string, page = 0, size = 20): Promise<Page<RackUnit>> {
  return http.get<Page<RackUnit>>(`${RACKUNIT_PATH}/by-mounted-device-id/${mountedDeviceId}`, { page, size });
}
