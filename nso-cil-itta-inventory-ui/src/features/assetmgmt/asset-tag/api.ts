import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ASSETTAG_PATH } from './types';
import type { AssetTag, AssetTagFilter, AssetTagInput, AssetTagSummary, AssetTagUpdateInput } from './types';
import { toAssetTagQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAssetTags(
  filter: AssetTagFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<AssetTag>> {
  return http.get<Page<AssetTag>>(ASSETTAG_PATH, { ...toAssetTagQuery(filter), page, size, sort });
}

export function getAssetTag(id: string): Promise<AssetTag> {
  return http.get<AssetTag>(`${ASSETTAG_PATH}/${id}`);
}

export function getAssetTagByCode(code: string): Promise<AssetTag> {
  return http.get<AssetTag>(`${ASSETTAG_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAssetTagSummaries(status?: LifecycleStatus): Promise<AssetTagSummary[]> {
  return http.get<AssetTagSummary[]>(`${ASSETTAG_PATH}/summaries`, { status });
}

export function createAssetTag(input: AssetTagInput): Promise<AssetTag> {
  return http.post<AssetTag>(ASSETTAG_PATH, input);
}

export function updateAssetTag(id: string, input: AssetTagUpdateInput): Promise<AssetTag> {
  return http.put<AssetTag>(`${ASSETTAG_PATH}/${id}`, input);
}

export function changeAssetTagStatus(id: string, status: LifecycleStatus): Promise<AssetTag> {
  return http.patch<AssetTag>(`${ASSETTAG_PATH}/${id}/status`, undefined, { status });
}

export function deleteAssetTag(id: string): Promise<void> {
  return http.delete(`${ASSETTAG_PATH}/${id}`);
}

export function bulkCreateAssetTags(inputs: AssetTagInput[]): Promise<BulkResult<AssetTag>> {
  return http.post<BulkResult<AssetTag>>(`${ASSETTAG_PATH}/bulk`, inputs);
}

export function getAssetTagStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ASSETTAG_PATH}/stats/status`);
}

export function exportAssetTagsCsv(filter: AssetTagFilter): Promise<Blob> {
  return http.blob(`${ASSETTAG_PATH}/export`, toAssetTagQuery(filter));
}

export function listAssetTagByAssetId(assetId: string, page = 0, size = 20): Promise<Page<AssetTag>> {
  return http.get<Page<AssetTag>>(`${ASSETTAG_PATH}/by-asset-id/${assetId}`, { page, size });
}
