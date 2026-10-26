import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { IPSUBNET_PATH } from './types';
import type { IpSubnet, IpSubnetFilter, IpSubnetInput, IpSubnetSummary, IpSubnetUpdateInput } from './types';
import { toIpSubnetQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchIpSubnets(
  filter: IpSubnetFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<IpSubnet>> {
  return http.get<Page<IpSubnet>>(IPSUBNET_PATH, { ...toIpSubnetQuery(filter), page, size, sort });
}

export function getIpSubnet(id: string): Promise<IpSubnet> {
  return http.get<IpSubnet>(`${IPSUBNET_PATH}/${id}`);
}

export function getIpSubnetByCode(code: string): Promise<IpSubnet> {
  return http.get<IpSubnet>(`${IPSUBNET_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listIpSubnetSummaries(status?: LifecycleStatus): Promise<IpSubnetSummary[]> {
  return http.get<IpSubnetSummary[]>(`${IPSUBNET_PATH}/summaries`, { status });
}

export function createIpSubnet(input: IpSubnetInput): Promise<IpSubnet> {
  return http.post<IpSubnet>(IPSUBNET_PATH, input);
}

export function updateIpSubnet(id: string, input: IpSubnetUpdateInput): Promise<IpSubnet> {
  return http.put<IpSubnet>(`${IPSUBNET_PATH}/${id}`, input);
}

export function changeIpSubnetStatus(id: string, status: LifecycleStatus): Promise<IpSubnet> {
  return http.patch<IpSubnet>(`${IPSUBNET_PATH}/${id}/status`, undefined, { status });
}

export function deleteIpSubnet(id: string): Promise<void> {
  return http.delete(`${IPSUBNET_PATH}/${id}`);
}

export function bulkCreateIpSubnets(inputs: IpSubnetInput[]): Promise<BulkResult<IpSubnet>> {
  return http.post<BulkResult<IpSubnet>>(`${IPSUBNET_PATH}/bulk`, inputs);
}

export function getIpSubnetStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${IPSUBNET_PATH}/stats/status`);
}

export function exportIpSubnetsCsv(filter: IpSubnetFilter): Promise<Blob> {
  return http.blob(`${IPSUBNET_PATH}/export`, toIpSubnetQuery(filter));
}

export function listIpSubnetByVrfId(vrfId: string, page = 0, size = 20): Promise<Page<IpSubnet>> {
  return http.get<Page<IpSubnet>>(`${IPSUBNET_PATH}/by-vrf-id/${vrfId}`, { page, size });
}

export function listIpSubnetByVlanId(vlanId: string, page = 0, size = 20): Promise<Page<IpSubnet>> {
  return http.get<Page<IpSubnet>>(`${IPSUBNET_PATH}/by-vlan-id/${vlanId}`, { page, size });
}
