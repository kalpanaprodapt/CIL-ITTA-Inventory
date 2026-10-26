import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateServiceEndpoints,
  changeServiceEndpointStatus,
  createServiceEndpoint,
  deleteServiceEndpoint,
  exportServiceEndpointsCsv,
  getServiceEndpoint,
  getServiceEndpointStatusStats,
  listServiceEndpointSummaries,
  searchServiceEndpoints,
  updateServiceEndpoint,
  listServiceEndpointByServiceId,
  listServiceEndpointBySiteId,
  listServiceEndpointByPortId,
} from './api';
import type { ServiceEndpointFilter, ServiceEndpointInput, ServiceEndpointUpdateInput } from './types';

export const serviceEndpointKeys = {
  all: ['serviceEndpoint'] as const,
  lists: () => [...serviceEndpointKeys.all, 'list'] as const,
  list: (filter: ServiceEndpointFilter, page: number, size: number, sort?: string) =>
    [...serviceEndpointKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...serviceEndpointKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...serviceEndpointKeys.all, 'summaries', status] as const,
  stats: () => [...serviceEndpointKeys.all, 'stats'] as const,
};

export function useServiceEndpointList(filter: ServiceEndpointFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: serviceEndpointKeys.list(filter, page, size, sort),
    queryFn: () => searchServiceEndpoints(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useServiceEndpoint(id: string | undefined) {
  return useQuery({
    queryKey: serviceEndpointKeys.detail(id ?? ''),
    queryFn: () => getServiceEndpoint(id as string),
    enabled: Boolean(id),
  });
}

export function useServiceEndpointSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: serviceEndpointKeys.summaries(status),
    queryFn: () => listServiceEndpointSummaries(status),
    staleTime: 60_000,
  });
}

export function useServiceEndpointStatusStats() {
  return useQuery({
    queryKey: serviceEndpointKeys.stats(),
    queryFn: getServiceEndpointStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateServiceEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceEndpointInput) => createServiceEndpoint(input),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceEndpointKeys.all }),
  });
}

export function useUpdateServiceEndpoint(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceEndpointUpdateInput) => updateServiceEndpoint(id, input),
    onSuccess: (updated) => {
      client.setQueryData(serviceEndpointKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: serviceEndpointKeys.lists() });
    },
  });
}

export function useChangeServiceEndpointStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeServiceEndpointStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceEndpointKeys.all }),
  });
}

export function useDeleteServiceEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteServiceEndpoint(id),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceEndpointKeys.all }),
  });
}

export function useBulkCreateServiceEndpoints() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ServiceEndpointInput[]) => bulkCreateServiceEndpoints(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceEndpointKeys.all }),
  });
}

export function useExportServiceEndpoints() {
  return useMutation({
    mutationFn: (filter: ServiceEndpointFilter) => exportServiceEndpointsCsv(filter),
  });
}

export function useServiceEndpointsByServiceId(serviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceEndpointKeys.all, 'by-service_id', serviceId, page, size],
    queryFn: () => listServiceEndpointByServiceId(serviceId as string, page, size),
    enabled: Boolean(serviceId),
  });
}

export function useServiceEndpointsBySiteId(siteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceEndpointKeys.all, 'by-site_id', siteId, page, size],
    queryFn: () => listServiceEndpointBySiteId(siteId as string, page, size),
    enabled: Boolean(siteId),
  });
}

export function useServiceEndpointsByPortId(portId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...serviceEndpointKeys.all, 'by-port_id', portId, page, size],
    queryFn: () => listServiceEndpointByPortId(portId as string, page, size),
    enabled: Boolean(portId),
  });
}
