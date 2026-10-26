import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateReconciliationRules,
  changeReconciliationRuleStatus,
  createReconciliationRule,
  deleteReconciliationRule,
  exportReconciliationRulesCsv,
  getReconciliationRule,
  getReconciliationRuleStatusStats,
  listReconciliationRuleSummaries,
  searchReconciliationRules,
  updateReconciliationRule,

} from './api';
import type { ReconciliationRuleFilter, ReconciliationRuleInput, ReconciliationRuleUpdateInput } from './types';

export const reconciliationRuleKeys = {
  all: ['reconciliationRule'] as const,
  lists: () => [...reconciliationRuleKeys.all, 'list'] as const,
  list: (filter: ReconciliationRuleFilter, page: number, size: number, sort?: string) =>
    [...reconciliationRuleKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...reconciliationRuleKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...reconciliationRuleKeys.all, 'summaries', status] as const,
  stats: () => [...reconciliationRuleKeys.all, 'stats'] as const,
};

export function useReconciliationRuleList(filter: ReconciliationRuleFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: reconciliationRuleKeys.list(filter, page, size, sort),
    queryFn: () => searchReconciliationRules(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useReconciliationRule(id: string | undefined) {
  return useQuery({
    queryKey: reconciliationRuleKeys.detail(id ?? ''),
    queryFn: () => getReconciliationRule(id as string),
    enabled: Boolean(id),
  });
}

export function useReconciliationRuleSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: reconciliationRuleKeys.summaries(status),
    queryFn: () => listReconciliationRuleSummaries(status),
    staleTime: 60_000,
  });
}

export function useReconciliationRuleStatusStats() {
  return useQuery({
    queryKey: reconciliationRuleKeys.stats(),
    queryFn: getReconciliationRuleStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateReconciliationRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReconciliationRuleInput) => createReconciliationRule(input),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationRuleKeys.all }),
  });
}

export function useUpdateReconciliationRule(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReconciliationRuleUpdateInput) => updateReconciliationRule(id, input),
    onSuccess: (updated) => {
      client.setQueryData(reconciliationRuleKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: reconciliationRuleKeys.lists() });
    },
  });
}

export function useChangeReconciliationRuleStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeReconciliationRuleStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationRuleKeys.all }),
  });
}

export function useDeleteReconciliationRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteReconciliationRule(id),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationRuleKeys.all }),
  });
}

export function useBulkCreateReconciliationRules() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ReconciliationRuleInput[]) => bulkCreateReconciliationRules(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationRuleKeys.all }),
  });
}

export function useExportReconciliationRules() {
  return useMutation({
    mutationFn: (filter: ReconciliationRuleFilter) => exportReconciliationRulesCsv(filter),
  });
}
