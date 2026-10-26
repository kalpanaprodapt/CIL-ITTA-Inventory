import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ROUTETARGET_PATH } from './types';
import type { RouteTarget, RouteTargetFilter, RouteTargetInput, RouteTargetSummary, RouteTargetUpdateInput } from './types';
import { toRouteTargetQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRouteTargets(
  filter: RouteTargetFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<RouteTarget>> {
  return http.get<Page<RouteTarget>>(ROUTETARGET_PATH, { ...toRouteTargetQuery(filter), page, size, sort });
}

export function getRouteTarget(id: string): Promise<RouteTarget> {
  return http.get<RouteTarget>(`${ROUTETARGET_PATH}/${id}`);
}

export function getRouteTargetByCode(code: string): Promise<RouteTarget> {
  return http.get<RouteTarget>(`${ROUTETARGET_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRouteTargetSummaries(status?: LifecycleStatus): Promise<RouteTargetSummary[]> {
  return http.get<RouteTargetSummary[]>(`${ROUTETARGET_PATH}/summaries`, { status });
}

export function createRouteTarget(input: RouteTargetInput): Promise<RouteTarget> {
  return http.post<RouteTarget>(ROUTETARGET_PATH, input);
}

export function updateRouteTarget(id: string, input: RouteTargetUpdateInput): Promise<RouteTarget> {
  return http.put<RouteTarget>(`${ROUTETARGET_PATH}/${id}`, input);
}

export function changeRouteTargetStatus(id: string, status: LifecycleStatus): Promise<RouteTarget> {
  return http.patch<RouteTarget>(`${ROUTETARGET_PATH}/${id}/status`, undefined, { status });
}

export function deleteRouteTarget(id: string): Promise<void> {
  return http.delete(`${ROUTETARGET_PATH}/${id}`);
}

export function bulkCreateRouteTargets(inputs: RouteTargetInput[]): Promise<BulkResult<RouteTarget>> {
  return http.post<BulkResult<RouteTarget>>(`${ROUTETARGET_PATH}/bulk`, inputs);
}

export function getRouteTargetStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ROUTETARGET_PATH}/stats/status`);
}

export function exportRouteTargetsCsv(filter: RouteTargetFilter): Promise<Blob> {
  return http.blob(`${ROUTETARGET_PATH}/export`, toRouteTargetQuery(filter));
}

export function listRouteTargetByVrfId(vrfId: string, page = 0, size = 20): Promise<Page<RouteTarget>> {
  return http.get<Page<RouteTarget>>(`${ROUTETARGET_PATH}/by-vrf-id/${vrfId}`, { page, size });
}
