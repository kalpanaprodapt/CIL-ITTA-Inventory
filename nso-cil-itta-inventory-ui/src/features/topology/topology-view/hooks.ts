import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTopologyViews,
  changeTopologyViewStatus,
  createTopologyView,
  deleteTopologyView,
  exportTopologyViewsCsv,
  getTopologyView,
  getTopologyViewStatusStats,
  listTopologyViewSummaries,
  searchTopologyViews,
  updateTopologyView,

} from './api';
import type { TopologyViewFilter, TopologyViewInput, TopologyViewUpdateInput } from './types';

export const topologyViewKeys = {
  all: ['topologyView'] as const,
  lists: () => [...topologyViewKeys.all, 'list'] as const,
  list: (filter: TopologyViewFilter, page: number, size: number, sort?: string) =>
    [...topologyViewKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...topologyViewKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...topologyViewKeys.all, 'summaries', status] as const,
  stats: () => [...topologyViewKeys.all, 'stats'] as const,
};

export function useTopologyViewList(filter: TopologyViewFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: topologyViewKeys.list(filter, page, size, sort),
    queryFn: () => searchTopologyViews(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTopologyView(id: string | undefined) {
  return useQuery({
    queryKey: topologyViewKeys.detail(id ?? ''),
    queryFn: () => getTopologyView(id as string),
    enabled: Boolean(id),
  });
}

export function useTopologyViewSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: topologyViewKeys.summaries(status),
    queryFn: () => listTopologyViewSummaries(status),
    staleTime: 60_000,
  });
}

export function useTopologyViewStatusStats() {
  return useQuery({
    queryKey: topologyViewKeys.stats(),
    queryFn: getTopologyViewStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTopologyView() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyViewInput) => createTopologyView(input),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyViewKeys.all }),
  });
}

export function useUpdateTopologyView(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyViewUpdateInput) => updateTopologyView(id, input),
    onSuccess: (updated) => {
      client.setQueryData(topologyViewKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: topologyViewKeys.lists() });
    },
  });
}

export function useChangeTopologyViewStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTopologyViewStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyViewKeys.all }),
  });
}

export function useDeleteTopologyView() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTopologyView(id),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyViewKeys.all }),
  });
}

export function useBulkCreateTopologyViews() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TopologyViewInput[]) => bulkCreateTopologyViews(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyViewKeys.all }),
  });
}

export function useExportTopologyViews() {
  return useMutation({
    mutationFn: (filter: TopologyViewFilter) => exportTopologyViewsCsv(filter),
  });
}
