import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateIntegrationEndpoints,
  changeIntegrationEndpointStatus,
  createIntegrationEndpoint,
  deleteIntegrationEndpoint,
  exportIntegrationEndpointsCsv,
  getIntegrationEndpoint,
  getIntegrationEndpointStatusStats,
  listIntegrationEndpointSummaries,
  searchIntegrationEndpoints,
  updateIntegrationEndpoint,

} from './api';
import type { IntegrationEndpointFilter, IntegrationEndpointInput, IntegrationEndpointUpdateInput } from './types';

export const integrationEndpointKeys = {
  all: ['integrationEndpoint'] as const,
  lists: () => [...integrationEndpointKeys.all, 'list'] as const,
  list: (filter: IntegrationEndpointFilter, page: number, size: number, sort?: string) =>
    [...integrationEndpointKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...integrationEndpointKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...integrationEndpointKeys.all, 'summaries', status] as const,
  stats: () => [...integrationEndpointKeys.all, 'stats'] as const,
};

export function useIntegrationEndpointList(filter: IntegrationEndpointFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: integrationEndpointKeys.list(filter, page, size, sort),
    queryFn: () => searchIntegrationEndpoints(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useIntegrationEndpoint(id: string | undefined) {
  return useQuery({
    queryKey: integrationEndpointKeys.detail(id ?? ''),
    queryFn: () => getIntegrationEndpoint(id as string),
    enabled: Boolean(id),
  });
}

export function useIntegrationEndpointSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: integrationEndpointKeys.summaries(status),
    queryFn: () => listIntegrationEndpointSummaries(status),
    staleTime: 60_000,
  });
}

export function useIntegrationEndpointStatusStats() {
  return useQuery({
    queryKey: integrationEndpointKeys.stats(),
    queryFn: getIntegrationEndpointStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateIntegrationEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IntegrationEndpointInput) => createIntegrationEndpoint(input),
    onSuccess: () => client.invalidateQueries({ queryKey: integrationEndpointKeys.all }),
  });
}

export function useUpdateIntegrationEndpoint(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: IntegrationEndpointUpdateInput) => updateIntegrationEndpoint(id, input),
    onSuccess: (updated) => {
      client.setQueryData(integrationEndpointKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: integrationEndpointKeys.lists() });
    },
  });
}

export function useChangeIntegrationEndpointStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeIntegrationEndpointStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: integrationEndpointKeys.all }),
  });
}

export function useDeleteIntegrationEndpoint() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteIntegrationEndpoint(id),
    onSuccess: () => client.invalidateQueries({ queryKey: integrationEndpointKeys.all }),
  });
}

export function useBulkCreateIntegrationEndpoints() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: IntegrationEndpointInput[]) => bulkCreateIntegrationEndpoints(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: integrationEndpointKeys.all }),
  });
}

export function useExportIntegrationEndpoints() {
  return useMutation({
    mutationFn: (filter: IntegrationEndpointFilter) => exportIntegrationEndpointsCsv(filter),
  });
}
