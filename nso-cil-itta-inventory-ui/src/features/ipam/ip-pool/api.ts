import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { IPPOOL_PATH } from './types';
import type { IpPool, IpPoolFilter, IpPoolInput, IpPoolSummary, IpPoolUpdateInput } from './types';
import { toIpPoolQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchIpPools(
  filter: IpPoolFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<IpPool>> {
  return http.get<Page<IpPool>>(IPPOOL_PATH, { ...toIpPoolQuery(filter), page, size, sort });
}

export function getIpPool(id: string): Promise<IpPool> {
  return http.get<IpPool>(`${IPPOOL_PATH}/${id}`);
}

export function getIpPoolByCode(code: string): Promise<IpPool> {
  return http.get<IpPool>(`${IPPOOL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listIpPoolSummaries(status?: LifecycleStatus): Promise<IpPoolSummary[]> {
  return http.get<IpPoolSummary[]>(`${IPPOOL_PATH}/summaries`, { status });
}

export function createIpPool(input: IpPoolInput): Promise<IpPool> {
  return http.post<IpPool>(IPPOOL_PATH, input);
}

export function updateIpPool(id: string, input: IpPoolUpdateInput): Promise<IpPool> {
  return http.put<IpPool>(`${IPPOOL_PATH}/${id}`, input);
}

export function changeIpPoolStatus(id: string, status: LifecycleStatus): Promise<IpPool> {
  return http.patch<IpPool>(`${IPPOOL_PATH}/${id}/status`, undefined, { status });
}

export function deleteIpPool(id: string): Promise<void> {
  return http.delete(`${IPPOOL_PATH}/${id}`);
}

export function bulkCreateIpPools(inputs: IpPoolInput[]): Promise<BulkResult<IpPool>> {
  return http.post<BulkResult<IpPool>>(`${IPPOOL_PATH}/bulk`, inputs);
}

export function getIpPoolStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${IPPOOL_PATH}/stats/status`);
}

export function exportIpPoolsCsv(filter: IpPoolFilter): Promise<Blob> {
  return http.blob(`${IPPOOL_PATH}/export`, toIpPoolQuery(filter));
}

export function listIpPoolBySubnetId(subnetId: string, page = 0, size = 20): Promise<Page<IpPool>> {
  return http.get<Page<IpPool>>(`${IPPOOL_PATH}/by-subnet-id/${subnetId}`, { page, size });
}
