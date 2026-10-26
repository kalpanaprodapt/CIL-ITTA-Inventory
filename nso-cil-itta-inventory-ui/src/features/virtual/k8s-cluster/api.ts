import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { K8SCLUSTER_PATH } from './types';
import type { K8sCluster, K8sClusterFilter, K8sClusterInput, K8sClusterSummary, K8sClusterUpdateInput } from './types';
import { toK8sClusterQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchK8sClusters(
  filter: K8sClusterFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<K8sCluster>> {
  return http.get<Page<K8sCluster>>(K8SCLUSTER_PATH, { ...toK8sClusterQuery(filter), page, size, sort });
}

export function getK8sCluster(id: string): Promise<K8sCluster> {
  return http.get<K8sCluster>(`${K8SCLUSTER_PATH}/${id}`);
}

export function getK8sClusterByCode(code: string): Promise<K8sCluster> {
  return http.get<K8sCluster>(`${K8SCLUSTER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listK8sClusterSummaries(status?: LifecycleStatus): Promise<K8sClusterSummary[]> {
  return http.get<K8sClusterSummary[]>(`${K8SCLUSTER_PATH}/summaries`, { status });
}

export function createK8sCluster(input: K8sClusterInput): Promise<K8sCluster> {
  return http.post<K8sCluster>(K8SCLUSTER_PATH, input);
}

export function updateK8sCluster(id: string, input: K8sClusterUpdateInput): Promise<K8sCluster> {
  return http.put<K8sCluster>(`${K8SCLUSTER_PATH}/${id}`, input);
}

export function changeK8sClusterStatus(id: string, status: LifecycleStatus): Promise<K8sCluster> {
  return http.patch<K8sCluster>(`${K8SCLUSTER_PATH}/${id}/status`, undefined, { status });
}

export function deleteK8sCluster(id: string): Promise<void> {
  return http.delete(`${K8SCLUSTER_PATH}/${id}`);
}

export function bulkCreateK8sClusters(inputs: K8sClusterInput[]): Promise<BulkResult<K8sCluster>> {
  return http.post<BulkResult<K8sCluster>>(`${K8SCLUSTER_PATH}/bulk`, inputs);
}

export function getK8sClusterStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${K8SCLUSTER_PATH}/stats/status`);
}

export function exportK8sClustersCsv(filter: K8sClusterFilter): Promise<Blob> {
  return http.blob(`${K8SCLUSTER_PATH}/export`, toK8sClusterQuery(filter));
}

export function listK8sClusterByCloudAccountId(cloudAccountId: string, page = 0, size = 20): Promise<Page<K8sCluster>> {
  return http.get<Page<K8sCluster>>(`${K8SCLUSTER_PATH}/by-cloud-account-id/${cloudAccountId}`, { page, size });
}
