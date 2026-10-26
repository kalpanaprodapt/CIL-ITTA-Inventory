import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TOPOLOGYNODE_PATH } from './types';
import type { TopologyNode, TopologyNodeFilter, TopologyNodeInput, TopologyNodeSummary, TopologyNodeUpdateInput } from './types';
import { toTopologyNodeQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTopologyNodes(
  filter: TopologyNodeFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<TopologyNode>> {
  return http.get<Page<TopologyNode>>(TOPOLOGYNODE_PATH, { ...toTopologyNodeQuery(filter), page, size, sort });
}

export function getTopologyNode(id: string): Promise<TopologyNode> {
  return http.get<TopologyNode>(`${TOPOLOGYNODE_PATH}/${id}`);
}

export function getTopologyNodeByCode(code: string): Promise<TopologyNode> {
  return http.get<TopologyNode>(`${TOPOLOGYNODE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTopologyNodeSummaries(status?: LifecycleStatus): Promise<TopologyNodeSummary[]> {
  return http.get<TopologyNodeSummary[]>(`${TOPOLOGYNODE_PATH}/summaries`, { status });
}

export function createTopologyNode(input: TopologyNodeInput): Promise<TopologyNode> {
  return http.post<TopologyNode>(TOPOLOGYNODE_PATH, input);
}

export function updateTopologyNode(id: string, input: TopologyNodeUpdateInput): Promise<TopologyNode> {
  return http.put<TopologyNode>(`${TOPOLOGYNODE_PATH}/${id}`, input);
}

export function changeTopologyNodeStatus(id: string, status: LifecycleStatus): Promise<TopologyNode> {
  return http.patch<TopologyNode>(`${TOPOLOGYNODE_PATH}/${id}/status`, undefined, { status });
}

export function deleteTopologyNode(id: string): Promise<void> {
  return http.delete(`${TOPOLOGYNODE_PATH}/${id}`);
}

export function bulkCreateTopologyNodes(inputs: TopologyNodeInput[]): Promise<BulkResult<TopologyNode>> {
  return http.post<BulkResult<TopologyNode>>(`${TOPOLOGYNODE_PATH}/bulk`, inputs);
}

export function getTopologyNodeStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TOPOLOGYNODE_PATH}/stats/status`);
}

export function exportTopologyNodesCsv(filter: TopologyNodeFilter): Promise<Blob> {
  return http.blob(`${TOPOLOGYNODE_PATH}/export`, toTopologyNodeQuery(filter));
}

export function listTopologyNodeByViewId(viewId: string, page = 0, size = 20): Promise<Page<TopologyNode>> {
  return http.get<Page<TopologyNode>>(`${TOPOLOGYNODE_PATH}/by-view-id/${viewId}`, { page, size });
}
