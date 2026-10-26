import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { MAINTENANCEWINDOW_PATH } from './types';
import type { MaintenanceWindow, MaintenanceWindowFilter, MaintenanceWindowInput, MaintenanceWindowSummary, MaintenanceWindowUpdateInput } from './types';
import { toMaintenanceWindowQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchMaintenanceWindows(
  filter: MaintenanceWindowFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<MaintenanceWindow>> {
  return http.get<Page<MaintenanceWindow>>(MAINTENANCEWINDOW_PATH, { ...toMaintenanceWindowQuery(filter), page, size, sort });
}

export function getMaintenanceWindow(id: string): Promise<MaintenanceWindow> {
  return http.get<MaintenanceWindow>(`${MAINTENANCEWINDOW_PATH}/${id}`);
}

export function getMaintenanceWindowByCode(code: string): Promise<MaintenanceWindow> {
  return http.get<MaintenanceWindow>(`${MAINTENANCEWINDOW_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listMaintenanceWindowSummaries(status?: LifecycleStatus): Promise<MaintenanceWindowSummary[]> {
  return http.get<MaintenanceWindowSummary[]>(`${MAINTENANCEWINDOW_PATH}/summaries`, { status });
}

export function createMaintenanceWindow(input: MaintenanceWindowInput): Promise<MaintenanceWindow> {
  return http.post<MaintenanceWindow>(MAINTENANCEWINDOW_PATH, input);
}

export function updateMaintenanceWindow(id: string, input: MaintenanceWindowUpdateInput): Promise<MaintenanceWindow> {
  return http.put<MaintenanceWindow>(`${MAINTENANCEWINDOW_PATH}/${id}`, input);
}

export function changeMaintenanceWindowStatus(id: string, status: LifecycleStatus): Promise<MaintenanceWindow> {
  return http.patch<MaintenanceWindow>(`${MAINTENANCEWINDOW_PATH}/${id}/status`, undefined, { status });
}

export function deleteMaintenanceWindow(id: string): Promise<void> {
  return http.delete(`${MAINTENANCEWINDOW_PATH}/${id}`);
}

export function bulkCreateMaintenanceWindows(inputs: MaintenanceWindowInput[]): Promise<BulkResult<MaintenanceWindow>> {
  return http.post<BulkResult<MaintenanceWindow>>(`${MAINTENANCEWINDOW_PATH}/bulk`, inputs);
}

export function getMaintenanceWindowStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${MAINTENANCEWINDOW_PATH}/stats/status`);
}

export function exportMaintenanceWindowsCsv(filter: MaintenanceWindowFilter): Promise<Blob> {
  return http.blob(`${MAINTENANCEWINDOW_PATH}/export`, toMaintenanceWindowQuery(filter));
}
