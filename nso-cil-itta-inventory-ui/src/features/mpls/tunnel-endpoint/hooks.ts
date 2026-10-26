import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateTunnelEndpoints,
  changeTunnelEndpointStatus,
  createTunnelEndpoint,
  deleteTunnelEndpoint,
  exportTunnelEndpointsCsv,
  getTunnelEndpoint,
  getTunnelEndpointStatusStats,
  listTunnelEndpointSummaries,
  searchTunnelEndpoints,
  updateTunnelEndpoint,
  listTunnelEndpointByDeviceId,
} from './api';
import type { TunnelEndpointFilter, TunnelEndpointInput, TunnelEndpointUpdateInput } from './types';

export const tunnelEndpointKeys = {
  all: ['tunnelEndpoint'] as const,
  lists: () => [...tunnelEndpointKeys.all, 'list'] as const,
  list: (filter: TunnelEndpointFilter, page: number, size: number, sort?: string) =>
    [...tunnelEndpointKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...tunnelEndpointKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...tunnelEndpointKeys.all, 'summaries', status] as const,
  stats: () => [...tunnelEndpointKeys.all, 'stats'] as const,
};

export function useTunnelEndpointList(filter: TunnelEndpointFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: tunnelEndpointKeys.list(filter, page, size, sort),
    queryFn: () => searchTunnelEndpoints(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useTunnelEndpoint(id: string | undefined) {
  return useQuery({
    queryKey: tunnelEndpointKeys.detail(id ?? ''),
    queryFn: () => getTunnelEndpoint(id as string),
    enabled: Boolean(id),
  });
}

export function useTunnelEndpointSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: tunnelEndpointKeys.summaries(status),
    queryFn: () => listTunnelEndpointSummaries(status),
    staleTime: 60_000,
  });
}

export function useTunnelEndpointStatusStats() {
  return useQuery({
    queryKey: tunnelEndpointKeys.stats(),
    queryFn: getTunnelEndpointStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateTunnelEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TunnelEndpointInput) => createTunnelEndpoint(input),
    onSuccess: () => client.invalidateQueries({ queryKey: tunnelEndpointKeys.all }),
  });
}

export function useUpdateTunnelEndpoint(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: TunnelEndpointUpdateInput) => updateTunnelEndpoint(id, input),
    onSuccess: (updated) => {
      client.setQueryData(tunnelEndpointKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: tunnelEndpointKeys.lists() });
    },
  });
}

export function useChangeTunnelEndpointStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeTunnelEndpointStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: tunnelEndpointKeys.all }),
  });
}

export function useDeleteTunnelEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteTunnelEndpoint(id),
    onSuccess: () => client.invalidateQueries({ queryKey: tunnelEndpointKeys.all }),
  });
}

export function useBulkCreateTunnelEndpoints() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: TunnelEndpointInput[]) => bulkCreateTunnelEndpoints(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: tunnelEndpointKeys.all }),
  });
}

export function useExportTunnelEndpoints() {
  return useMutation({
    mutationFn: (filter: TunnelEndpointFilter) => exportTunnelEndpointsCsv(filter),
  });
}

export function useTunnelEndpointsByDeviceId(deviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...tunnelEndpointKeys.all, 'by-device_id', deviceId, page, size],
    queryFn: () => listTunnelEndpointByDeviceId(deviceId as string, page, size),
    enabled: Boolean(deviceId),
  });
}
