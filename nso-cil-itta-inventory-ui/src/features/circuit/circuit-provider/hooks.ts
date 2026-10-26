import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCircuitProviders,
  changeCircuitProviderStatus,
  createCircuitProvider,
  deleteCircuitProvider,
  exportCircuitProvidersCsv,
  getCircuitProvider,
  getCircuitProviderStatusStats,
  listCircuitProviderSummaries,
  searchCircuitProviders,
  updateCircuitProvider,

} from './api';
import type { CircuitProviderFilter, CircuitProviderInput, CircuitProviderUpdateInput } from './types';

export const circuitProviderKeys = {
  all: ['circuitProvider'] as const,
  lists: () => [...circuitProviderKeys.all, 'list'] as const,
  list: (filter: CircuitProviderFilter, page: number, size: number, sort?: string) =>
    [...circuitProviderKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...circuitProviderKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...circuitProviderKeys.all, 'summaries', status] as const,
  stats: () => [...circuitProviderKeys.all, 'stats'] as const,
};

export function useCircuitProviderList(filter: CircuitProviderFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: circuitProviderKeys.list(filter, page, size, sort),
    queryFn: () => searchCircuitProviders(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCircuitProvider(id: string | undefined) {
  return useQuery({
    queryKey: circuitProviderKeys.detail(id ?? ''),
    queryFn: () => getCircuitProvider(id as string),
    enabled: Boolean(id),
  });
}

export function useCircuitProviderSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: circuitProviderKeys.summaries(status),
    queryFn: () => listCircuitProviderSummaries(status),
    staleTime: 60_000,
  });
}

export function useCircuitProviderStatusStats() {
  return useQuery({
    queryKey: circuitProviderKeys.stats(),
    queryFn: getCircuitProviderStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCircuitProvider() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitProviderInput) => createCircuitProvider(input),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitProviderKeys.all }),
  });
}

export function useUpdateCircuitProvider(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitProviderUpdateInput) => updateCircuitProvider(id, input),
    onSuccess: (updated) => {
      client.setQueryData(circuitProviderKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: circuitProviderKeys.lists() });
    },
  });
}

export function useChangeCircuitProviderStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCircuitProviderStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitProviderKeys.all }),
  });
}

export function useDeleteCircuitProvider() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCircuitProvider(id),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitProviderKeys.all }),
  });
}

export function useBulkCreateCircuitProviders() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CircuitProviderInput[]) => bulkCreateCircuitProviders(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitProviderKeys.all }),
  });
}

export function useExportCircuitProviders() {
  return useMutation({
    mutationFn: (filter: CircuitProviderFilter) => exportCircuitProvidersCsv(filter),
  });
}
