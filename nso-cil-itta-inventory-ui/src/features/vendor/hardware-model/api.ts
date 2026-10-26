import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { HARDWAREMODEL_PATH } from './types';
import type { HardwareModel, HardwareModelFilter, HardwareModelInput, HardwareModelSummary, HardwareModelUpdateInput } from './types';
import { toHardwareModelQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchHardwareModels(
  filter: HardwareModelFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<HardwareModel>> {
  return http.get<Page<HardwareModel>>(HARDWAREMODEL_PATH, { ...toHardwareModelQuery(filter), page, size, sort });
}

export function getHardwareModel(id: string): Promise<HardwareModel> {
  return http.get<HardwareModel>(`${HARDWAREMODEL_PATH}/${id}`);
}

export function getHardwareModelByCode(code: string): Promise<HardwareModel> {
  return http.get<HardwareModel>(`${HARDWAREMODEL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listHardwareModelSummaries(status?: LifecycleStatus): Promise<HardwareModelSummary[]> {
  return http.get<HardwareModelSummary[]>(`${HARDWAREMODEL_PATH}/summaries`, { status });
}

export function createHardwareModel(input: HardwareModelInput): Promise<HardwareModel> {
  return http.post<HardwareModel>(HARDWAREMODEL_PATH, input);
}

export function updateHardwareModel(id: string, input: HardwareModelUpdateInput): Promise<HardwareModel> {
  return http.put<HardwareModel>(`${HARDWAREMODEL_PATH}/${id}`, input);
}

export function changeHardwareModelStatus(id: string, status: LifecycleStatus): Promise<HardwareModel> {
  return http.patch<HardwareModel>(`${HARDWAREMODEL_PATH}/${id}/status`, undefined, { status });
}

export function deleteHardwareModel(id: string): Promise<void> {
  return http.delete(`${HARDWAREMODEL_PATH}/${id}`);
}

export function bulkCreateHardwareModels(inputs: HardwareModelInput[]): Promise<BulkResult<HardwareModel>> {
  return http.post<BulkResult<HardwareModel>>(`${HARDWAREMODEL_PATH}/bulk`, inputs);
}

export function getHardwareModelStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${HARDWAREMODEL_PATH}/stats/status`);
}

export function exportHardwareModelsCsv(filter: HardwareModelFilter): Promise<Blob> {
  return http.blob(`${HARDWAREMODEL_PATH}/export`, toHardwareModelQuery(filter));
}

export function listHardwareModelByVendorId(vendorId: string, page = 0, size = 20): Promise<Page<HardwareModel>> {
  return http.get<Page<HardwareModel>>(`${HARDWAREMODEL_PATH}/by-vendor-id/${vendorId}`, { page, size });
}
