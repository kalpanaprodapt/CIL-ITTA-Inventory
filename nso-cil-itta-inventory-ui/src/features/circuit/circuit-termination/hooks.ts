import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCircuitTerminations,
  changeCircuitTerminationStatus,
  createCircuitTermination,
  deleteCircuitTermination,
  exportCircuitTerminationsCsv,
  getCircuitTermination,
  getCircuitTerminationStatusStats,
  listCircuitTerminationSummaries,
  searchCircuitTerminations,
  updateCircuitTermination,
  listCircuitTerminationByCircuitId,
  listCircuitTerminationByPortId,
} from './api';
import type { CircuitTerminationFilter, CircuitTerminationInput, CircuitTerminationUpdateInput } from './types';

export const circuitTerminationKeys = {
  all: ['circuitTermination'] as const,
  lists: () => [...circuitTerminationKeys.all, 'list'] as const,
  list: (filter: CircuitTerminationFilter, page: number, size: number, sort?: string) =>
    [...circuitTerminationKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...circuitTerminationKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...circuitTerminationKeys.all, 'summaries', status] as const,
  stats: () => [...circuitTerminationKeys.all, 'stats'] as const,
};

export function useCircuitTerminationList(filter: CircuitTerminationFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: circuitTerminationKeys.list(filter, page, size, sort),
    queryFn: () => searchCircuitTerminations(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCircuitTermination(id: string | undefined) {
  return useQuery({
    queryKey: circuitTerminationKeys.detail(id ?? ''),
    queryFn: () => getCircuitTermination(id as string),
    enabled: Boolean(id),
  });
}

export function useCircuitTerminationSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: circuitTerminationKeys.summaries(status),
    queryFn: () => listCircuitTerminationSummaries(status),
    staleTime: 60_000,
  });
}

export function useCircuitTerminationStatusStats() {
  return useQuery({
    queryKey: circuitTerminationKeys.stats(),
    queryFn: getCircuitTerminationStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCircuitTermination() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitTerminationInput) => createCircuitTermination(input),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitTerminationKeys.all }),
  });
}

export function useUpdateCircuitTermination(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitTerminationUpdateInput) => updateCircuitTermination(id, input),
    onSuccess: (updated) => {
      client.setQueryData(circuitTerminationKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: circuitTerminationKeys.lists() });
    },
  });
}

export function useChangeCircuitTerminationStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCircuitTerminationStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitTerminationKeys.all }),
  });
}

export function useDeleteCircuitTermination() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCircuitTermination(id),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitTerminationKeys.all }),
  });
}

export function useBulkCreateCircuitTerminations() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CircuitTerminationInput[]) => bulkCreateCircuitTerminations(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitTerminationKeys.all }),
  });
}

export function useExportCircuitTerminations() {
  return useMutation({
    mutationFn: (filter: CircuitTerminationFilter) => exportCircuitTerminationsCsv(filter),
  });
}

export function useCircuitTerminationsByCircuitId(circuitId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitTerminationKeys.all, 'by-circuit_id', circuitId, page, size],
    queryFn: () => listCircuitTerminationByCircuitId(circuitId as string, page, size),
    enabled: Boolean(circuitId),
  });
}

export function useCircuitTerminationsByPortId(portId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitTerminationKeys.all, 'by-port_id', portId, page, size],
    queryFn: () => listCircuitTerminationByPortId(portId as string, page, size),
    enabled: Boolean(portId),
  });
}
