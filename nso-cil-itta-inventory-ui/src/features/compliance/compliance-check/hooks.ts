import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateComplianceChecks,
  changeComplianceCheckStatus,
  createComplianceCheck,
  deleteComplianceCheck,
  exportComplianceChecksCsv,
  getComplianceCheck,
  getComplianceCheckStatusStats,
  listComplianceCheckSummaries,
  searchComplianceChecks,
  updateComplianceCheck,
  listComplianceCheckByRuleId,
} from './api';
import type { ComplianceCheckFilter, ComplianceCheckInput, ComplianceCheckUpdateInput } from './types';

export const complianceCheckKeys = {
  all: ['complianceCheck'] as const,
  lists: () => [...complianceCheckKeys.all, 'list'] as const,
  list: (filter: ComplianceCheckFilter, page: number, size: number, sort?: string) =>
    [...complianceCheckKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...complianceCheckKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...complianceCheckKeys.all, 'summaries', status] as const,
  stats: () => [...complianceCheckKeys.all, 'stats'] as const,
};

export function useComplianceCheckList(filter: ComplianceCheckFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: complianceCheckKeys.list(filter, page, size, sort),
    queryFn: () => searchComplianceChecks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useComplianceCheck(id: string | undefined) {
  return useQuery({
    queryKey: complianceCheckKeys.detail(id ?? ''),
    queryFn: () => getComplianceCheck(id as string),
    enabled: Boolean(id),
  });
}

export function useComplianceCheckSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: complianceCheckKeys.summaries(status),
    queryFn: () => listComplianceCheckSummaries(status),
    staleTime: 60_000,
  });
}

export function useComplianceCheckStatusStats() {
  return useQuery({
    queryKey: complianceCheckKeys.stats(),
    queryFn: getComplianceCheckStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateComplianceCheck() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceCheckInput) => createComplianceCheck(input),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceCheckKeys.all }),
  });
}

export function useUpdateComplianceCheck(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ComplianceCheckUpdateInput) => updateComplianceCheck(id, input),
    onSuccess: (updated) => {
      client.setQueryData(complianceCheckKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: complianceCheckKeys.lists() });
    },
  });
}

export function useChangeComplianceCheckStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeComplianceCheckStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceCheckKeys.all }),
  });
}

export function useDeleteComplianceCheck() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteComplianceCheck(id),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceCheckKeys.all }),
  });
}

export function useBulkCreateComplianceChecks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ComplianceCheckInput[]) => bulkCreateComplianceChecks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: complianceCheckKeys.all }),
  });
}

export function useExportComplianceChecks() {
  return useMutation({
    mutationFn: (filter: ComplianceCheckFilter) => exportComplianceChecksCsv(filter),
  });
}

export function useComplianceChecksByRuleId(ruleId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...complianceCheckKeys.all, 'by-rule_id', ruleId, page, size],
    queryFn: () => listComplianceCheckByRuleId(ruleId as string, page, size),
    enabled: Boolean(ruleId),
  });
}
