import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CHASSIS_PATH } from './types';
import type { Chassis, ChassisFilter, ChassisInput, ChassisSummary, ChassisUpdateInput } from './types';
import { toChassisQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchChassiss(
  filter: ChassisFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Chassis>> {
  return http.get<Page<Chassis>>(CHASSIS_PATH, { ...toChassisQuery(filter), page, size, sort });
}

export function getChassis(id: string): Promise<Chassis> {
  return http.get<Chassis>(`${CHASSIS_PATH}/${id}`);
}

export function getChassisByCode(code: string): Promise<Chassis> {
  return http.get<Chassis>(`${CHASSIS_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listChassisSummaries(status?: LifecycleStatus): Promise<ChassisSummary[]> {
  return http.get<ChassisSummary[]>(`${CHASSIS_PATH}/summaries`, { status });
}

export function createChassis(input: ChassisInput): Promise<Chassis> {
  return http.post<Chassis>(CHASSIS_PATH, input);
}

export function updateChassis(id: string, input: ChassisUpdateInput): Promise<Chassis> {
  return http.put<Chassis>(`${CHASSIS_PATH}/${id}`, input);
}

export function changeChassisStatus(id: string, status: LifecycleStatus): Promise<Chassis> {
  return http.patch<Chassis>(`${CHASSIS_PATH}/${id}/status`, undefined, { status });
}

export function deleteChassis(id: string): Promise<void> {
  return http.delete(`${CHASSIS_PATH}/${id}`);
}

export function bulkCreateChassiss(inputs: ChassisInput[]): Promise<BulkResult<Chassis>> {
  return http.post<BulkResult<Chassis>>(`${CHASSIS_PATH}/bulk`, inputs);
}

export function getChassisStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CHASSIS_PATH}/stats/status`);
}

export function exportChassissCsv(filter: ChassisFilter): Promise<Blob> {
  return http.blob(`${CHASSIS_PATH}/export`, toChassisQuery(filter));
}

export function listChassisByDeviceId(deviceId: string, page = 0, size = 20): Promise<Page<Chassis>> {
  return http.get<Page<Chassis>>(`${CHASSIS_PATH}/by-device-id/${deviceId}`, { page, size });
}
