import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { SUBINTERFACE_PATH } from './types';
import type { SubInterface, SubInterfaceFilter, SubInterfaceInput, SubInterfaceSummary, SubInterfaceUpdateInput } from './types';
import { toSubInterfaceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchSubInterfaces(
  filter: SubInterfaceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<SubInterface>> {
  return http.get<Page<SubInterface>>(SUBINTERFACE_PATH, { ...toSubInterfaceQuery(filter), page, size, sort });
}

export function getSubInterface(id: string): Promise<SubInterface> {
  return http.get<SubInterface>(`${SUBINTERFACE_PATH}/${id}`);
}

export function getSubInterfaceByCode(code: string): Promise<SubInterface> {
  return http.get<SubInterface>(`${SUBINTERFACE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listSubInterfaceSummaries(status?: LifecycleStatus): Promise<SubInterfaceSummary[]> {
  return http.get<SubInterfaceSummary[]>(`${SUBINTERFACE_PATH}/summaries`, { status });
}

export function createSubInterface(input: SubInterfaceInput): Promise<SubInterface> {
  return http.post<SubInterface>(SUBINTERFACE_PATH, input);
}

export function updateSubInterface(id: string, input: SubInterfaceUpdateInput): Promise<SubInterface> {
  return http.put<SubInterface>(`${SUBINTERFACE_PATH}/${id}`, input);
}

export function changeSubInterfaceStatus(id: string, status: LifecycleStatus): Promise<SubInterface> {
  return http.patch<SubInterface>(`${SUBINTERFACE_PATH}/${id}/status`, undefined, { status });
}

export function deleteSubInterface(id: string): Promise<void> {
  return http.delete(`${SUBINTERFACE_PATH}/${id}`);
}

export function bulkCreateSubInterfaces(inputs: SubInterfaceInput[]): Promise<BulkResult<SubInterface>> {
  return http.post<BulkResult<SubInterface>>(`${SUBINTERFACE_PATH}/bulk`, inputs);
}

export function getSubInterfaceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${SUBINTERFACE_PATH}/stats/status`);
}

export function exportSubInterfacesCsv(filter: SubInterfaceFilter): Promise<Blob> {
  return http.blob(`${SUBINTERFACE_PATH}/export`, toSubInterfaceQuery(filter));
}

export function listSubInterfaceByInterfaceId(interfaceId: string, page = 0, size = 20): Promise<Page<SubInterface>> {
  return http.get<Page<SubInterface>>(`${SUBINTERFACE_PATH}/by-interface-id/${interfaceId}`, { page, size });
}
