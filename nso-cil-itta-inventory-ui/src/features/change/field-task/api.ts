import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { FIELDTASK_PATH } from './types';
import type { FieldTask, FieldTaskFilter, FieldTaskInput, FieldTaskSummary, FieldTaskUpdateInput } from './types';
import { toFieldTaskQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchFieldTasks(
  filter: FieldTaskFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<FieldTask>> {
  return http.get<Page<FieldTask>>(FIELDTASK_PATH, { ...toFieldTaskQuery(filter), page, size, sort });
}

export function getFieldTask(id: string): Promise<FieldTask> {
  return http.get<FieldTask>(`${FIELDTASK_PATH}/${id}`);
}

export function getFieldTaskByCode(code: string): Promise<FieldTask> {
  return http.get<FieldTask>(`${FIELDTASK_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listFieldTaskSummaries(status?: LifecycleStatus): Promise<FieldTaskSummary[]> {
  return http.get<FieldTaskSummary[]>(`${FIELDTASK_PATH}/summaries`, { status });
}

export function createFieldTask(input: FieldTaskInput): Promise<FieldTask> {
  return http.post<FieldTask>(FIELDTASK_PATH, input);
}

export function updateFieldTask(id: string, input: FieldTaskUpdateInput): Promise<FieldTask> {
  return http.put<FieldTask>(`${FIELDTASK_PATH}/${id}`, input);
}

export function changeFieldTaskStatus(id: string, status: LifecycleStatus): Promise<FieldTask> {
  return http.patch<FieldTask>(`${FIELDTASK_PATH}/${id}/status`, undefined, { status });
}

export function deleteFieldTask(id: string): Promise<void> {
  return http.delete(`${FIELDTASK_PATH}/${id}`);
}

export function bulkCreateFieldTasks(inputs: FieldTaskInput[]): Promise<BulkResult<FieldTask>> {
  return http.post<BulkResult<FieldTask>>(`${FIELDTASK_PATH}/bulk`, inputs);
}

export function getFieldTaskStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${FIELDTASK_PATH}/stats/status`);
}

export function exportFieldTasksCsv(filter: FieldTaskFilter): Promise<Blob> {
  return http.blob(`${FIELDTASK_PATH}/export`, toFieldTaskQuery(filter));
}

export function listFieldTaskByWorkOrderId(workOrderId: string, page = 0, size = 20): Promise<Page<FieldTask>> {
  return http.get<Page<FieldTask>>(`${FIELDTASK_PATH}/by-work-order-id/${workOrderId}`, { page, size });
}
