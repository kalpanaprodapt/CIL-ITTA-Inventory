import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { IPADDRESS_PATH } from './types';
import type { IpAddress, IpAddressFilter, IpAddressInput, IpAddressSummary, IpAddressUpdateInput } from './types';
import { toIpAddressQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchIpAddresss(
  filter: IpAddressFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<IpAddress>> {
  return http.get<Page<IpAddress>>(IPADDRESS_PATH, { ...toIpAddressQuery(filter), page, size, sort });
}

export function getIpAddress(id: string): Promise<IpAddress> {
  return http.get<IpAddress>(`${IPADDRESS_PATH}/${id}`);
}

export function getIpAddressByCode(code: string): Promise<IpAddress> {
  return http.get<IpAddress>(`${IPADDRESS_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listIpAddressSummaries(status?: LifecycleStatus): Promise<IpAddressSummary[]> {
  return http.get<IpAddressSummary[]>(`${IPADDRESS_PATH}/summaries`, { status });
}

export function createIpAddress(input: IpAddressInput): Promise<IpAddress> {
  return http.post<IpAddress>(IPADDRESS_PATH, input);
}

export function updateIpAddress(id: string, input: IpAddressUpdateInput): Promise<IpAddress> {
  return http.put<IpAddress>(`${IPADDRESS_PATH}/${id}`, input);
}

export function changeIpAddressStatus(id: string, status: LifecycleStatus): Promise<IpAddress> {
  return http.patch<IpAddress>(`${IPADDRESS_PATH}/${id}/status`, undefined, { status });
}

export function deleteIpAddress(id: string): Promise<void> {
  return http.delete(`${IPADDRESS_PATH}/${id}`);
}

export function bulkCreateIpAddresss(inputs: IpAddressInput[]): Promise<BulkResult<IpAddress>> {
  return http.post<BulkResult<IpAddress>>(`${IPADDRESS_PATH}/bulk`, inputs);
}

export function getIpAddressStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${IPADDRESS_PATH}/stats/status`);
}

export function exportIpAddresssCsv(filter: IpAddressFilter): Promise<Blob> {
  return http.blob(`${IPADDRESS_PATH}/export`, toIpAddressQuery(filter));
}

export function listIpAddressBySubnetId(subnetId: string, page = 0, size = 20): Promise<Page<IpAddress>> {
  return http.get<Page<IpAddress>>(`${IPADDRESS_PATH}/by-subnet-id/${subnetId}`, { page, size });
}

export function listIpAddressByAssignedToId(assignedToId: string, page = 0, size = 20): Promise<Page<IpAddress>> {
  return http.get<Page<IpAddress>>(`${IPADDRESS_PATH}/by-assigned-to-id/${assignedToId}`, { page, size });
}
