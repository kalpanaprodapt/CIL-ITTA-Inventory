import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { LAGGROUP_PATH } from './types';
import type { LagGroup, LagGroupFilter, LagGroupInput, LagGroupSummary, LagGroupUpdateInput } from './types';
import { toLagGroupQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchLagGroups(
  filter: LagGroupFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<LagGroup>> {
  return http.get<Page<LagGroup>>(LAGGROUP_PATH, { ...toLagGroupQuery(filter), page, size, sort });
}

export function getLagGroup(id: string): Promise<LagGroup> {
  return http.get<LagGroup>(`${LAGGROUP_PATH}/${id}`);
}

export function getLagGroupByCode(code: string): Promise<LagGroup> {
  return http.get<LagGroup>(`${LAGGROUP_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listLagGroupSummaries(status?: LifecycleStatus): Promise<LagGroupSummary[]> {
  return http.get<LagGroupSummary[]>(`${LAGGROUP_PATH}/summaries`, { status });
}

export function createLagGroup(input: LagGroupInput): Promise<LagGroup> {
  return http.post<LagGroup>(LAGGROUP_PATH, input);
}

export function updateLagGroup(id: string, input: LagGroupUpdateInput): Promise<LagGroup> {
  return http.put<LagGroup>(`${LAGGROUP_PATH}/${id}`, input);
}

export function changeLagGroupStatus(id: string, status: LifecycleStatus): Promise<LagGroup> {
  return http.patch<LagGroup>(`${LAGGROUP_PATH}/${id}/status`, undefined, { status });
}

export function deleteLagGroup(id: string): Promise<void> {
  return http.delete(`${LAGGROUP_PATH}/${id}`);
}

export function bulkCreateLagGroups(inputs: LagGroupInput[]): Promise<BulkResult<LagGroup>> {
  return http.post<BulkResult<LagGroup>>(`${LAGGROUP_PATH}/bulk`, inputs);
}

export function getLagGroupStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${LAGGROUP_PATH}/stats/status`);
}

export function exportLagGroupsCsv(filter: LagGroupFilter): Promise<Blob> {
  return http.blob(`${LAGGROUP_PATH}/export`, toLagGroupQuery(filter));
}

export function listLagGroupByDeviceId(deviceId: string, page = 0, size = 20): Promise<Page<LagGroup>> {
  return http.get<Page<LagGroup>>(`${LAGGROUP_PATH}/by-device-id/${deviceId}`, { page, size });
}
