import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateServiceDependencys,
  changeServiceDependencyStatus,
  createServiceDependency,
  deleteServiceDependency,
  exportServiceDependencysCsv,
  getServiceDependency,
  getServiceDependencyStatusStats,
  listServiceDependencySummaries,
  searchServiceDependencys,
  updateServiceDependency,
  listServiceDependencyByServiceId,
  listServiceDependencyByDependsOnId,
} from './api';
import type { ServiceDependencyFilter, ServiceDependencyInput, ServiceDependencyUpdateInput } from './types';

export const serviceDependencyKeys = {
  all: ['serviceDependency'] as const,
  lists: () => [...serviceDependencyKeys.all, 'list'] as const,
  list: (filter: ServiceDependencyFilter, page: number, size: number, sort?: string) =>
    [...serviceDependencyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...serviceDependencyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...serviceDependencyKeys.all, 'summaries', status] as const,
  stats: () => [...serviceDependencyKeys.all, 'stats'] as const,
};

export function useServiceDependencyList(filter: ServiceDependencyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: serviceDependencyKeys.list(filter, page, size, sort),
    queryFn: () => searchServiceDependencys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useServiceDependency(id: string | undefined) {
  return useQuery({
    queryKey: serviceDependencyKeys.detail(id ?? ''),
    queryFn: () => getServiceDependency(id as string),
    enabled: Boolean(id),
  });
}

export function useServiceDependencySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: serviceDependencyKeys.summaries(status),
    queryFn: () => listServiceDependencySummaries(status),
    staleTime: 60_000,
  });
}

export function useServiceDependencyStatusStats() {
  return useQuery({
    queryKey: serviceDependencyKeys.stats(),
    queryFn: getServiceDependencyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateServiceDependency() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceDependencyInput) => createServiceDependency(input),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceDependencyKeys.all }),
  });
}

export function useUpdateServiceDependency(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceDependencyUpdateInput) => updateServiceDependency(id, input),
    onSuccess: (updated) => {
      client.setQueryData(serviceDependencyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: serviceDependencyKeys.lists() });
    },
  });
}

export function useChangeServiceDependencyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeServiceDependencyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceDependencyKeys.all }),
  });
}

export function useDeleteServiceDependency() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteServiceDependency(id),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceDependencyKeys.all }),
  });
}

export function useBulkCreateServiceDependencys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ServiceDependencyInput[]) => bulkCreateServiceDependencys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceDependencyKeys.all }),
  });
}

export function useExportServiceDependencys() {
  return useMutation({
    mutationFn: (filter: ServiceDependencyFilter) => exportServiceDependencysCsv(filter),
  });
}

export function useServiceDependencysByServiceId(serviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceDependencyKeys.all, 'by-service_id', serviceId, page, size],
    queryFn: () => listServiceDependencyByServiceId(serviceId as string, page, size),
    enabled: Boolean(serviceId),
  });
}

export function useServiceDependencysByDependsOnId(dependsOnId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceDependencyKeys.all, 'by-depends_on_id', dependsOnId, page, size],
    queryFn: () => listServiceDependencyByDependsOnId(dependsOnId as string, page, size),
    enabled: Boolean(dependsOnId),
  });
}
