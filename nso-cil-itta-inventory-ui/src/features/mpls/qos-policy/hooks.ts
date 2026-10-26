import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateQosPolicys,
  changeQosPolicyStatus,
  createQosPolicy,
  deleteQosPolicy,
  exportQosPolicysCsv,
  getQosPolicy,
  getQosPolicyStatusStats,
  listQosPolicySummaries,
  searchQosPolicys,
  updateQosPolicy,

} from './api';
import type { QosPolicyFilter, QosPolicyInput, QosPolicyUpdateInput } from './types';

export const qosPolicyKeys = {
  all: ['qosPolicy'] as const,
  lists: () => [...qosPolicyKeys.all, 'list'] as const,
  list: (filter: QosPolicyFilter, page: number, size: number, sort?: string) =>
    [...qosPolicyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...qosPolicyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...qosPolicyKeys.all, 'summaries', status] as const,
  stats: () => [...qosPolicyKeys.all, 'stats'] as const,
};

export function useQosPolicyList(filter: QosPolicyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: qosPolicyKeys.list(filter, page, size, sort),
    queryFn: () => searchQosPolicys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useQosPolicy(id: string | undefined) {
  return useQuery({
    queryKey: qosPolicyKeys.detail(id ?? ''),
    queryFn: () => getQosPolicy(id as string),
    enabled: Boolean(id),
  });
}

export function useQosPolicySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: qosPolicyKeys.summaries(status),
    queryFn: () => listQosPolicySummaries(status),
    staleTime: 60_000,
  });
}

export function useQosPolicyStatusStats() {
  return useQuery({
    queryKey: qosPolicyKeys.stats(),
    queryFn: getQosPolicyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateQosPolicy() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: QosPolicyInput) => createQosPolicy(input),
    onSuccess: () => client.invalidateQueries({ queryKey: qosPolicyKeys.all }),
  });
}

export function useUpdateQosPolicy(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: QosPolicyUpdateInput) => updateQosPolicy(id, input),
    onSuccess: (updated) => {
      client.setQueryData(qosPolicyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: qosPolicyKeys.lists() });
    },
  });
}

export function useChangeQosPolicyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeQosPolicyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: qosPolicyKeys.all }),
  });
}

export function useDeleteQosPolicy() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteQosPolicy(id),
    onSuccess: () => client.invalidateQueries({ queryKey: qosPolicyKeys.all }),
  });
}

export function useBulkCreateQosPolicys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: QosPolicyInput[]) => bulkCreateQosPolicys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: qosPolicyKeys.all }),
  });
}

export function useExportQosPolicys() {
  return useMutation({
    mutationFn: (filter: QosPolicyFilter) => exportQosPolicysCsv(filter),
  });
}
