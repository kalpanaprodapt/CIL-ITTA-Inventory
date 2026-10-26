import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VLANPOOL_PATH } from './types';
import type { VlanPool, VlanPoolFilter, VlanPoolInput, VlanPoolSummary, VlanPoolUpdateInput } from './types';
import { toVlanPoolQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVlanPools(
  filter: VlanPoolFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<VlanPool>> {
  return http.get<Page<VlanPool>>(VLANPOOL_PATH, { ...toVlanPoolQuery(filter), page, size, sort });
}

export function getVlanPool(id: string): Promise<VlanPool> {
  return http.get<VlanPool>(`${VLANPOOL_PATH}/${id}`);
}

export function getVlanPoolByCode(code: string): Promise<VlanPool> {
  return http.get<VlanPool>(`${VLANPOOL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVlanPoolSummaries(status?: LifecycleStatus): Promise<VlanPoolSummary[]> {
  return http.get<VlanPoolSummary[]>(`${VLANPOOL_PATH}/summaries`, { status });
}

export function createVlanPool(input: VlanPoolInput): Promise<VlanPool> {
  return http.post<VlanPool>(VLANPOOL_PATH, input);
}

export function updateVlanPool(id: string, input: VlanPoolUpdateInput): Promise<VlanPool> {
  return http.put<VlanPool>(`${VLANPOOL_PATH}/${id}`, input);
}

export function changeVlanPoolStatus(id: string, status: LifecycleStatus): Promise<VlanPool> {
  return http.patch<VlanPool>(`${VLANPOOL_PATH}/${id}/status`, undefined, { status });
}

export function deleteVlanPool(id: string): Promise<void> {
  return http.delete(`${VLANPOOL_PATH}/${id}`);
}

export function bulkCreateVlanPools(inputs: VlanPoolInput[]): Promise<BulkResult<VlanPool>> {
  return http.post<BulkResult<VlanPool>>(`${VLANPOOL_PATH}/bulk`, inputs);
}

export function getVlanPoolStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VLANPOOL_PATH}/stats/status`);
}

export function exportVlanPoolsCsv(filter: VlanPoolFilter): Promise<Blob> {
  return http.blob(`${VLANPOOL_PATH}/export`, toVlanPoolQuery(filter));
}
