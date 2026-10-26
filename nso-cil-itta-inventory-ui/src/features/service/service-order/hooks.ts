import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateServiceOrders,
  changeServiceOrderStatus,
  createServiceOrder,
  deleteServiceOrder,
  exportServiceOrdersCsv,
  getServiceOrder,
  getServiceOrderStatusStats,
  listServiceOrderSummaries,
  searchServiceOrders,
  updateServiceOrder,
  listServiceOrderByCustomerId,
} from './api';
import type { ServiceOrderFilter, ServiceOrderInput, ServiceOrderUpdateInput } from './types';

export const serviceOrderKeys = {
  all: ['serviceOrder'] as const,
  lists: () => [...serviceOrderKeys.all, 'list'] as const,
  list: (filter: ServiceOrderFilter, page: number, size: number, sort?: string) =>
    [...serviceOrderKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...serviceOrderKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...serviceOrderKeys.all, 'summaries', status] as const,
  stats: () => [...serviceOrderKeys.all, 'stats'] as const,
};

export function useServiceOrderList(filter: ServiceOrderFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: serviceOrderKeys.list(filter, page, size, sort),
    queryFn: () => searchServiceOrders(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useServiceOrder(id: string | undefined) {
  return useQuery({
    queryKey: serviceOrderKeys.detail(id ?? ''),
    queryFn: () => getServiceOrder(id as string),
    enabled: Boolean(id),
  });
}

export function useServiceOrderSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: serviceOrderKeys.summaries(status),
    queryFn: () => listServiceOrderSummaries(status),
    staleTime: 60_000,
  });
}

export function useServiceOrderStatusStats() {
  return useQuery({
    queryKey: serviceOrderKeys.stats(),
    queryFn: getServiceOrderStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateServiceOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceOrderInput) => createServiceOrder(input),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceOrderKeys.all }),
  });
}

export function useUpdateServiceOrder(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceOrderUpdateInput) => updateServiceOrder(id, input),
    onSuccess: (updated) => {
      client.setQueryData(serviceOrderKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: serviceOrderKeys.lists() });
    },
  });
}

export function useChangeServiceOrderStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeServiceOrderStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceOrderKeys.all }),
  });
}

export function useDeleteServiceOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteServiceOrder(id),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceOrderKeys.all }),
  });
}

export function useBulkCreateServiceOrders() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ServiceOrderInput[]) => bulkCreateServiceOrders(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceOrderKeys.all }),
  });
}

export function useExportServiceOrders() {
  return useMutation({
    mutationFn: (filter: ServiceOrderFilter) => exportServiceOrdersCsv(filter),
  });
}

export function useServiceOrdersByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceOrderKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listServiceOrderByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
