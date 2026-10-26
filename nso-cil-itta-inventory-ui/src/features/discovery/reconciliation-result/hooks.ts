import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateReconciliationResults,
  changeReconciliationResultStatus,
  createReconciliationResult,
  deleteReconciliationResult,
  exportReconciliationResultsCsv,
  getReconciliationResult,
  getReconciliationResultStatusStats,
  listReconciliationResultSummaries,
  searchReconciliationResults,
  updateReconciliationResult,
  listReconciliationResultByJobId,
  listReconciliationResultByRuleId,
} from './api';
import type { ReconciliationResultFilter, ReconciliationResultInput, ReconciliationResultUpdateInput } from './types';

export const reconciliationResultKeys = {
  all: ['reconciliationResult'] as const,
  lists: () => [...reconciliationResultKeys.all, 'list'] as const,
  list: (filter: ReconciliationResultFilter, page: number, size: number, sort?: string) =>
    [...reconciliationResultKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...reconciliationResultKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...reconciliationResultKeys.all, 'summaries', status] as const,
  stats: () => [...reconciliationResultKeys.all, 'stats'] as const,
};

export function useReconciliationResultList(filter: ReconciliationResultFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: reconciliationResultKeys.list(filter, page, size, sort),
    queryFn: () => searchReconciliationResults(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useReconciliationResult(id: string | undefined) {
  return useQuery({
    queryKey: reconciliationResultKeys.detail(id ?? ''),
    queryFn: () => getReconciliationResult(id as string),
    enabled: Boolean(id),
  });
}

export function useReconciliationResultSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: reconciliationResultKeys.summaries(status),
    queryFn: () => listReconciliationResultSummaries(status),
    staleTime: 60_000,
  });
}

export function useReconciliationResultStatusStats() {
  return useQuery({
    queryKey: reconciliationResultKeys.stats(),
    queryFn: getReconciliationResultStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateReconciliationResult() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReconciliationResultInput) => createReconciliationResult(input),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationResultKeys.all }),
  });
}

export function useUpdateReconciliationResult(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ReconciliationResultUpdateInput) => updateReconciliationResult(id, input),
    onSuccess: (updated) => {
      client.setQueryData(reconciliationResultKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: reconciliationResultKeys.lists() });
    },
  });
}

export function useChangeReconciliationResultStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeReconciliationResultStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationResultKeys.all }),
  });
}

export function useDeleteReconciliationResult() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteReconciliationResult(id),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationResultKeys.all }),
  });
}

export function useBulkCreateReconciliationResults() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ReconciliationResultInput[]) => bulkCreateReconciliationResults(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: reconciliationResultKeys.all }),
  });
}

export function useExportReconciliationResults() {
  return useMutation({
    mutationFn: (filter: ReconciliationResultFilter) => exportReconciliationResultsCsv(filter),
  });
}

export function useReconciliationResultsByJobId(jobId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...reconciliationResultKeys.all, 'by-job_id', jobId, page, size],
    queryFn: () => listReconciliationResultByJobId(jobId as string, page, size),
    enabled: Boolean(jobId),
  });
}

export function useReconciliationResultsByRuleId(ruleId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...reconciliationResultKeys.all, 'by-rule_id', ruleId, page, size],
    queryFn: () => listReconciliationResultByRuleId(ruleId as string, page, size),
    enabled: Boolean(ruleId),
  });
}
