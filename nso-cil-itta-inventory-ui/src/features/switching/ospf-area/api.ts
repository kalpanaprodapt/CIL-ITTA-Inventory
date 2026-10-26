import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { OSPFAREA_PATH } from './types';
import type { OspfArea, OspfAreaFilter, OspfAreaInput, OspfAreaSummary, OspfAreaUpdateInput } from './types';
import { toOspfAreaQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchOspfAreas(
  filter: OspfAreaFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<OspfArea>> {
  return http.get<Page<OspfArea>>(OSPFAREA_PATH, { ...toOspfAreaQuery(filter), page, size, sort });
}

export function getOspfArea(id: string): Promise<OspfArea> {
  return http.get<OspfArea>(`${OSPFAREA_PATH}/${id}`);
}

export function getOspfAreaByCode(code: string): Promise<OspfArea> {
  return http.get<OspfArea>(`${OSPFAREA_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listOspfAreaSummaries(status?: LifecycleStatus): Promise<OspfAreaSummary[]> {
  return http.get<OspfAreaSummary[]>(`${OSPFAREA_PATH}/summaries`, { status });
}

export function createOspfArea(input: OspfAreaInput): Promise<OspfArea> {
  return http.post<OspfArea>(OSPFAREA_PATH, input);
}

export function updateOspfArea(id: string, input: OspfAreaUpdateInput): Promise<OspfArea> {
  return http.put<OspfArea>(`${OSPFAREA_PATH}/${id}`, input);
}

export function changeOspfAreaStatus(id: string, status: LifecycleStatus): Promise<OspfArea> {
  return http.patch<OspfArea>(`${OSPFAREA_PATH}/${id}/status`, undefined, { status });
}

export function deleteOspfArea(id: string): Promise<void> {
  return http.delete(`${OSPFAREA_PATH}/${id}`);
}

export function bulkCreateOspfAreas(inputs: OspfAreaInput[]): Promise<BulkResult<OspfArea>> {
  return http.post<BulkResult<OspfArea>>(`${OSPFAREA_PATH}/bulk`, inputs);
}

export function getOspfAreaStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${OSPFAREA_PATH}/stats/status`);
}

export function exportOspfAreasCsv(filter: OspfAreaFilter): Promise<Blob> {
  return http.blob(`${OSPFAREA_PATH}/export`, toOspfAreaQuery(filter));
}
