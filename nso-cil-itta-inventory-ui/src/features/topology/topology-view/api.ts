import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TOPOLOGYVIEW_PATH } from './types';
import type { TopologyView, TopologyViewFilter, TopologyViewInput, TopologyViewSummary, TopologyViewUpdateInput } from './types';
import { toTopologyViewQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTopologyViews(
  filter: TopologyViewFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<TopologyView>> {
  return http.get<Page<TopologyView>>(TOPOLOGYVIEW_PATH, { ...toTopologyViewQuery(filter), page, size, sort });
}

export function getTopologyView(id: string): Promise<TopologyView> {
  return http.get<TopologyView>(`${TOPOLOGYVIEW_PATH}/${id}`);
}

export function getTopologyViewByCode(code: string): Promise<TopologyView> {
  return http.get<TopologyView>(`${TOPOLOGYVIEW_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTopologyViewSummaries(status?: LifecycleStatus): Promise<TopologyViewSummary[]> {
  return http.get<TopologyViewSummary[]>(`${TOPOLOGYVIEW_PATH}/summaries`, { status });
}

export function createTopologyView(input: TopologyViewInput): Promise<TopologyView> {
  return http.post<TopologyView>(TOPOLOGYVIEW_PATH, input);
}

export function updateTopologyView(id: string, input: TopologyViewUpdateInput): Promise<TopologyView> {
  return http.put<TopologyView>(`${TOPOLOGYVIEW_PATH}/${id}`, input);
}

export function changeTopologyViewStatus(id: string, status: LifecycleStatus): Promise<TopologyView> {
  return http.patch<TopologyView>(`${TOPOLOGYVIEW_PATH}/${id}/status`, undefined, { status });
}

export function deleteTopologyView(id: string): Promise<void> {
  return http.delete(`${TOPOLOGYVIEW_PATH}/${id}`);
}

export function bulkCreateTopologyViews(inputs: TopologyViewInput[]): Promise<BulkResult<TopologyView>> {
  return http.post<BulkResult<TopologyView>>(`${TOPOLOGYVIEW_PATH}/bulk`, inputs);
}

export function getTopologyViewStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TOPOLOGYVIEW_PATH}/stats/status`);
}

export function exportTopologyViewsCsv(filter: TopologyViewFilter): Promise<Blob> {
  return http.blob(`${TOPOLOGYVIEW_PATH}/export`, toTopologyViewQuery(filter));
}
