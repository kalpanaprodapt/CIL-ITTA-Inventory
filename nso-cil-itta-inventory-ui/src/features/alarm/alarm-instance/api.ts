import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ALARMINSTANCE_PATH } from './types';
import type { AlarmInstance, AlarmInstanceFilter, AlarmInstanceInput, AlarmInstanceSummary, AlarmInstanceUpdateInput } from './types';
import { toAlarmInstanceQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAlarmInstances(
  filter: AlarmInstanceFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<AlarmInstance>> {
  return http.get<Page<AlarmInstance>>(ALARMINSTANCE_PATH, { ...toAlarmInstanceQuery(filter), page, size, sort });
}

export function getAlarmInstance(id: string): Promise<AlarmInstance> {
  return http.get<AlarmInstance>(`${ALARMINSTANCE_PATH}/${id}`);
}

export function getAlarmInstanceByCode(code: string): Promise<AlarmInstance> {
  return http.get<AlarmInstance>(`${ALARMINSTANCE_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAlarmInstanceSummaries(status?: LifecycleStatus): Promise<AlarmInstanceSummary[]> {
  return http.get<AlarmInstanceSummary[]>(`${ALARMINSTANCE_PATH}/summaries`, { status });
}

export function createAlarmInstance(input: AlarmInstanceInput): Promise<AlarmInstance> {
  return http.post<AlarmInstance>(ALARMINSTANCE_PATH, input);
}

export function updateAlarmInstance(id: string, input: AlarmInstanceUpdateInput): Promise<AlarmInstance> {
  return http.put<AlarmInstance>(`${ALARMINSTANCE_PATH}/${id}`, input);
}

export function changeAlarmInstanceStatus(id: string, status: LifecycleStatus): Promise<AlarmInstance> {
  return http.patch<AlarmInstance>(`${ALARMINSTANCE_PATH}/${id}/status`, undefined, { status });
}

export function deleteAlarmInstance(id: string): Promise<void> {
  return http.delete(`${ALARMINSTANCE_PATH}/${id}`);
}

export function bulkCreateAlarmInstances(inputs: AlarmInstanceInput[]): Promise<BulkResult<AlarmInstance>> {
  return http.post<BulkResult<AlarmInstance>>(`${ALARMINSTANCE_PATH}/bulk`, inputs);
}

export function getAlarmInstanceStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ALARMINSTANCE_PATH}/stats/status`);
}

export function exportAlarmInstancesCsv(filter: AlarmInstanceFilter): Promise<Blob> {
  return http.blob(`${ALARMINSTANCE_PATH}/export`, toAlarmInstanceQuery(filter));
}

export function listAlarmInstanceByDefinitionId(definitionId: string, page = 0, size = 20): Promise<Page<AlarmInstance>> {
  return http.get<Page<AlarmInstance>>(`${ALARMINSTANCE_PATH}/by-definition-id/${definitionId}`, { page, size });
}
