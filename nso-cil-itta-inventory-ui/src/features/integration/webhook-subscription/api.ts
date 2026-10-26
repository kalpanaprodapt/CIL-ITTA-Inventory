import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { WEBHOOKSUBSCRIPTION_PATH } from './types';
import type { WebhookSubscription, WebhookSubscriptionFilter, WebhookSubscriptionInput, WebhookSubscriptionSummary, WebhookSubscriptionUpdateInput } from './types';
import { toWebhookSubscriptionQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchWebhookSubscriptions(
  filter: WebhookSubscriptionFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<WebhookSubscription>> {
  return http.get<Page<WebhookSubscription>>(WEBHOOKSUBSCRIPTION_PATH, { ...toWebhookSubscriptionQuery(filter), page, size, sort });
}

export function getWebhookSubscription(id: string): Promise<WebhookSubscription> {
  return http.get<WebhookSubscription>(`${WEBHOOKSUBSCRIPTION_PATH}/${id}`);
}

export function getWebhookSubscriptionByCode(code: string): Promise<WebhookSubscription> {
  return http.get<WebhookSubscription>(`${WEBHOOKSUBSCRIPTION_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listWebhookSubscriptionSummaries(status?: LifecycleStatus): Promise<WebhookSubscriptionSummary[]> {
  return http.get<WebhookSubscriptionSummary[]>(`${WEBHOOKSUBSCRIPTION_PATH}/summaries`, { status });
}

export function createWebhookSubscription(input: WebhookSubscriptionInput): Promise<WebhookSubscription> {
  return http.post<WebhookSubscription>(WEBHOOKSUBSCRIPTION_PATH, input);
}

export function updateWebhookSubscription(id: string, input: WebhookSubscriptionUpdateInput): Promise<WebhookSubscription> {
  return http.put<WebhookSubscription>(`${WEBHOOKSUBSCRIPTION_PATH}/${id}`, input);
}

export function changeWebhookSubscriptionStatus(id: string, status: LifecycleStatus): Promise<WebhookSubscription> {
  return http.patch<WebhookSubscription>(`${WEBHOOKSUBSCRIPTION_PATH}/${id}/status`, undefined, { status });
}

export function deleteWebhookSubscription(id: string): Promise<void> {
  return http.delete(`${WEBHOOKSUBSCRIPTION_PATH}/${id}`);
}

export function bulkCreateWebhookSubscriptions(inputs: WebhookSubscriptionInput[]): Promise<BulkResult<WebhookSubscription>> {
  return http.post<BulkResult<WebhookSubscription>>(`${WEBHOOKSUBSCRIPTION_PATH}/bulk`, inputs);
}

export function getWebhookSubscriptionStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${WEBHOOKSUBSCRIPTION_PATH}/stats/status`);
}

export function exportWebhookSubscriptionsCsv(filter: WebhookSubscriptionFilter): Promise<Blob> {
  return http.blob(`${WEBHOOKSUBSCRIPTION_PATH}/export`, toWebhookSubscriptionQuery(filter));
}
