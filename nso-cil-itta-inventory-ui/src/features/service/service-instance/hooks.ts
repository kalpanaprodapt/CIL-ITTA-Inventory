import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateServiceInstances,
  changeServiceInstanceStatus,
  createServiceInstance,
  deleteServiceInstance,
  exportServiceInstancesCsv,
  getServiceInstance,
  getServiceInstanceStatusStats,
  listServiceInstanceSummaries,
  searchServiceInstances,
  updateServiceInstance,
  listServiceInstanceByCatalogItemId,
  listServiceInstanceByCustomerId,
} from './api';
import type { ServiceInstanceFilter, ServiceInstanceInput, ServiceInstanceUpdateInput } from './types';

export const serviceInstanceKeys = {
  all: ['serviceInstance'] as const,
  lists: () => [...serviceInstanceKeys.all, 'list'] as const,
  list: (filter: ServiceInstanceFilter, page: number, size: number, sort?: string) =>
    [...serviceInstanceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...serviceInstanceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...serviceInstanceKeys.all, 'summaries', status] as const,
  stats: () => [...serviceInstanceKeys.all, 'stats'] as const,
};

export function useServiceInstanceList(filter: ServiceInstanceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: serviceInstanceKeys.list(filter, page, size, sort),
    queryFn: () => searchServiceInstances(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useServiceInstance(id: string | undefined) {
  return useQuery({
    queryKey: serviceInstanceKeys.detail(id ?? ''),
    queryFn: () => getServiceInstance(id as string),
    enabled: Boolean(id),
  });
}

export function useServiceInstanceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: serviceInstanceKeys.summaries(status),
    queryFn: () => listServiceInstanceSummaries(status),
    staleTime: 60_000,
  });
}

export function useServiceInstanceStatusStats() {
  return useQuery({
    queryKey: serviceInstanceKeys.stats(),
    queryFn: getServiceInstanceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateServiceInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceInstanceInput) => createServiceInstance(input),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceInstanceKeys.all }),
  });
}

export function useUpdateServiceInstance(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceInstanceUpdateInput) => updateServiceInstance(id, input),
    onSuccess: (updated) => {
      client.setQueryData(serviceInstanceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: serviceInstanceKeys.lists() });
    },
  });
}

export function useChangeServiceInstanceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeServiceInstanceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceInstanceKeys.all }),
  });
}

export function useDeleteServiceInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteServiceInstance(id),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceInstanceKeys.all }),
  });
}

export function useBulkCreateServiceInstances() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ServiceInstanceInput[]) => bulkCreateServiceInstances(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceInstanceKeys.all }),
  });
}

export function useExportServiceInstances() {
  return useMutation({
    mutationFn: (filter: ServiceInstanceFilter) => exportServiceInstancesCsv(filter),
  });
}

export function useServiceInstancesByCatalogItemId(catalogItemId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceInstanceKeys.all, 'by-catalog_item_id', catalogItemId, page, size],
    queryFn: () => listServiceInstanceByCatalogItemId(catalogItemId as string, page, size),
    enabled: Boolean(catalogItemId),
  });
}

export function useServiceInstancesByCustomerId(customerId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceInstanceKeys.all, 'by-customer_id', customerId, page, size],
    queryFn: () => listServiceInstanceByCustomerId(customerId as string, page, size),
    enabled: Boolean(customerId),
  });
}
