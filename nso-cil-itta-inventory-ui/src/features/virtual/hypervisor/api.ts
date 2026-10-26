import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { HYPERVISOR_PATH } from './types';
import type { Hypervisor, HypervisorFilter, HypervisorInput, HypervisorSummary, HypervisorUpdateInput } from './types';
import { toHypervisorQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchHypervisors(
  filter: HypervisorFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Hypervisor>> {
  return http.get<Page<Hypervisor>>(HYPERVISOR_PATH, { ...toHypervisorQuery(filter), page, size, sort });
}

export function getHypervisor(id: string): Promise<Hypervisor> {
  return http.get<Hypervisor>(`${HYPERVISOR_PATH}/${id}`);
}

export function getHypervisorByCode(code: string): Promise<Hypervisor> {
  return http.get<Hypervisor>(`${HYPERVISOR_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listHypervisorSummaries(status?: LifecycleStatus): Promise<HypervisorSummary[]> {
  return http.get<HypervisorSummary[]>(`${HYPERVISOR_PATH}/summaries`, { status });
}

export function createHypervisor(input: HypervisorInput): Promise<Hypervisor> {
  return http.post<Hypervisor>(HYPERVISOR_PATH, input);
}

export function updateHypervisor(id: string, input: HypervisorUpdateInput): Promise<Hypervisor> {
  return http.put<Hypervisor>(`${HYPERVISOR_PATH}/${id}`, input);
}

export function changeHypervisorStatus(id: string, status: LifecycleStatus): Promise<Hypervisor> {
  return http.patch<Hypervisor>(`${HYPERVISOR_PATH}/${id}/status`, undefined, { status });
}

export function deleteHypervisor(id: string): Promise<void> {
  return http.delete(`${HYPERVISOR_PATH}/${id}`);
}

export function bulkCreateHypervisors(inputs: HypervisorInput[]): Promise<BulkResult<Hypervisor>> {
  return http.post<BulkResult<Hypervisor>>(`${HYPERVISOR_PATH}/bulk`, inputs);
}

export function getHypervisorStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${HYPERVISOR_PATH}/stats/status`);
}

export function exportHypervisorsCsv(filter: HypervisorFilter): Promise<Blob> {
  return http.blob(`${HYPERVISOR_PATH}/export`, toHypervisorQuery(filter));
}

export function listHypervisorByHostDeviceId(hostDeviceId: string, page = 0, size = 20): Promise<Page<Hypervisor>> {
  return http.get<Page<Hypervisor>>(`${HYPERVISOR_PATH}/by-host-device-id/${hostDeviceId}`, { page, size });
}
