import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCircuits,
  changeCircuitStatus,
  createCircuit,
  deleteCircuit,
  exportCircuitsCsv,
  getCircuit,
  getCircuitStatusStats,
  listCircuitSummaries,
  searchCircuits,
  updateCircuit,
  listCircuitByProviderId,
  listCircuitByASiteId,
  listCircuitByZSiteId,
} from './api';
import type { CircuitFilter, CircuitInput, CircuitUpdateInput } from './types';

export const circuitKeys = {
  all: ['circuit'] as const,
  lists: () => [...circuitKeys.all, 'list'] as const,
  list: (filter: CircuitFilter, page: number, size: number, sort?: string) =>
    [...circuitKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...circuitKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...circuitKeys.all, 'summaries', status] as const,
  stats: () => [...circuitKeys.all, 'stats'] as const,
};

export function useCircuitList(filter: CircuitFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: circuitKeys.list(filter, page, size, sort),
    queryFn: () => searchCircuits(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCircuit(id: string | undefined) {
  return useQuery({
    queryKey: circuitKeys.detail(id ?? ''),
    queryFn: () => getCircuit(id as string),
    enabled: Boolean(id),
  });
}

export function useCircuitSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: circuitKeys.summaries(status),
    queryFn: () => listCircuitSummaries(status),
    staleTime: 60_000,
  });
}

export function useCircuitStatusStats() {
  return useQuery({
    queryKey: circuitKeys.stats(),
    queryFn: getCircuitStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCircuit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitInput) => createCircuit(input),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitKeys.all }),
  });
}

export function useUpdateCircuit(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitUpdateInput) => updateCircuit(id, input),
    onSuccess: (updated) => {
      client.setQueryData(circuitKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: circuitKeys.lists() });
    },
  });
}

export function useChangeCircuitStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCircuitStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitKeys.all }),
  });
}

export function useDeleteCircuit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCircuit(id),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitKeys.all }),
  });
}

export function useBulkCreateCircuits() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CircuitInput[]) => bulkCreateCircuits(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitKeys.all }),
  });
}

export function useExportCircuits() {
  return useMutation({
    mutationFn: (filter: CircuitFilter) => exportCircuitsCsv(filter),
  });
}

export function useCircuitsByProviderId(providerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitKeys.all, 'by-provider_id', providerId, page, size],
    queryFn: () => listCircuitByProviderId(providerId as string, page, size),
    enabled: Boolean(providerId),
  });
}

export function useCircuitsByASiteId(aSiteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitKeys.all, 'by-a_site_id', aSiteId, page, size],
    queryFn: () => listCircuitByASiteId(aSiteId as string, page, size),
    enabled: Boolean(aSiteId),
  });
}

export function useCircuitsByZSiteId(zSiteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitKeys.all, 'by-z_site_id', zSiteId, page, size],
    queryFn: () => listCircuitByZSiteId(zSiteId as string, page, size),
    enabled: Boolean(zSiteId),
  });
}
