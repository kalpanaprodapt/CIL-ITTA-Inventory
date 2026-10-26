import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCircuitOrders,
  changeCircuitOrderStatus,
  createCircuitOrder,
  deleteCircuitOrder,
  exportCircuitOrdersCsv,
  getCircuitOrder,
  getCircuitOrderStatusStats,
  listCircuitOrderSummaries,
  searchCircuitOrders,
  updateCircuitOrder,
  listCircuitOrderByCircuitId,
} from './api';
import type { CircuitOrderFilter, CircuitOrderInput, CircuitOrderUpdateInput } from './types';

export const circuitOrderKeys = {
  all: ['circuitOrder'] as const,
  lists: () => [...circuitOrderKeys.all, 'list'] as const,
  list: (filter: CircuitOrderFilter, page: number, size: number, sort?: string) =>
    [...circuitOrderKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...circuitOrderKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...circuitOrderKeys.all, 'summaries', status] as const,
  stats: () => [...circuitOrderKeys.all, 'stats'] as const,
};

export function useCircuitOrderList(filter: CircuitOrderFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: circuitOrderKeys.list(filter, page, size, sort),
    queryFn: () => searchCircuitOrders(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCircuitOrder(id: string | undefined) {
  return useQuery({
    queryKey: circuitOrderKeys.detail(id ?? ''),
    queryFn: () => getCircuitOrder(id as string),
    enabled: Boolean(id),
  });
}

export function useCircuitOrderSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: circuitOrderKeys.summaries(status),
    queryFn: () => listCircuitOrderSummaries(status),
    staleTime: 60_000,
  });
}

export function useCircuitOrderStatusStats() {
  return useQuery({
    queryKey: circuitOrderKeys.stats(),
    queryFn: getCircuitOrderStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCircuitOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitOrderInput) => createCircuitOrder(input),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitOrderKeys.all }),
  });
}

export function useUpdateCircuitOrder(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CircuitOrderUpdateInput) => updateCircuitOrder(id, input),
    onSuccess: (updated) => {
      client.setQueryData(circuitOrderKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: circuitOrderKeys.lists() });
    },
  });
}

export function useChangeCircuitOrderStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCircuitOrderStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitOrderKeys.all }),
  });
}

export function useDeleteCircuitOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCircuitOrder(id),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitOrderKeys.all }),
  });
}

export function useBulkCreateCircuitOrders() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CircuitOrderInput[]) => bulkCreateCircuitOrders(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: circuitOrderKeys.all }),
  });
}

export function useExportCircuitOrders() {
  return useMutation({
    mutationFn: (filter: CircuitOrderFilter) => exportCircuitOrdersCsv(filter),
  });
}

export function useCircuitOrdersByCircuitId(circuitId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...circuitOrderKeys.all, 'by-circuit_id', circuitId, page, size],
    queryFn: () => listCircuitOrderByCircuitId(circuitId as string, page, size),
    enabled: Boolean(circuitId),
  });
}
