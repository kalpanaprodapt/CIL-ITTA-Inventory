import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VPNSERVICE_PATH } from './types';
import type { VpnService, VpnServiceFilter, VpnServiceInput, VpnServiceSummary, VpnServiceUpdateInput } from './types';
import { toVpnServiceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVpnServices(
  filter: VpnServiceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<VpnService>> {
  return http.get<Page<VpnService>>(VPNSERVICE_PATH, { ...toVpnServiceQuery(filter), page, size, sort });
}

export function getVpnService(id: string): Promise<VpnService> {
  return http.get<VpnService>(`${VPNSERVICE_PATH}/${id}`);
}

export function getVpnServiceByCode(code: string): Promise<VpnService> {
  return http.get<VpnService>(`${VPNSERVICE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVpnServiceSummaries(status?: LifecycleStatus): Promise<VpnServiceSummary[]> {
  return http.get<VpnServiceSummary[]>(`${VPNSERVICE_PATH}/summaries`, { status });
}

export function createVpnService(input: VpnServiceInput): Promise<VpnService> {
  return http.post<VpnService>(VPNSERVICE_PATH, input);
}

export function updateVpnService(id: string, input: VpnServiceUpdateInput): Promise<VpnService> {
  return http.put<VpnService>(`${VPNSERVICE_PATH}/${id}`, input);
}

export function changeVpnServiceStatus(id: string, status: LifecycleStatus): Promise<VpnService> {
  return http.patch<VpnService>(`${VPNSERVICE_PATH}/${id}/status`, undefined, { status });
}

export function deleteVpnService(id: string): Promise<void> {
  return http.delete(`${VPNSERVICE_PATH}/${id}`);
}

export function bulkCreateVpnServices(inputs: VpnServiceInput[]): Promise<BulkResult<VpnService>> {
  return http.post<BulkResult<VpnService>>(`${VPNSERVICE_PATH}/bulk`, inputs);
}

export function getVpnServiceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VPNSERVICE_PATH}/stats/status`);
}

export function exportVpnServicesCsv(filter: VpnServiceFilter): Promise<Blob> {
  return http.blob(`${VPNSERVICE_PATH}/export`, toVpnServiceQuery(filter));
}

export function listVpnServiceByVrfId(vrfId: string, page = 0, size = 20): Promise<Page<VpnService>> {
  return http.get<Page<VpnService>>(`${VPNSERVICE_PATH}/by-vrf-id/${vrfId}`, { page, size });
}

export function listVpnServiceByCustomerId(customerId: string, page = 0, size = 20): Promise<Page<VpnService>> {
  return http.get<Page<VpnService>>(`${VPNSERVICE_PATH}/by-customer-id/${customerId}`, { page, size });
}
