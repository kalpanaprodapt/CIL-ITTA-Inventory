import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSkus,
  changeSkuStatus,
  createSku,
  deleteSku,
  exportSkusCsv,
  getSku,
  getSkuStatusStats,
  listSkuSummaries,
  searchSkus,
  updateSku,
  listSkuByModelId,
} from './api';
import type { SkuFilter, SkuInput, SkuUpdateInput } from './types';

export const skuKeys = {
  all: ['sku'] as const,
  lists: () => [...skuKeys.all, 'list'] as const,
  list: (filter: SkuFilter, page: number, size: number, sort?: string) =>
    [...skuKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...skuKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...skuKeys.all, 'summaries', status] as const,
  stats: () => [...skuKeys.all, 'stats'] as const,
};

export function useSkuList(filter: SkuFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: skuKeys.list(filter, page, size, sort),
    queryFn: () => searchSkus(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSku(id: string | undefined) {
  return useQuery({
    queryKey: skuKeys.detail(id ?? ''),
    queryFn: () => getSku(id as string),
    enabled: Boolean(id),
  });
}

export function useSkuSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: skuKeys.summaries(status),
    queryFn: () => listSkuSummaries(status),
    staleTime: 60_000,
  });
}

export function useSkuStatusStats() {
  return useQuery({
    queryKey: skuKeys.stats(),
    queryFn: getSkuStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSku() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SkuInput) => createSku(input),
    onSuccess: () => client.invalidateQueries({ queryKey: skuKeys.all }),
  });
}

export function useUpdateSku(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SkuUpdateInput) => updateSku(id, input),
    onSuccess: (updated) => {
      client.setQueryData(skuKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: skuKeys.lists() });
    },
  });
}

export function useChangeSkuStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSkuStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: skuKeys.all }),
  });
}

export function useDeleteSku() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSku(id),
    onSuccess: () => client.invalidateQueries({ queryKey: skuKeys.all }),
  });
}

export function useBulkCreateSkus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SkuInput[]) => bulkCreateSkus(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: skuKeys.all }),
  });
}

export function useExportSkus() {
  return useMutation({
    mutationFn: (filter: SkuFilter) => exportSkusCsv(filter),
  });
}

export function useSkusByModelId(modelId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...skuKeys.all, 'by-model_id', modelId, page, size],
    queryFn: () => listSkuByModelId(modelId as string, page, size),
    enabled: Boolean(modelId),
  });
}
