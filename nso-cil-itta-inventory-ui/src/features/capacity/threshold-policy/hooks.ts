import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateThresholdPolicys,
  changeThresholdPolicyStatus,
  createThresholdPolicy,
  deleteThresholdPolicy,
  exportThresholdPolicysCsv,
  getThresholdPolicy,
  getThresholdPolicyStatusStats,
  listThresholdPolicySummaries,
  searchThresholdPolicys,
  updateThresholdPolicy,

} from './api';
import type { ThresholdPolicyFilter, ThresholdPolicyInput, ThresholdPolicyUpdateInput } from './types';

export const thresholdPolicyKeys = {
  all: ['thresholdPolicy'] as const,
  lists: () => [...thresholdPolicyKeys.all, 'list'] as const,
  list: (filter: ThresholdPolicyFilter, page: number, size: number, sort?: string) =>
    [...thresholdPolicyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...thresholdPolicyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...thresholdPolicyKeys.all, 'summaries', status] as const,
  stats: () => [...thresholdPolicyKeys.all, 'stats'] as const,
};

export function useThresholdPolicyList(filter: ThresholdPolicyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: thresholdPolicyKeys.list(filter, page, size, sort),
    queryFn: () => searchThresholdPolicys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useThresholdPolicy(id: string | undefined) {
  return useQuery({
    queryKey: thresholdPolicyKeys.detail(id ?? ''),
    queryFn: () => getThresholdPolicy(id as string),
    enabled: Boolean(id),
  });
}

export function useThresholdPolicySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: thresholdPolicyKeys.summaries(status),
    queryFn: () => listThresholdPolicySummaries(status),
    staleTime: 60_000,
  });
}

export function useThresholdPolicyStatusStats() {
  return useQuery({
    queryKey: thresholdPolicyKeys.stats(),
    queryFn: getThresholdPolicyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateThresholdPolicy() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ThresholdPolicyInput) => createThresholdPolicy(input),
    onSuccess: () => client.invalidateQueries({ queryKey: thresholdPolicyKeys.all }),
  });
}

export function useUpdateThresholdPolicy(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ThresholdPolicyUpdateInput) => updateThresholdPolicy(id, input),
    onSuccess: (updated) => {
      client.setQueryData(thresholdPolicyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: thresholdPolicyKeys.lists() });
    },
  });
}

export function useChangeThresholdPolicyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeThresholdPolicyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: thresholdPolicyKeys.all }),
  });
}

export function useDeleteThresholdPolicy() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteThresholdPolicy(id),
    onSuccess: () => client.invalidateQueries({ queryKey: thresholdPolicyKeys.all }),
  });
}

export function useBulkCreateThresholdPolicys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ThresholdPolicyInput[]) => bulkCreateThresholdPolicys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: thresholdPolicyKeys.all }),
  });
}

export function useExportThresholdPolicys() {
  return useMutation({
    mutationFn: (filter: ThresholdPolicyFilter) => exportThresholdPolicysCsv(filter),
  });
}
