import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { TECHNICIAN_PATH } from './types';
import type { Technician, TechnicianFilter, TechnicianInput, TechnicianSummary, TechnicianUpdateInput } from './types';
import { toTechnicianQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchTechnicians(
  filter: TechnicianFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Technician>> {
  return http.get<Page<Technician>>(TECHNICIAN_PATH, { ...toTechnicianQuery(filter), page, size, sort });
}

export function getTechnician(id: string): Promise<Technician> {
  return http.get<Technician>(`${TECHNICIAN_PATH}/${id}`);
}

export function getTechnicianByCode(code: string): Promise<Technician> {
  return http.get<Technician>(`${TECHNICIAN_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listTechnicianSummaries(status?: LifecycleStatus): Promise<TechnicianSummary[]> {
  return http.get<TechnicianSummary[]>(`${TECHNICIAN_PATH}/summaries`, { status });
}

export function createTechnician(input: TechnicianInput): Promise<Technician> {
  return http.post<Technician>(TECHNICIAN_PATH, input);
}

export function updateTechnician(id: string, input: TechnicianUpdateInput): Promise<Technician> {
  return http.put<Technician>(`${TECHNICIAN_PATH}/${id}`, input);
}

export function changeTechnicianStatus(id: string, status: LifecycleStatus): Promise<Technician> {
  return http.patch<Technician>(`${TECHNICIAN_PATH}/${id}/status`, undefined, { status });
}

export function deleteTechnician(id: string): Promise<void> {
  return http.delete(`${TECHNICIAN_PATH}/${id}`);
}

export function bulkCreateTechnicians(inputs: TechnicianInput[]): Promise<BulkResult<Technician>> {
  return http.post<BulkResult<Technician>>(`${TECHNICIAN_PATH}/bulk`, inputs);
}

export function getTechnicianStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${TECHNICIAN_PATH}/stats/status`);
}

export function exportTechniciansCsv(filter: TechnicianFilter): Promise<Blob> {
  return http.blob(`${TECHNICIAN_PATH}/export`, toTechnicianQuery(filter));
}

export function listTechnicianByHomeSiteId(homeSiteId: string, page = 0, size = 20): Promise<Page<Technician>> {
  return http.get<Page<Technician>>(`${TECHNICIAN_PATH}/by-home-site-id/${homeSiteId}`, { page, size });
}
