import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PATCHPANEL_PATH } from './types';
import type { PatchPanel, PatchPanelFilter, PatchPanelInput, PatchPanelSummary, PatchPanelUpdateInput } from './types';
import { toPatchPanelQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPatchPanels(
  filter: PatchPanelFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<PatchPanel>> {
  return http.get<Page<PatchPanel>>(PATCHPANEL_PATH, { ...toPatchPanelQuery(filter), page, size, sort });
}

export function getPatchPanel(id: string): Promise<PatchPanel> {
  return http.get<PatchPanel>(`${PATCHPANEL_PATH}/${id}`);
}

export function getPatchPanelByCode(code: string): Promise<PatchPanel> {
  return http.get<PatchPanel>(`${PATCHPANEL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPatchPanelSummaries(status?: LifecycleStatus): Promise<PatchPanelSummary[]> {
  return http.get<PatchPanelSummary[]>(`${PATCHPANEL_PATH}/summaries`, { status });
}

export function createPatchPanel(input: PatchPanelInput): Promise<PatchPanel> {
  return http.post<PatchPanel>(PATCHPANEL_PATH, input);
}

export function updatePatchPanel(id: string, input: PatchPanelUpdateInput): Promise<PatchPanel> {
  return http.put<PatchPanel>(`${PATCHPANEL_PATH}/${id}`, input);
}

export function changePatchPanelStatus(id: string, status: LifecycleStatus): Promise<PatchPanel> {
  return http.patch<PatchPanel>(`${PATCHPANEL_PATH}/${id}/status`, undefined, { status });
}

export function deletePatchPanel(id: string): Promise<void> {
  return http.delete(`${PATCHPANEL_PATH}/${id}`);
}

export function bulkCreatePatchPanels(inputs: PatchPanelInput[]): Promise<BulkResult<PatchPanel>> {
  return http.post<BulkResult<PatchPanel>>(`${PATCHPANEL_PATH}/bulk`, inputs);
}

export function getPatchPanelStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PATCHPANEL_PATH}/stats/status`);
}

export function exportPatchPanelsCsv(filter: PatchPanelFilter): Promise<Blob> {
  return http.blob(`${PATCHPANEL_PATH}/export`, toPatchPanelQuery(filter));
}

export function listPatchPanelByRoomId(roomId: string, page = 0, size = 20): Promise<Page<PatchPanel>> {
  return http.get<Page<PatchPanel>>(`${PATCHPANEL_PATH}/by-room-id/${roomId}`, { page, size });
}

export function listPatchPanelByRackId(rackId: string, page = 0, size = 20): Promise<Page<PatchPanel>> {
  return http.get<Page<PatchPanel>>(`${PATCHPANEL_PATH}/by-rack-id/${rackId}`, { page, size });
}
