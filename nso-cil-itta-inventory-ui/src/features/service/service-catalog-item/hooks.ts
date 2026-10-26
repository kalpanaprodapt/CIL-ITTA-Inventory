import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateServiceCatalogItems,
  changeServiceCatalogItemStatus,
  createServiceCatalogItem,
  deleteServiceCatalogItem,
  exportServiceCatalogItemsCsv,
  getServiceCatalogItem,
  getServiceCatalogItemStatusStats,
  listServiceCatalogItemSummaries,
  searchServiceCatalogItems,
  updateServiceCatalogItem,

} from './api';
import type { ServiceCatalogItemFilter, ServiceCatalogItemInput, ServiceCatalogItemUpdateInput } from './types';

export const serviceCatalogItemKeys = {
  all: ['serviceCatalogItem'] as const,
  lists: () => [...serviceCatalogItemKeys.all, 'list'] as const,
  list: (filter: ServiceCatalogItemFilter, page: number, size: number, sort?: string) =>
    [...serviceCatalogItemKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...serviceCatalogItemKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...serviceCatalogItemKeys.all, 'summaries', status] as const,
  stats: () => [...serviceCatalogItemKeys.all, 'stats'] as const,
};

export function useServiceCatalogItemList(filter: ServiceCatalogItemFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: serviceCatalogItemKeys.list(filter, page, size, sort),
    queryFn: () => searchServiceCatalogItems(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useServiceCatalogItem(id: string | undefined) {
  return useQuery({
    queryKey: serviceCatalogItemKeys.detail(id ?? ''),
    queryFn: () => getServiceCatalogItem(id as string),
    enabled: Boolean(id),
  });
}

export function useServiceCatalogItemSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: serviceCatalogItemKeys.summaries(status),
    queryFn: () => listServiceCatalogItemSummaries(status),
    staleTime: 60_000,
  });
}

export function useServiceCatalogItemStatusStats() {
  return useQuery({
    queryKey: serviceCatalogItemKeys.stats(),
    queryFn: getServiceCatalogItemStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateServiceCatalogItem() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceCatalogItemInput) => createServiceCatalogItem(input),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceCatalogItemKeys.all }),
  });
}

export function useUpdateServiceCatalogItem(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: ServiceCatalogItemUpdateInput) => updateServiceCatalogItem(id, input),
    onSuccess: (updated) => {
      client.setQueryData(serviceCatalogItemKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: serviceCatalogItemKeys.lists() });
    },
  });
}

export function useChangeServiceCatalogItemStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeServiceCatalogItemStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceCatalogItemKeys.all }),
  });
}

export function useDeleteServiceCatalogItem() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteServiceCatalogItem(id),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceCatalogItemKeys.all }),
  });
}

export function useBulkCreateServiceCatalogItems() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: ServiceCatalogItemInput[]) => bulkCreateServiceCatalogItems(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: serviceCatalogItemKeys.all }),
  });
}

export function useExportServiceCatalogItems() {
  return useMutation({
    mutationFn: (filter: ServiceCatalogItemFilter) => exportServiceCatalogItemsCsv(filter),
  });
}
