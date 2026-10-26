import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ASSET_PATH } from './types';
import type { Asset, AssetFilter, AssetInput, AssetSummary, AssetUpdateInput } from './types';
import { toAssetQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAssets(
  filter: AssetFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Asset>> {
  return http.get<Page<Asset>>(ASSET_PATH, { ...toAssetQuery(filter), page, size, sort });
}

export function getAsset(id: string): Promise<Asset> {
  return http.get<Asset>(`${ASSET_PATH}/${id}`);
}

export function getAssetByCode(code: string): Promise<Asset> {
  return http.get<Asset>(`${ASSET_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAssetSummaries(status?: LifecycleStatus): Promise<AssetSummary[]> {
  return http.get<AssetSummary[]>(`${ASSET_PATH}/summaries`, { status });
}

export function createAsset(input: AssetInput): Promise<Asset> {
  return http.post<Asset>(ASSET_PATH, input);
}

export function updateAsset(id: string, input: AssetUpdateInput): Promise<Asset> {
  return http.put<Asset>(`${ASSET_PATH}/${id}`, input);
}

export function changeAssetStatus(id: string, status: LifecycleStatus): Promise<Asset> {
  return http.patch<Asset>(`${ASSET_PATH}/${id}/status`, undefined, { status });
}

export function deleteAsset(id: string): Promise<void> {
  return http.delete(`${ASSET_PATH}/${id}`);
}

export function bulkCreateAssets(inputs: AssetInput[]): Promise<BulkResult<Asset>> {
  return http.post<BulkResult<Asset>>(`${ASSET_PATH}/bulk`, inputs);
}

export function getAssetStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ASSET_PATH}/stats/status`);
}

export function exportAssetsCsv(filter: AssetFilter): Promise<Blob> {
  return http.blob(`${ASSET_PATH}/export`, toAssetQuery(filter));
}
