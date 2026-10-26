import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateWebhookSubscriptions,
  changeWebhookSubscriptionStatus,
  createWebhookSubscription,
  deleteWebhookSubscription,
  exportWebhookSubscriptionsCsv,
  getWebhookSubscription,
  getWebhookSubscriptionStatusStats,
  listWebhookSubscriptionSummaries,
  searchWebhookSubscriptions,
  updateWebhookSubscription,

} from './api';
import type { WebhookSubscriptionFilter, WebhookSubscriptionInput, WebhookSubscriptionUpdateInput } from './types';

export const webhookSubscriptionKeys = {
  all: ['webhookSubscription'] as const,
  lists: () => [...webhookSubscriptionKeys.all, 'list'] as const,
  list: (filter: WebhookSubscriptionFilter, page: number, size: number, sort?: string) =>
    [...webhookSubscriptionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...webhookSubscriptionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...webhookSubscriptionKeys.all, 'summaries', status] as const,
  stats: () => [...webhookSubscriptionKeys.all, 'stats'] as const,
};

export function useWebhookSubscriptionList(filter: WebhookSubscriptionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: webhookSubscriptionKeys.list(filter, page, size, sort),
    queryFn: () => searchWebhookSubscriptions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useWebhookSubscription(id: string | undefined) {
  return useQuery({
    queryKey: webhookSubscriptionKeys.detail(id ?? ''),
    queryFn: () => getWebhookSubscription(id as string),
    enabled: Boolean(id),
  });
}

export function useWebhookSubscriptionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: webhookSubscriptionKeys.summaries(status),
    queryFn: () => listWebhookSubscriptionSummaries(status),
    staleTime: 60_000,
  });
}

export function useWebhookSubscriptionStatusStats() {
  return useQuery({
    queryKey: webhookSubscriptionKeys.stats(),
    queryFn: getWebhookSubscriptionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateWebhookSubscription() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WebhookSubscriptionInput) => createWebhookSubscription(input),
    onSuccess: () => client.invalidateQueries({ queryKey: webhookSubscriptionKeys.all }),
  });
}

export function useUpdateWebhookSubscription(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WebhookSubscriptionUpdateInput) => updateWebhookSubscription(id, input),
    onSuccess: (updated) => {
      client.setQueryData(webhookSubscriptionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: webhookSubscriptionKeys.lists() });
    },
  });
}

export function useChangeWebhookSubscriptionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeWebhookSubscriptionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: webhookSubscriptionKeys.all }),
  });
}

export function useDeleteWebhookSubscription() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteWebhookSubscription(id),
    onSuccess: () => client.invalidateQueries({ queryKey: webhookSubscriptionKeys.all }),
  });
}

export function useBulkCreateWebhookSubscriptions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: WebhookSubscriptionInput[]) => bulkCreateWebhookSubscriptions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: webhookSubscriptionKeys.all }),
  });
}

export function useExportWebhookSubscriptions() {
  return useMutation({
    mutationFn: (filter: WebhookSubscriptionFilter) => exportWebhookSubscriptionsCsv(filter),
  });
}
