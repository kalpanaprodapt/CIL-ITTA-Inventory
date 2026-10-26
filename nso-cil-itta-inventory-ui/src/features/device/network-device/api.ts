import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { NETWORKDEVICE_PATH } from './types';
import type { NetworkDevice, NetworkDeviceFilter, NetworkDeviceInput, NetworkDeviceSummary, NetworkDeviceUpdateInput } from './types';
import { toNetworkDeviceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchNetworkDevices(
  filter: NetworkDeviceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<NetworkDevice>> {
  return http.get<Page<NetworkDevice>>(NETWORKDEVICE_PATH, { ...toNetworkDeviceQuery(filter), page, size, sort });
}

export function getNetworkDevice(id: string): Promise<NetworkDevice> {
  return http.get<NetworkDevice>(`${NETWORKDEVICE_PATH}/${id}`);
}

export function getNetworkDeviceByCode(code: string): Promise<NetworkDevice> {
  return http.get<NetworkDevice>(`${NETWORKDEVICE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listNetworkDeviceSummaries(status?: LifecycleStatus): Promise<NetworkDeviceSummary[]> {
  return http.get<NetworkDeviceSummary[]>(`${NETWORKDEVICE_PATH}/summaries`, { status });
}

export function createNetworkDevice(input: NetworkDeviceInput): Promise<NetworkDevice> {
  return http.post<NetworkDevice>(NETWORKDEVICE_PATH, input);
}

export function updateNetworkDevice(id: string, input: NetworkDeviceUpdateInput): Promise<NetworkDevice> {
  return http.put<NetworkDevice>(`${NETWORKDEVICE_PATH}/${id}`, input);
}

export function changeNetworkDeviceStatus(id: string, status: LifecycleStatus): Promise<NetworkDevice> {
  return http.patch<NetworkDevice>(`${NETWORKDEVICE_PATH}/${id}/status`, undefined, { status });
}

export function deleteNetworkDevice(id: string): Promise<void> {
  return http.delete(`${NETWORKDEVICE_PATH}/${id}`);
}

export function bulkCreateNetworkDevices(inputs: NetworkDeviceInput[]): Promise<BulkResult<NetworkDevice>> {
  return http.post<BulkResult<NetworkDevice>>(`${NETWORKDEVICE_PATH}/bulk`, inputs);
}

export function getNetworkDeviceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${NETWORKDEVICE_PATH}/stats/status`);
}

export function exportNetworkDevicesCsv(filter: NetworkDeviceFilter): Promise<Blob> {
  return http.blob(`${NETWORKDEVICE_PATH}/export`, toNetworkDeviceQuery(filter));
}

export function listNetworkDeviceByModelId(modelId: string, page = 0, size = 20): Promise<Page<NetworkDevice>> {
  return http.get<Page<NetworkDevice>>(`${NETWORKDEVICE_PATH}/by-model-id/${modelId}`, { page, size });
}

export function listNetworkDeviceByRackUnitId(rackUnitId: string, page = 0, size = 20): Promise<Page<NetworkDevice>> {
  return http.get<Page<NetworkDevice>>(`${NETWORKDEVICE_PATH}/by-rack-unit-id/${rackUnitId}`, { page, size });
}
