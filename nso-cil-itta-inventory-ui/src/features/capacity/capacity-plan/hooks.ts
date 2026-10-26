import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCapacityPlans,
  changeCapacityPlanStatus,
  createCapacityPlan,
  deleteCapacityPlan,
  exportCapacityPlansCsv,
  getCapacityPlan,
  getCapacityPlanStatusStats,
  listCapacityPlanSummaries,
  searchCapacityPlans,
  updateCapacityPlan,

} from './api';
import type { CapacityPlanFilter, CapacityPlanInput, CapacityPlanUpdateInput } from './types';

export const capacityPlanKeys = {
  all: ['capacityPlan'] as const,
  lists: () => [...capacityPlanKeys.all, 'list'] as const,
  list: (filter: CapacityPlanFilter, page: number, size: number, sort?: string) =>
    [...capacityPlanKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...capacityPlanKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...capacityPlanKeys.all, 'summaries', status] as const,
  stats: () => [...capacityPlanKeys.all, 'stats'] as const,
};

export function useCapacityPlanList(filter: CapacityPlanFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: capacityPlanKeys.list(filter, page, size, sort),
    queryFn: () => searchCapacityPlans(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCapacityPlan(id: string | undefined) {
  return useQuery({
    queryKey: capacityPlanKeys.detail(id ?? ''),
    queryFn: () => getCapacityPlan(id as string),
    enabled: Boolean(id),
  });
}

export function useCapacityPlanSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: capacityPlanKeys.summaries(status),
    queryFn: () => listCapacityPlanSummaries(status),
    staleTime: 60_000,
  });
}

export function useCapacityPlanStatusStats() {
  return useQuery({
    queryKey: capacityPlanKeys.stats(),
    queryFn: getCapacityPlanStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCapacityPlan() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CapacityPlanInput) => createCapacityPlan(input),
    onSuccess: () => client.invalidateQueries({ queryKey: capacityPlanKeys.all }),
  });
}

export function useUpdateCapacityPlan(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CapacityPlanUpdateInput) => updateCapacityPlan(id, input),
    onSuccess: (updated) => {
      client.setQueryData(capacityPlanKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: capacityPlanKeys.lists() });
    },
  });
}

export function useChangeCapacityPlanStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCapacityPlanStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: capacityPlanKeys.all }),
  });
}

export function useDeleteCapacityPlan() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCapacityPlan(id),
    onSuccess: () => client.invalidateQueries({ queryKey: capacityPlanKeys.all }),
  });
}

export function useBulkCreateCapacityPlans() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CapacityPlanInput[]) => bulkCreateCapacityPlans(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: capacityPlanKeys.all }),
  });
}

export function useExportCapacityPlans() {
  return useMutation({
    mutationFn: (filter: CapacityPlanFilter) => exportCapacityPlansCsv(filter),
  });
}
