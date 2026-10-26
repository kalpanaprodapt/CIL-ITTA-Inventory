import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { VRFINSTANCE_PATH } from './types';
import type { VrfInstance, VrfInstanceFilter, VrfInstanceInput, VrfInstanceSummary, VrfInstanceUpdateInput } from './types';
import { toVrfInstanceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchVrfInstances(
  filter: VrfInstanceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<VrfInstance>> {
  return http.get<Page<VrfInstance>>(VRFINSTANCE_PATH, { ...toVrfInstanceQuery(filter), page, size, sort });
}

export function getVrfInstance(id: string): Promise<VrfInstance> {
  return http.get<VrfInstance>(`${VRFINSTANCE_PATH}/${id}`);
}

export function getVrfInstanceByCode(code: string): Promise<VrfInstance> {
  return http.get<VrfInstance>(`${VRFINSTANCE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listVrfInstanceSummaries(status?: LifecycleStatus): Promise<VrfInstanceSummary[]> {
  return http.get<VrfInstanceSummary[]>(`${VRFINSTANCE_PATH}/summaries`, { status });
}

export function createVrfInstance(input: VrfInstanceInput): Promise<VrfInstance> {
  return http.post<VrfInstance>(VRFINSTANCE_PATH, input);
}

export function updateVrfInstance(id: string, input: VrfInstanceUpdateInput): Promise<VrfInstance> {
  return http.put<VrfInstance>(`${VRFINSTANCE_PATH}/${id}`, input);
}

export function changeVrfInstanceStatus(id: string, status: LifecycleStatus): Promise<VrfInstance> {
  return http.patch<VrfInstance>(`${VRFINSTANCE_PATH}/${id}/status`, undefined, { status });
}

export function deleteVrfInstance(id: string): Promise<void> {
  return http.delete(`${VRFINSTANCE_PATH}/${id}`);
}

export function bulkCreateVrfInstances(inputs: VrfInstanceInput[]): Promise<BulkResult<VrfInstance>> {
  return http.post<BulkResult<VrfInstance>>(`${VRFINSTANCE_PATH}/bulk`, inputs);
}

export function getVrfInstanceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${VRFINSTANCE_PATH}/stats/status`);
}

export function exportVrfInstancesCsv(filter: VrfInstanceFilter): Promise<Blob> {
  return http.blob(`${VRFINSTANCE_PATH}/export`, toVrfInstanceQuery(filter));
}

export function listVrfInstanceByDeviceId(deviceId: string, page = 0, size = 20): Promise<Page<VrfInstance>> {
  return http.get<Page<VrfInstance>>(`${VRFINSTANCE_PATH}/by-device-id/${deviceId}`, { page, size });
}
