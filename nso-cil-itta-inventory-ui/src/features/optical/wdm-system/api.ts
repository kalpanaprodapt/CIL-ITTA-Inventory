import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { WDMSYSTEM_PATH } from './types';
import type { WdmSystem, WdmSystemFilter, WdmSystemInput, WdmSystemSummary, WdmSystemUpdateInput } from './types';
import { toWdmSystemQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchWdmSystems(
  filter: WdmSystemFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<WdmSystem>> {
  return http.get<Page<WdmSystem>>(WDMSYSTEM_PATH, { ...toWdmSystemQuery(filter), page, size, sort });
}

export function getWdmSystem(id: string): Promise<WdmSystem> {
  return http.get<WdmSystem>(`${WDMSYSTEM_PATH}/${id}`);
}

export function getWdmSystemByCode(code: string): Promise<WdmSystem> {
  return http.get<WdmSystem>(`${WDMSYSTEM_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listWdmSystemSummaries(status?: LifecycleStatus): Promise<WdmSystemSummary[]> {
  return http.get<WdmSystemSummary[]>(`${WDMSYSTEM_PATH}/summaries`, { status });
}

export function createWdmSystem(input: WdmSystemInput): Promise<WdmSystem> {
  return http.post<WdmSystem>(WDMSYSTEM_PATH, input);
}

export function updateWdmSystem(id: string, input: WdmSystemUpdateInput): Promise<WdmSystem> {
  return http.put<WdmSystem>(`${WDMSYSTEM_PATH}/${id}`, input);
}

export function changeWdmSystemStatus(id: string, status: LifecycleStatus): Promise<WdmSystem> {
  return http.patch<WdmSystem>(`${WDMSYSTEM_PATH}/${id}/status`, undefined, { status });
}

export function deleteWdmSystem(id: string): Promise<void> {
  return http.delete(`${WDMSYSTEM_PATH}/${id}`);
}

export function bulkCreateWdmSystems(inputs: WdmSystemInput[]): Promise<BulkResult<WdmSystem>> {
  return http.post<BulkResult<WdmSystem>>(`${WDMSYSTEM_PATH}/bulk`, inputs);
}

export function getWdmSystemStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${WDMSYSTEM_PATH}/stats/status`);
}

export function exportWdmSystemsCsv(filter: WdmSystemFilter): Promise<Blob> {
  return http.blob(`${WDMSYSTEM_PATH}/export`, toWdmSystemQuery(filter));
}

export function listWdmSystemByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<WdmSystem>> {
  return http.get<Page<WdmSystem>>(`${WDMSYSTEM_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
