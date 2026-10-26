import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRouteTargets,
  changeRouteTargetStatus,
  createRouteTarget,
  deleteRouteTarget,
  exportRouteTargetsCsv,
  getRouteTarget,
  getRouteTargetStatusStats,
  listRouteTargetSummaries,
  searchRouteTargets,
  updateRouteTarget,
  listRouteTargetByVrfId,
} from './api';
import type { RouteTargetFilter, RouteTargetInput, RouteTargetUpdateInput } from './types';

export const routeTargetKeys = {
  all: ['routeTarget'] as const,
  lists: () => [...routeTargetKeys.all, 'list'] as const,
  list: (filter: RouteTargetFilter, page: number, size: number, sort?: string) =>
    [...routeTargetKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...routeTargetKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...routeTargetKeys.all, 'summaries', status] as const,
  stats: () => [...routeTargetKeys.all, 'stats'] as const,
};

export function useRouteTargetList(filter: RouteTargetFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: routeTargetKeys.list(filter, page, size, sort),
    queryFn: () => searchRouteTargets(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRouteTarget(id: string | undefined) {
  return useQuery({
    queryKey: routeTargetKeys.detail(id ?? ''),
    queryFn: () => getRouteTarget(id as string),
    enabled: Boolean(id),
  });
}

export function useRouteTargetSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: routeTargetKeys.summaries(status),
    queryFn: () => listRouteTargetSummaries(status),
    staleTime: 60_000,
  });
}

export function useRouteTargetStatusStats() {
  return useQuery({
    queryKey: routeTargetKeys.stats(),
    queryFn: getRouteTargetStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRouteTarget() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RouteTargetInput) => createRouteTarget(input),
    onSuccess: () => client.invalidateQueries({ queryKey: routeTargetKeys.all }),
  });
}

export function useUpdateRouteTarget(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RouteTargetUpdateInput) => updateRouteTarget(id, input),
    onSuccess: (updated) => {
      client.setQueryData(routeTargetKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: routeTargetKeys.lists() });
    },
  });
}

export function useChangeRouteTargetStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRouteTargetStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: routeTargetKeys.all }),
  });
}

export function useDeleteRouteTarget() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRouteTarget(id),
    onSuccess: () => client.invalidateQueries({ queryKey: routeTargetKeys.all }),
  });
}

export function useBulkCreateRouteTargets() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RouteTargetInput[]) => bulkCreateRouteTargets(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: routeTargetKeys.all }),
  });
}

export function useExportRouteTargets() {
  return useMutation({
    mutationFn: (filter: RouteTargetFilter) => exportRouteTargetsCsv(filter),
  });
}

export function useRouteTargetsByVrfId(vrfId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...routeTargetKeys.all, 'by-vrf_id', vrfId, page, size],
    queryFn: () => listRouteTargetByVrfId(vrfId as string, page, size),
    enabled: Boolean(vrfId),
  });
}
