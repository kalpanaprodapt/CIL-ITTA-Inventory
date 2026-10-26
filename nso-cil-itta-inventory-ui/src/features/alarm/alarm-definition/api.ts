import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ALARMDEFINITION_PATH } from './types';
import type { AlarmDefinition, AlarmDefinitionFilter, AlarmDefinitionInput, AlarmDefinitionSummary, AlarmDefinitionUpdateInput } from './types';
import { toAlarmDefinitionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchAlarmDefinitions(
  filter: AlarmDefinitionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<AlarmDefinition>> {
  return http.get<Page<AlarmDefinition>>(ALARMDEFINITION_PATH, { ...toAlarmDefinitionQuery(filter), page, size, sort });
}

export function getAlarmDefinition(id: string): Promise<AlarmDefinition> {
  return http.get<AlarmDefinition>(`${ALARMDEFINITION_PATH}/${id}`);
}

export function getAlarmDefinitionByCode(code: string): Promise<AlarmDefinition> {
  return http.get<AlarmDefinition>(`${ALARMDEFINITION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listAlarmDefinitionSummaries(status?: LifecycleStatus): Promise<AlarmDefinitionSummary[]> {
  return http.get<AlarmDefinitionSummary[]>(`${ALARMDEFINITION_PATH}/summaries`, { status });
}

export function createAlarmDefinition(input: AlarmDefinitionInput): Promise<AlarmDefinition> {
  return http.post<AlarmDefinition>(ALARMDEFINITION_PATH, input);
}

export function updateAlarmDefinition(id: string, input: AlarmDefinitionUpdateInput): Promise<AlarmDefinition> {
  return http.put<AlarmDefinition>(`${ALARMDEFINITION_PATH}/${id}`, input);
}

export function changeAlarmDefinitionStatus(id: string, status: LifecycleStatus): Promise<AlarmDefinition> {
  return http.patch<AlarmDefinition>(`${ALARMDEFINITION_PATH}/${id}/status`, undefined, { status });
}

export function deleteAlarmDefinition(id: string): Promise<void> {
  return http.delete(`${ALARMDEFINITION_PATH}/${id}`);
}

export function bulkCreateAlarmDefinitions(inputs: AlarmDefinitionInput[]): Promise<BulkResult<AlarmDefinition>> {
  return http.post<BulkResult<AlarmDefinition>>(`${ALARMDEFINITION_PATH}/bulk`, inputs);
}

export function getAlarmDefinitionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ALARMDEFINITION_PATH}/stats/status`);
}

export function exportAlarmDefinitionsCsv(filter: AlarmDefinitionFilter): Promise<Blob> {
  return http.blob(`${ALARMDEFINITION_PATH}/export`, toAlarmDefinitionQuery(filter));
}
