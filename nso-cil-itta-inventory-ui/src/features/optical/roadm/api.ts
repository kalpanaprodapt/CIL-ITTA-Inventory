import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ROADM_PATH } from './types';
import type { Roadm, RoadmFilter, RoadmInput, RoadmSummary, RoadmUpdateInput } from './types';
import { toRoadmQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRoadms(
  filter: RoadmFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Roadm>> {
  return http.get<Page<Roadm>>(ROADM_PATH, { ...toRoadmQuery(filter), page, size, sort });
}

export function getRoadm(id: string): Promise<Roadm> {
  return http.get<Roadm>(`${ROADM_PATH}/${id}`);
}

export function getRoadmByCode(code: string): Promise<Roadm> {
  return http.get<Roadm>(`${ROADM_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRoadmSummaries(status?: LifecycleStatus): Promise<RoadmSummary[]> {
  return http.get<RoadmSummary[]>(`${ROADM_PATH}/summaries`, { status });
}

export function createRoadm(input: RoadmInput): Promise<Roadm> {
  return http.post<Roadm>(ROADM_PATH, input);
}

export function updateRoadm(id: string, input: RoadmUpdateInput): Promise<Roadm> {
  return http.put<Roadm>(`${ROADM_PATH}/${id}`, input);
}

export function changeRoadmStatus(id: string, status: LifecycleStatus): Promise<Roadm> {
  return http.patch<Roadm>(`${ROADM_PATH}/${id}/status`, undefined, { status });
}

export function deleteRoadm(id: string): Promise<void> {
  return http.delete(`${ROADM_PATH}/${id}`);
}

export function bulkCreateRoadms(inputs: RoadmInput[]): Promise<BulkResult<Roadm>> {
  return http.post<BulkResult<Roadm>>(`${ROADM_PATH}/bulk`, inputs);
}

export function getRoadmStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ROADM_PATH}/stats/status`);
}

export function exportRoadmsCsv(filter: RoadmFilter): Promise<Blob> {
  return http.blob(`${ROADM_PATH}/export`, toRoadmQuery(filter));
}

export function listRoadmBySystemId(systemId: string, page = 0, size = 20): Promise<Page<Roadm>> {
  return http.get<Page<Roadm>>(`${ROADM_PATH}/by-system-id/${systemId}`, { page, size });
}
