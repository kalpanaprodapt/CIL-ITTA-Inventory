import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateWarrantys,
  changeWarrantyStatus,
  createWarranty,
  deleteWarranty,
  exportWarrantysCsv,
  getWarranty,
  getWarrantyStatusStats,
  listWarrantySummaries,
  searchWarrantys,
  updateWarranty,
  listWarrantyByAssetId,
} from './api';
import type { WarrantyFilter, WarrantyInput, WarrantyUpdateInput } from './types';

export const warrantyKeys = {
  all: ['warranty'] as const,
  lists: () => [...warrantyKeys.all, 'list'] as const,
  list: (filter: WarrantyFilter, page: number, size: number, sort?: string) =>
    [...warrantyKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...warrantyKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...warrantyKeys.all, 'summaries', status] as const,
  stats: () => [...warrantyKeys.all, 'stats'] as const,
};

export function useWarrantyList(filter: WarrantyFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: warrantyKeys.list(filter, page, size, sort),
    queryFn: () => searchWarrantys(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useWarranty(id: string | undefined) {
  return useQuery({
    queryKey: warrantyKeys.detail(id ?? ''),
    queryFn: () => getWarranty(id as string),
    enabled: Boolean(id),
  });
}

export function useWarrantySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: warrantyKeys.summaries(status),
    queryFn: () => listWarrantySummaries(status),
    staleTime: 60_000,
  });
}

export function useWarrantyStatusStats() {
  return useQuery({
    queryKey: warrantyKeys.stats(),
    queryFn: getWarrantyStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateWarranty() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WarrantyInput) => createWarranty(input),
    onSuccess: () => client.invalidateQueries({ queryKey: warrantyKeys.all }),
  });
}

export function useUpdateWarranty(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WarrantyUpdateInput) => updateWarranty(id, input),
    onSuccess: (updated) => {
      client.setQueryData(warrantyKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: warrantyKeys.lists() });
    },
  });
}

export function useChangeWarrantyStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeWarrantyStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: warrantyKeys.all }),
  });
}

export function useDeleteWarranty() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteWarranty(id),
    onSuccess: () => client.invalidateQueries({ queryKey: warrantyKeys.all }),
  });
}

export function useBulkCreateWarrantys() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: WarrantyInput[]) => bulkCreateWarrantys(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: warrantyKeys.all }),
  });
}

export function useExportWarrantys() {
  return useMutation({
    mutationFn: (filter: WarrantyFilter) => exportWarrantysCsv(filter),
  });
}

export function useWarrantysByAssetId(assetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...warrantyKeys.all, 'by-asset_id', assetId, page, size],
    queryFn: () => listWarrantyByAssetId(assetId as string, page, size),
    enabled: Boolean(assetId),
  });
}
