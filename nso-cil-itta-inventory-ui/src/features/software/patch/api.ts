import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { PATCH_PATH } from './types';
import type { Patch, PatchFilter, PatchInput, PatchSummary, PatchUpdateInput } from './types';
import { toPatchQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchPatchs(
  filter: PatchFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Patch>> {
  return http.get<Page<Patch>>(PATCH_PATH, { ...toPatchQuery(filter), page, size, sort });
}

export function getPatch(id: string): Promise<Patch> {
  return http.get<Patch>(`${PATCH_PATH}/${id}`);
}

export function getPatchByCode(code: string): Promise<Patch> {
  return http.get<Patch>(`${PATCH_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listPatchSummaries(status?: LifecycleStatus): Promise<PatchSummary[]> {
  return http.get<PatchSummary[]>(`${PATCH_PATH}/summaries`, { status });
}

export function createPatch(input: PatchInput): Promise<Patch> {
  return http.post<Patch>(PATCH_PATH, input);
}

export function updatePatch(id: string, input: PatchUpdateInput): Promise<Patch> {
  return http.put<Patch>(`${PATCH_PATH}/${id}`, input);
}

export function changePatchStatus(id: string, status: LifecycleStatus): Promise<Patch> {
  return http.patch<Patch>(`${PATCH_PATH}/${id}/status`, undefined, { status });
}

export function deletePatch(id: string): Promise<void> {
  return http.delete(`${PATCH_PATH}/${id}`);
}

export function bulkCreatePatchs(inputs: PatchInput[]): Promise<BulkResult<Patch>> {
  return http.post<BulkResult<Patch>>(`${PATCH_PATH}/bulk`, inputs);
}

export function getPatchStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${PATCH_PATH}/stats/status`);
}

export function exportPatchsCsv(filter: PatchFilter): Promise<Blob> {
  return http.blob(`${PATCH_PATH}/export`, toPatchQuery(filter));
}

export function listPatchByVersionId(versionId: string, page = 0, size = 20): Promise<Page<Patch>> {
  return http.get<Page<Patch>>(`${PATCH_PATH}/by-version-id/${versionId}`, { page, size });
}
