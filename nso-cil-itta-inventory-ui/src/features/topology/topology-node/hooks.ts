import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTopologyNodes,
  changeTopologyNodeStatus,
  createTopologyNode,
  deleteTopologyNode,
  exportTopologyNodesCsv,
  getTopologyNode,
  getTopologyNodeStatusStats,
  listTopologyNodeSummaries,
  searchTopologyNodes,
  updateTopologyNode,
  listTopologyNodeByViewId,
} from './api';
import type { TopologyNodeFilter, TopologyNodeInput, TopologyNodeUpdateInput } from './types';

export const topologyNodeKeys = {
  all: ['topologyNode'] as const,
  lists: () => [...topologyNodeKeys.all, 'list'] as const,
  list: (filter: TopologyNodeFilter, page: number, size: number, sort?: string) =>
    [...topologyNodeKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...topologyNodeKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...topologyNodeKeys.all, 'summaries', status] as const,
  stats: () => [...topologyNodeKeys.all, 'stats'] as const,
};

export function useTopologyNodeList(filter: TopologyNodeFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: topologyNodeKeys.list(filter, page, size, sort),
    queryFn: () => searchTopologyNodes(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTopologyNode(id: string | undefined) {
  return useQuery({
    queryKey: topologyNodeKeys.detail(id ?? ''),
    queryFn: () => getTopologyNode(id as string),
    enabled: Boolean(id),
  });
}

export function useTopologyNodeSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: topologyNodeKeys.summaries(status),
    queryFn: () => listTopologyNodeSummaries(status),
    staleTime: 60_000,
  });
}

export function useTopologyNodeStatusStats() {
  return useQuery({
    queryKey: topologyNodeKeys.stats(),
    queryFn: getTopologyNodeStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTopologyNode() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyNodeInput) => createTopologyNode(input),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyNodeKeys.all }),
  });
}

export function useUpdateTopologyNode(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyNodeUpdateInput) => updateTopologyNode(id, input),
    onSuccess: (updated) => {
      client.setQueryData(topologyNodeKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: topologyNodeKeys.lists() });
    },
  });
}

export function useChangeTopologyNodeStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTopologyNodeStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyNodeKeys.all }),
  });
}

export function useDeleteTopologyNode() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTopologyNode(id),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyNodeKeys.all }),
  });
}

export function useBulkCreateTopologyNodes() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TopologyNodeInput[]) => bulkCreateTopologyNodes(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyNodeKeys.all }),
  });
}

export function useExportTopologyNodes() {
  return useMutation({
    mutationFn: (filter: TopologyNodeFilter) => exportTopologyNodesCsv(filter),
  });
}

export function useTopologyNodesByViewId(viewId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...topologyNodeKeys.all, 'by-view_id', viewId, page, size],
    queryFn: () => listTopologyNodeByViewId(viewId as string, page, size),
    enabled: Boolean(viewId),
  });
}
