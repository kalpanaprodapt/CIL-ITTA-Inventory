import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { WORKORDER_PATH } from './types';
import type { WorkOrder, WorkOrderFilter, WorkOrderInput, WorkOrderSummary, WorkOrderUpdateInput } from './types';
import { toWorkOrderQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchWorkOrders(
  filter: WorkOrderFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<WorkOrder>> {
  return http.get<Page<WorkOrder>>(WORKORDER_PATH, { ...toWorkOrderQuery(filter), page, size, sort });
}

export function getWorkOrder(id: string): Promise<WorkOrder> {
  return http.get<WorkOrder>(`${WORKORDER_PATH}/${id}`);
}

export function getWorkOrderByCode(code: string): Promise<WorkOrder> {
  return http.get<WorkOrder>(`${WORKORDER_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listWorkOrderSummaries(status?: LifecycleStatus): Promise<WorkOrderSummary[]> {
  return http.get<WorkOrderSummary[]>(`${WORKORDER_PATH}/summaries`, { status });
}

export function createWorkOrder(input: WorkOrderInput): Promise<WorkOrder> {
  return http.post<WorkOrder>(WORKORDER_PATH, input);
}

export function updateWorkOrder(id: string, input: WorkOrderUpdateInput): Promise<WorkOrder> {
  return http.put<WorkOrder>(`${WORKORDER_PATH}/${id}`, input);
}

export function changeWorkOrderStatus(id: string, status: LifecycleStatus): Promise<WorkOrder> {
  return http.patch<WorkOrder>(`${WORKORDER_PATH}/${id}/status`, undefined, { status });
}

export function deleteWorkOrder(id: string): Promise<void> {
  return http.delete(`${WORKORDER_PATH}/${id}`);
}

export function bulkCreateWorkOrders(inputs: WorkOrderInput[]): Promise<BulkResult<WorkOrder>> {
  return http.post<BulkResult<WorkOrder>>(`${WORKORDER_PATH}/bulk`, inputs);
}

export function getWorkOrderStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${WORKORDER_PATH}/stats/status`);
}

export function exportWorkOrdersCsv(filter: WorkOrderFilter): Promise<Blob> {
  return http.blob(`${WORKORDER_PATH}/export`, toWorkOrderQuery(filter));
}

export function listWorkOrderByAssignedTechnicianId(assignedTechnicianId: string, page = 0, size = 20): Promise<Page<WorkOrder>> {
  return http.get<Page<WorkOrder>>(`${WORKORDER_PATH}/by-assigned-technician-id/${assignedTechnicianId}`, { page, size });
}
