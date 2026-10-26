import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateComplianceRules,
  changeComplianceRuleStatus,
  createComplianceRule,
  deleteComplianceRule,
  exportComplianceRulesCsv,
  getComplianceRule,
  getComplianceRuleStatusStats,
  listComplianceRuleSummaries,
  searchComplianceRules,
  updateComplianceRule,

} from './api';
import type { ComplianceRuleFilter, ComplianceRuleInput, ComplianceRuleUpdateInput } from './types';

export const complianceRuleKeys = {
  all: ['complianceRule'] as const,
  lists: () => [...complianceRuleKeys.all, 'list'] as const,
  list: (filter: ComplianceRuleFilter, page: number, size: number, sort?: string) =>
    [...complianceRuleKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...complianceRuleKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...complianceRuleKeys.all, 'summaries', status] as const,
  stats: () => [...complianceRuleKeys.all, 'stats'] as const,
};

export function useComplianceRuleList(filter: ComplianceRuleFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: complianceRuleKeys.list(filter, page, size, sort),
    queryFn: () => searchComplianceRules(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useComplianceRule(id: string | undefined) {
  return useQuery({
    queryKey: complianceRuleKeys.detail(id ?? ''),
    queryFn: () => getComplianceRule(id as string),
    enabled: Boolean(id),
  });
}

export function useComplianceRuleSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: complianceRuleKeys.summaries(status),
    queryFn: () => listComplianceRuleSummaries(status),
    staleTime: 60_000,
  });
}

export function useComplianceRuleStatusStats() {
  return useQuery({
    queryKey: complianceRuleKeys.stats(),
    queryFn: getComplianceRuleStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateComplianceRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceRuleInput) => createComplianceRule(input),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceRuleKeys.all }),
  });
}

export function useUpdateComplianceRule(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceRuleUpdateInput) => updateComplianceRule(id, input),
    onSuccess: (updated) => {
      client.setQueryData(complianceRuleKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: complianceRuleKeys.lists() });
    },
  });
}

export function useChangeComplianceRuleStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeComplianceRuleStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceRuleKeys.all }),
  });
}

export function useDeleteComplianceRule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteComplianceRule(id),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceRuleKeys.all }),
  });
}

export function useBulkCreateComplianceRules() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ComplianceRuleInput[]) => bulkCreateComplianceRules(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceRuleKeys.all }),
  });
}

export function useExportComplianceRules() {
  return useMutation({
    mutationFn: (filter: ComplianceRuleFilter) => exportComplianceRulesCsv(filter),
  });
}
