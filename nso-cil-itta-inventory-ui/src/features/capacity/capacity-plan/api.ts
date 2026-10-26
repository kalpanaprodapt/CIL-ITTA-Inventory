import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CAPACITYPLAN_PATH } from './types';
import type { CapacityPlan, CapacityPlanFilter, CapacityPlanInput, CapacityPlanSummary, CapacityPlanUpdateInput } from './types';
import { toCapacityPlanQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCapacityPlans(
  filter: CapacityPlanFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<CapacityPlan>> {
  return http.get<Page<CapacityPlan>>(CAPACITYPLAN_PATH, { ...toCapacityPlanQuery(filter), page, size, sort });
}

export function getCapacityPlan(id: string): Promise<CapacityPlan> {
  return http.get<CapacityPlan>(`${CAPACITYPLAN_PATH}/${id}`);
}

export function getCapacityPlanByCode(code: string): Promise<CapacityPlan> {
  return http.get<CapacityPlan>(`${CAPACITYPLAN_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCapacityPlanSummaries(status?: LifecycleStatus): Promise<CapacityPlanSummary[]> {
  return http.get<CapacityPlanSummary[]>(`${CAPACITYPLAN_PATH}/summaries`, { status });
}

export function createCapacityPlan(input: CapacityPlanInput): Promise<CapacityPlan> {
  return http.post<CapacityPlan>(CAPACITYPLAN_PATH, input);
}

export function updateCapacityPlan(id: string, input: CapacityPlanUpdateInput): Promise<CapacityPlan> {
  return http.put<CapacityPlan>(`${CAPACITYPLAN_PATH}/${id}`, input);
}

export function changeCapacityPlanStatus(id: string, status: LifecycleStatus): Promise<CapacityPlan> {
  return http.patch<CapacityPlan>(`${CAPACITYPLAN_PATH}/${id}/status`, undefined, { status });
}

export function deleteCapacityPlan(id: string): Promise<void> {
  return http.delete(`${CAPACITYPLAN_PATH}/${id}`);
}

export function bulkCreateCapacityPlans(inputs: CapacityPlanInput[]): Promise<BulkResult<CapacityPlan>> {
  return http.post<BulkResult<CapacityPlan>>(`${CAPACITYPLAN_PATH}/bulk`, inputs);
}

export function getCapacityPlanStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CAPACITYPLAN_PATH}/stats/status`);
}

export function exportCapacityPlansCsv(filter: CapacityPlanFilter): Promise<Blob> {
  return http.blob(`${CAPACITYPLAN_PATH}/export`, toCapacityPlanQuery(filter));
}
