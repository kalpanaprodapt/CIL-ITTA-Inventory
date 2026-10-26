import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VNF_PATH } from './types';
import type { Vnf, VnfFilter, VnfInput, VnfSummary, VnfUpdateInput } from './types';
import { toVnfQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVnfs(
  filter: VnfFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Vnf>> {
  return http.get<Page<Vnf>>(VNF_PATH, { ...toVnfQuery(filter), page, size, sort });
}

export function getVnf(id: string): Promise<Vnf> {
  return http.get<Vnf>(`${VNF_PATH}/${id}`);
}

export function getVnfByCode(code: string): Promise<Vnf> {
  return http.get<Vnf>(`${VNF_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVnfSummaries(status?: LifecycleStatus): Promise<VnfSummary[]> {
  return http.get<VnfSummary[]>(`${VNF_PATH}/summaries`, { status });
}

export function createVnf(input: VnfInput): Promise<Vnf> {
  return http.post<Vnf>(VNF_PATH, input);
}

export function updateVnf(id: string, input: VnfUpdateInput): Promise<Vnf> {
  return http.put<Vnf>(`${VNF_PATH}/${id}`, input);
}

export function changeVnfStatus(id: string, status: LifecycleStatus): Promise<Vnf> {
  return http.patch<Vnf>(`${VNF_PATH}/${id}/status`, undefined, { status });
}

export function deleteVnf(id: string): Promise<void> {
  return http.delete(`${VNF_PATH}/${id}`);
}

export function bulkCreateVnfs(inputs: VnfInput[]): Promise<BulkResult<Vnf>> {
  return http.post<BulkResult<Vnf>>(`${VNF_PATH}/bulk`, inputs);
}

export function getVnfStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VNF_PATH}/stats/status`);
}

export function exportVnfsCsv(filter: VnfFilter): Promise<Blob> {
  return http.blob(`${VNF_PATH}/export`, toVnfQuery(filter));
}
