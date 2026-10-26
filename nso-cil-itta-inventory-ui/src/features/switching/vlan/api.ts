import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VLAN_PATH } from './types';
import type { Vlan, VlanFilter, VlanInput, VlanSummary, VlanUpdateInput } from './types';
import { toVlanQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVlans(
  filter: VlanFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Vlan>> {
  return http.get<Page<Vlan>>(VLAN_PATH, { ...toVlanQuery(filter), page, size, sort });
}

export function getVlan(id: string): Promise<Vlan> {
  return http.get<Vlan>(`${VLAN_PATH}/${id}`);
}

export function getVlanByCode(code: string): Promise<Vlan> {
  return http.get<Vlan>(`${VLAN_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVlanSummaries(status?: LifecycleStatus): Promise<VlanSummary[]> {
  return http.get<VlanSummary[]>(`${VLAN_PATH}/summaries`, { status });
}

export function createVlan(input: VlanInput): Promise<Vlan> {
  return http.post<Vlan>(VLAN_PATH, input);
}

export function updateVlan(id: string, input: VlanUpdateInput): Promise<Vlan> {
  return http.put<Vlan>(`${VLAN_PATH}/${id}`, input);
}

export function changeVlanStatus(id: string, status: LifecycleStatus): Promise<Vlan> {
  return http.patch<Vlan>(`${VLAN_PATH}/${id}/status`, undefined, { status });
}

export function deleteVlan(id: string): Promise<void> {
  return http.delete(`${VLAN_PATH}/${id}`);
}

export function bulkCreateVlans(inputs: VlanInput[]): Promise<BulkResult<Vlan>> {
  return http.post<BulkResult<Vlan>>(`${VLAN_PATH}/bulk`, inputs);
}

export function getVlanStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VLAN_PATH}/stats/status`);
}

export function exportVlansCsv(filter: VlanFilter): Promise<Blob> {
  return http.blob(`${VLAN_PATH}/export`, toVlanQuery(filter));
}

export function listVlanByPoolId(poolId: string, page = 0, size = 20): Promise<Page<Vlan>> {
  return http.get<Page<Vlan>>(`${VLAN_PATH}/by-pool-id/${poolId}`, { page, size });
}
