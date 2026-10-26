import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTopologyEdges,
  changeTopologyEdgeStatus,
  createTopologyEdge,
  deleteTopologyEdge,
  exportTopologyEdgesCsv,
  getTopologyEdge,
  getTopologyEdgeStatusStats,
  listTopologyEdgeSummaries,
  searchTopologyEdges,
  updateTopologyEdge,
  listTopologyEdgeByViewId,
  listTopologyEdgeBySourceNodeId,
  listTopologyEdgeByTargetNodeId,
} from './api';
import type { TopologyEdgeFilter, TopologyEdgeInput, TopologyEdgeUpdateInput } from './types';

export const topologyEdgeKeys = {
  all: ['topologyEdge'] as const,
  lists: () => [...topologyEdgeKeys.all, 'list'] as const,
  list: (filter: TopologyEdgeFilter, page: number, size: number, sort?: string) =>
    [...topologyEdgeKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...topologyEdgeKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...topologyEdgeKeys.all, 'summaries', status] as const,
  stats: () => [...topologyEdgeKeys.all, 'stats'] as const,
};

export function useTopologyEdgeList(filter: TopologyEdgeFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: topologyEdgeKeys.list(filter, page, size, sort),
    queryFn: () => searchTopologyEdges(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTopologyEdge(id: string | undefined) {
  return useQuery({
    queryKey: topologyEdgeKeys.detail(id ?? ''),
    queryFn: () => getTopologyEdge(id as string),
    enabled: Boolean(id),
  });
}

export function useTopologyEdgeSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: topologyEdgeKeys.summaries(status),
    queryFn: () => listTopologyEdgeSummaries(status),
    staleTime: 60_000,
  });
}

export function useTopologyEdgeStatusStats() {
  return useQuery({
    queryKey: topologyEdgeKeys.stats(),
    queryFn: getTopologyEdgeStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTopologyEdge() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyEdgeInput) => createTopologyEdge(input),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyEdgeKeys.all }),
  });
}

export function useUpdateTopologyEdge(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TopologyEdgeUpdateInput) => updateTopologyEdge(id, input),
    onSuccess: (updated) => {
      client.setQueryData(topologyEdgeKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: topologyEdgeKeys.lists() });
    },
  });
}

export function useChangeTopologyEdgeStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTopologyEdgeStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyEdgeKeys.all }),
  });
}

export function useDeleteTopologyEdge() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTopologyEdge(id),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyEdgeKeys.all }),
  });
}

export function useBulkCreateTopologyEdges() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TopologyEdgeInput[]) => bulkCreateTopologyEdges(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: topologyEdgeKeys.all }),
  });
}

export function useExportTopologyEdges() {
  return useMutation({
    mutationFn: (filter: TopologyEdgeFilter) => exportTopologyEdgesCsv(filter),
  });
}

export function useTopologyEdgesByViewId(viewId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...topologyEdgeKeys.all, 'by-view_id', viewId, page, size],
    queryFn: () => listTopologyEdgeByViewId(viewId as string, page, size),
    enabled: Boolean(viewId),
  });
}

export function useTopologyEdgesBySourceNodeId(sourceNodeId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...topologyEdgeKeys.all, 'by-source_node_id', sourceNodeId, page, size],
    queryFn: () => listTopologyEdgeBySourceNodeId(sourceNodeId as string, page, size),
    enabled: Boolean(sourceNodeId),
  });
}

export function useTopologyEdgesByTargetNodeId(targetNodeId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...topologyEdgeKeys.all, 'by-target_node_id', targetNodeId, page, size],
    queryFn: () => listTopologyEdgeByTargetNodeId(targetNodeId as string, page, size),
    enabled: Boolean(targetNodeId),
  });
}
