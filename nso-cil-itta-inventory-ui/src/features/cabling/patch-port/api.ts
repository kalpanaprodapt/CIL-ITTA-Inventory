import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PATCHPORT_PATH } from './types';
import type { PatchPort, PatchPortFilter, PatchPortInput, PatchPortSummary, PatchPortUpdateInput } from './types';
import { toPatchPortQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPatchPorts(
  filter: PatchPortFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PatchPort>> {
  return http.get<Page<PatchPort>>(PATCHPORT_PATH, { ...toPatchPortQuery(filter), page, size, sort });
}

export function getPatchPort(id: string): Promise<PatchPort> {
  return http.get<PatchPort>(`${PATCHPORT_PATH}/${id}`);
}

export function getPatchPortByCode(code: string): Promise<PatchPort> {
  return http.get<PatchPort>(`${PATCHPORT_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPatchPortSummaries(status?: LifecycleStatus): Promise<PatchPortSummary[]> {
  return http.get<PatchPortSummary[]>(`${PATCHPORT_PATH}/summaries`, { status });
}

export function createPatchPort(input: PatchPortInput): Promise<PatchPort> {
  return http.post<PatchPort>(PATCHPORT_PATH, input);
}

export function updatePatchPort(id: string, input: PatchPortUpdateInput): Promise<PatchPort> {
  return http.put<PatchPort>(`${PATCHPORT_PATH}/${id}`, input);
}

export function changePatchPortStatus(id: string, status: LifecycleStatus): Promise<PatchPort> {
  return http.patch<PatchPort>(`${PATCHPORT_PATH}/${id}/status`, undefined, { status });
}

export function deletePatchPort(id: string): Promise<void> {
  return http.delete(`${PATCHPORT_PATH}/${id}`);
}

export function bulkCreatePatchPorts(inputs: PatchPortInput[]): Promise<BulkResult<PatchPort>> {
  return http.post<BulkResult<PatchPort>>(`${PATCHPORT_PATH}/bulk`, inputs);
}

export function getPatchPortStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PATCHPORT_PATH}/stats/status`);
}

export function exportPatchPortsCsv(filter: PatchPortFilter): Promise<Blob> {
  return http.blob(`${PATCHPORT_PATH}/export`, toPatchPortQuery(filter));
}

export function listPatchPortByPanelId(panelId: string, page = 0, size = 20): Promise<Page<PatchPort>> {
  return http.get<Page<PatchPort>>(`${PATCHPORT_PATH}/by-panel-id/${panelId}`, { page, size });
}

export function listPatchPortByConnectedCableId(connectedCableId: string, page = 0, size = 20): Promise<Page<PatchPort>> {
  return http.get<Page<PatchPort>>(`${PATCHPORT_PATH}/by-connected-cable-id/${connectedCableId}`, { page, size });
}
