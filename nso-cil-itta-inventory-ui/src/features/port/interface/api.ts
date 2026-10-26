import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { INTERFACE_PATH } from './types';
import type { Interface, InterfaceFilter, InterfaceInput, InterfaceSummary, InterfaceUpdateInput } from './types';
import { toInterfaceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchInterfaces(
  filter: InterfaceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Interface>> {
  return http.get<Page<Interface>>(INTERFACE_PATH, { ...toInterfaceQuery(filter), page, size, sort });
}

export function getInterface(id: string): Promise<Interface> {
  return http.get<Interface>(`${INTERFACE_PATH}/${id}`);
}

export function getInterfaceByCode(code: string): Promise<Interface> {
  return http.get<Interface>(`${INTERFACE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listInterfaceSummaries(status?: LifecycleStatus): Promise<InterfaceSummary[]> {
  return http.get<InterfaceSummary[]>(`${INTERFACE_PATH}/summaries`, { status });
}

export function createInterface(input: InterfaceInput): Promise<Interface> {
  return http.post<Interface>(INTERFACE_PATH, input);
}

export function updateInterface(id: string, input: InterfaceUpdateInput): Promise<Interface> {
  return http.put<Interface>(`${INTERFACE_PATH}/${id}`, input);
}

export function changeInterfaceStatus(id: string, status: LifecycleStatus): Promise<Interface> {
  return http.patch<Interface>(`${INTERFACE_PATH}/${id}/status`, undefined, { status });
}

export function deleteInterface(id: string): Promise<void> {
  return http.delete(`${INTERFACE_PATH}/${id}`);
}

export function bulkCreateInterfaces(inputs: InterfaceInput[]): Promise<BulkResult<Interface>> {
  return http.post<BulkResult<Interface>>(`${INTERFACE_PATH}/bulk`, inputs);
}

export function getInterfaceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${INTERFACE_PATH}/stats/status`);
}

export function exportInterfacesCsv(filter: InterfaceFilter): Promise<Blob> {
  return http.blob(`${INTERFACE_PATH}/export`, toInterfaceQuery(filter));
}

export function listInterfaceByPortId(portId: string, page = 0, size = 20): Promise<Page<Interface>> {
  return http.get<Page<Interface>>(`${INTERFACE_PATH}/by-port-id/${portId}`, { page, size });
}
