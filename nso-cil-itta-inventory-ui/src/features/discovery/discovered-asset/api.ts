import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { DISCOVEREDASSET_PATH } from './types';
import type { DiscoveredAsset, DiscoveredAssetFilter, DiscoveredAssetInput, DiscoveredAssetSummary, DiscoveredAssetUpdateInput } from './types';
import { toDiscoveredAssetQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchDiscoveredAssets(
  filter: DiscoveredAssetFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<DiscoveredAsset>> {
  return http.get<Page<DiscoveredAsset>>(DISCOVEREDASSET_PATH, { ...toDiscoveredAssetQuery(filter), page, size, sort });
}

export function getDiscoveredAsset(id: string): Promise<DiscoveredAsset> {
  return http.get<DiscoveredAsset>(`${DISCOVEREDASSET_PATH}/${id}`);
}

export function getDiscoveredAssetByCode(code: string): Promise<DiscoveredAsset> {
  return http.get<DiscoveredAsset>(`${DISCOVEREDASSET_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listDiscoveredAssetSummaries(status?: LifecycleStatus): Promise<DiscoveredAssetSummary[]> {
  return http.get<DiscoveredAssetSummary[]>(`${DISCOVEREDASSET_PATH}/summaries`, { status });
}

export function createDiscoveredAsset(input: DiscoveredAssetInput): Promise<DiscoveredAsset> {
  return http.post<DiscoveredAsset>(DISCOVEREDASSET_PATH, input);
}

export function updateDiscoveredAsset(id: string, input: DiscoveredAssetUpdateInput): Promise<DiscoveredAsset> {
  return http.put<DiscoveredAsset>(`${DISCOVEREDASSET_PATH}/${id}`, input);
}

export function changeDiscoveredAssetStatus(id: string, status: LifecycleStatus): Promise<DiscoveredAsset> {
  return http.patch<DiscoveredAsset>(`${DISCOVEREDASSET_PATH}/${id}/status`, undefined, { status });
}

export function deleteDiscoveredAsset(id: string): Promise<void> {
  return http.delete(`${DISCOVEREDASSET_PATH}/${id}`);
}

export function bulkCreateDiscoveredAssets(inputs: DiscoveredAssetInput[]): Promise<BulkResult<DiscoveredAsset>> {
  return http.post<BulkResult<DiscoveredAsset>>(`${DISCOVEREDASSET_PATH}/bulk`, inputs);
}

export function getDiscoveredAssetStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${DISCOVEREDASSET_PATH}/stats/status`);
}

export function exportDiscoveredAssetsCsv(filter: DiscoveredAssetFilter): Promise<Blob> {
  return http.blob(`${DISCOVEREDASSET_PATH}/export`, toDiscoveredAssetQuery(filter));
}

export function listDiscoveredAssetByJobId(jobId: string, page = 0, size = 20): Promise<Page<DiscoveredAsset>> {
  return http.get<Page<DiscoveredAsset>>(`${DISCOVEREDASSET_PATH}/by-job-id/${jobId}`, { page, size });
}

export function listDiscoveredAssetByMatchedAssetId(matchedAssetId: string, page = 0, size = 20): Promise<Page<DiscoveredAsset>> {
  return http.get<Page<DiscoveredAsset>>(`${DISCOVEREDASSET_PATH}/by-matched-asset-id/${matchedAssetId}`, { page, size });
}
