import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PORT_PATH } from './types';
import type { Port, PortFilter, PortInput, PortSummary, PortUpdateInput } from './types';
import { toPortQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPorts(
  filter: PortFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Port>> {
  return http.get<Page<Port>>(PORT_PATH, { ...toPortQuery(filter), page, size, sort });
}

export function getPort(id: string): Promise<Port> {
  return http.get<Port>(`${PORT_PATH}/${id}`);
}

export function getPortByCode(code: string): Promise<Port> {
  return http.get<Port>(`${PORT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPortSummaries(status?: LifecycleStatus): Promise<PortSummary[]> {
  return http.get<PortSummary[]>(`${PORT_PATH}/summaries`, { status });
}

export function createPort(input: PortInput): Promise<Port> {
  return http.post<Port>(PORT_PATH, input);
}

export function updatePort(id: string, input: PortUpdateInput): Promise<Port> {
  return http.put<Port>(`${PORT_PATH}/${id}`, input);
}

export function changePortStatus(id: string, status: LifecycleStatus): Promise<Port> {
  return http.patch<Port>(`${PORT_PATH}/${id}/status`, undefined, { status });
}

export function deletePort(id: string): Promise<void> {
  return http.delete(`${PORT_PATH}/${id}`);
}

export function bulkCreatePorts(inputs: PortInput[]): Promise<BulkResult<Port>> {
  return http.post<BulkResult<Port>>(`${PORT_PATH}/bulk`, inputs);
}

export function getPortStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PORT_PATH}/stats/status`);
}

export function exportPortsCsv(filter: PortFilter): Promise<Blob> {
  return http.blob(`${PORT_PATH}/export`, toPortQuery(filter));
}

export function listPortByDeviceId(deviceId: string, page = 0, size = 20): Promise<Page<Port>> {
  return http.get<Page<Port>>(`${PORT_PATH}/by-device-id/${deviceId}`, { page, size });
}

export function listPortByCardId(cardId: string, page = 0, size = 20): Promise<Page<Port>> {
  return http.get<Page<Port>>(`${PORT_PATH}/by-card-id/${cardId}`, { page, size });
}
