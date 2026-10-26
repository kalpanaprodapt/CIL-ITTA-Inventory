import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TOPOLOGYEDGE_PATH } from './types';
import type { TopologyEdge, TopologyEdgeFilter, TopologyEdgeInput, TopologyEdgeSummary, TopologyEdgeUpdateInput } from './types';
import { toTopologyEdgeQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTopologyEdges(
  filter: TopologyEdgeFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<TopologyEdge>> {
  return http.get<Page<TopologyEdge>>(TOPOLOGYEDGE_PATH, { ...toTopologyEdgeQuery(filter), page, size, sort });
}

export function getTopologyEdge(id: string): Promise<TopologyEdge> {
  return http.get<TopologyEdge>(`${TOPOLOGYEDGE_PATH}/${id}`);
}

export function getTopologyEdgeByCode(code: string): Promise<TopologyEdge> {
  return http.get<TopologyEdge>(`${TOPOLOGYEDGE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTopologyEdgeSummaries(status?: LifecycleStatus): Promise<TopologyEdgeSummary[]> {
  return http.get<TopologyEdgeSummary[]>(`${TOPOLOGYEDGE_PATH}/summaries`, { status });
}

export function createTopologyEdge(input: TopologyEdgeInput): Promise<TopologyEdge> {
  return http.post<TopologyEdge>(TOPOLOGYEDGE_PATH, input);
}

export function updateTopologyEdge(id: string, input: TopologyEdgeUpdateInput): Promise<TopologyEdge> {
  return http.put<TopologyEdge>(`${TOPOLOGYEDGE_PATH}/${id}`, input);
}

export function changeTopologyEdgeStatus(id: string, status: LifecycleStatus): Promise<TopologyEdge> {
  return http.patch<TopologyEdge>(`${TOPOLOGYEDGE_PATH}/${id}/status`, undefined, { status });
}

export function deleteTopologyEdge(id: string): Promise<void> {
  return http.delete(`${TOPOLOGYEDGE_PATH}/${id}`);
}

export function bulkCreateTopologyEdges(inputs: TopologyEdgeInput[]): Promise<BulkResult<TopologyEdge>> {
  return http.post<BulkResult<TopologyEdge>>(`${TOPOLOGYEDGE_PATH}/bulk`, inputs);
}

export function getTopologyEdgeStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TOPOLOGYEDGE_PATH}/stats/status`);
}

export function exportTopologyEdgesCsv(filter: TopologyEdgeFilter): Promise<Blob> {
  return http.blob(`${TOPOLOGYEDGE_PATH}/export`, toTopologyEdgeQuery(filter));
}

export function listTopologyEdgeByViewId(viewId: string, page = 0, size = 20): Promise<Page<TopologyEdge>> {
  return http.get<Page<TopologyEdge>>(`${TOPOLOGYEDGE_PATH}/by-view-id/${viewId}`, { page, size });
}

export function listTopologyEdgeBySourceNodeId(sourceNodeId: string, page = 0, size = 20): Promise<Page<TopologyEdge>> {
  return http.get<Page<TopologyEdge>>(`${TOPOLOGYEDGE_PATH}/by-source-node-id/${sourceNodeId}`, { page, size });
}

export function listTopologyEdgeByTargetNodeId(targetNodeId: string, page = 0, size = 20): Promise<Page<TopologyEdge>> {
  return http.get<Page<TopologyEdge>>(`${TOPOLOGYEDGE_PATH}/by-target-node-id/${targetNodeId}`, { page, size });
}
