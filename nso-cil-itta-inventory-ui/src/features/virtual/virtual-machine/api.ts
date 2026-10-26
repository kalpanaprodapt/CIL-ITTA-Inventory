import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VIRTUALMACHINE_PATH } from './types';
import type { VirtualMachine, VirtualMachineFilter, VirtualMachineInput, VirtualMachineSummary, VirtualMachineUpdateInput } from './types';
import { toVirtualMachineQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVirtualMachines(
  filter: VirtualMachineFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<VirtualMachine>> {
  return http.get<Page<VirtualMachine>>(VIRTUALMACHINE_PATH, { ...toVirtualMachineQuery(filter), page, size, sort });
}

export function getVirtualMachine(id: string): Promise<VirtualMachine> {
  return http.get<VirtualMachine>(`${VIRTUALMACHINE_PATH}/${id}`);
}

export function getVirtualMachineByCode(code: string): Promise<VirtualMachine> {
  return http.get<VirtualMachine>(`${VIRTUALMACHINE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVirtualMachineSummaries(status?: LifecycleStatus): Promise<VirtualMachineSummary[]> {
  return http.get<VirtualMachineSummary[]>(`${VIRTUALMACHINE_PATH}/summaries`, { status });
}

export function createVirtualMachine(input: VirtualMachineInput): Promise<VirtualMachine> {
  return http.post<VirtualMachine>(VIRTUALMACHINE_PATH, input);
}

export function updateVirtualMachine(id: string, input: VirtualMachineUpdateInput): Promise<VirtualMachine> {
  return http.put<VirtualMachine>(`${VIRTUALMACHINE_PATH}/${id}`, input);
}

export function changeVirtualMachineStatus(id: string, status: LifecycleStatus): Promise<VirtualMachine> {
  return http.patch<VirtualMachine>(`${VIRTUALMACHINE_PATH}/${id}/status`, undefined, { status });
}

export function deleteVirtualMachine(id: string): Promise<void> {
  return http.delete(`${VIRTUALMACHINE_PATH}/${id}`);
}

export function bulkCreateVirtualMachines(inputs: VirtualMachineInput[]): Promise<BulkResult<VirtualMachine>> {
  return http.post<BulkResult<VirtualMachine>>(`${VIRTUALMACHINE_PATH}/bulk`, inputs);
}

export function getVirtualMachineStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VIRTUALMACHINE_PATH}/stats/status`);
}

export function exportVirtualMachinesCsv(filter: VirtualMachineFilter): Promise<Blob> {
  return http.blob(`${VIRTUALMACHINE_PATH}/export`, toVirtualMachineQuery(filter));
}

export function listVirtualMachineByHypervisorId(hypervisorId: string, page = 0, size = 20): Promise<Page<VirtualMachine>> {
  return http.get<Page<VirtualMachine>>(`${VIRTUALMACHINE_PATH}/by-hypervisor-id/${hypervisorId}`, { page, size });
}
