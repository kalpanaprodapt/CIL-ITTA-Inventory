import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSuppressionRules,
  changeSuppressionRuleStatus,
  createSuppressionRule,
  deleteSuppressionRule,
  exportSuppressionRulesCsv,
  getSuppressionRule,
  getSuppressionRuleStatusStats,
  listSuppressionRuleSummaries,
  searchSuppressionRules,
  updateSuppressionRule,

} from './api';
import type { SuppressionRuleFilter, SuppressionRuleInput, SuppressionRuleUpdateInput } from './types';

export const suppressionRuleKeys = {
  all: ['suppressionRule'] as const,
  lists: () => [...suppressionRuleKeys.all, 'list'] as const,
  list: (filter: SuppressionRuleFilter, page: number, size: number, sort?: string) =>
    [...suppressionRuleKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...suppressionRuleKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...suppressionRuleKeys.all, 'summaries', status] as const,
  stats: () => [...suppressionRuleKeys.all, 'stats'] as const,
};

export function useSuppressionRuleList(filter: SuppressionRuleFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: suppressionRuleKeys.list(filter, page, size, sort),
    queryFn: () => searchSuppressionRules(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSuppressionRule(id: string | undefined) {
  return useQuery({
    queryKey: suppressionRuleKeys.detail(id ?? ''),
    queryFn: () => getSuppressionRule(id as string),
    enabled: Boolean(id),
  });
}

export function useSuppressionRuleSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: suppressionRuleKeys.summaries(status),
    queryFn: () => listSuppressionRuleSummaries(status),
    staleTime: 60_000,
  });
}

export function useSuppressionRuleStatusStats() {
  return useQuery({
    queryKey: suppressionRuleKeys.stats(),
    queryFn: getSuppressionRuleStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSuppressionRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SuppressionRuleInput) => createSuppressionRule(input),
    onSuccess: () => client.invalidateQueries({ queryKey: suppressionRuleKeys.all }),
  });
}

export function useUpdateSuppressionRule(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SuppressionRuleUpdateInput) => updateSuppressionRule(id, input),
    onSuccess: (updated) => {
      client.setQueryData(suppressionRuleKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: suppressionRuleKeys.lists() });
    },
  });
}

export function useChangeSuppressionRuleStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSuppressionRuleStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: suppressionRuleKeys.all }),
  });
}

export function useDeleteSuppressionRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSuppressionRule(id),
    onSuccess: () => client.invalidateQueries({ queryKey: suppressionRuleKeys.all }),
  });
}

export function useBulkCreateSuppressionRules() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SuppressionRuleInput[]) => bulkCreateSuppressionRules(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: suppressionRuleKeys.all }),
  });
}

export function useExportSuppressionRules() {
  return useMutation({
    mutationFn: (filter: SuppressionRuleFilter) => exportSuppressionRulesCsv(filter),
  });
}
