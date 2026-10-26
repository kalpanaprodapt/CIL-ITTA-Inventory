import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { QOSPOLICY_PATH } from './types';
import type { QosPolicy, QosPolicyFilter, QosPolicyInput, QosPolicySummary, QosPolicyUpdateInput } from './types';
import { toQosPolicyQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchQosPolicys(
  filter: QosPolicyFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<QosPolicy>> {
  return http.get<Page<QosPolicy>>(QOSPOLICY_PATH, { ...toQosPolicyQuery(filter), page, size, sort });
}

export function getQosPolicy(id: string): Promise<QosPolicy> {
  return http.get<QosPolicy>(`${QOSPOLICY_PATH}/${id}`);
}

export function getQosPolicyByCode(code: string): Promise<QosPolicy> {
  return http.get<QosPolicy>(`${QOSPOLICY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listQosPolicySummaries(status?: LifecycleStatus): Promise<QosPolicySummary[]> {
  return http.get<QosPolicySummary[]>(`${QOSPOLICY_PATH}/summaries`, { status });
}

export function createQosPolicy(input: QosPolicyInput): Promise<QosPolicy> {
  return http.post<QosPolicy>(QOSPOLICY_PATH, input);
}

export function updateQosPolicy(id: string, input: QosPolicyUpdateInput): Promise<QosPolicy> {
  return http.put<QosPolicy>(`${QOSPOLICY_PATH}/${id}`, input);
}

export function changeQosPolicyStatus(id: string, status: LifecycleStatus): Promise<QosPolicy> {
  return http.patch<QosPolicy>(`${QOSPOLICY_PATH}/${id}/status`, undefined, { status });
}

export function deleteQosPolicy(id: string): Promise<void> {
  return http.delete(`${QOSPOLICY_PATH}/${id}`);
}

export function bulkCreateQosPolicys(inputs: QosPolicyInput[]): Promise<BulkResult<QosPolicy>> {
  return http.post<BulkResult<QosPolicy>>(`${QOSPOLICY_PATH}/bulk`, inputs);
}

export function getQosPolicyStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${QOSPOLICY_PATH}/stats/status`);
}

export function exportQosPolicysCsv(filter: QosPolicyFilter): Promise<Blob> {
  return http.blob(`${QOSPOLICY_PATH}/export`, toQosPolicyQuery(filter));
}
