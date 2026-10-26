import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAssets,
  changeAssetStatus,
  createAsset,
  deleteAsset,
  exportAssetsCsv,
  getAsset,
  getAssetStatusStats,
  listAssetSummaries,
  searchAssets,
  updateAsset,

} from './api';
import type { AssetFilter, AssetInput, AssetUpdateInput } from './types';

export const assetKeys = {
  all: ['asset'] as const,
  lists: () => [...assetKeys.all, 'list'] as const,
  list: (filter: AssetFilter, page: number, size: number, sort?: string) =>
    [...assetKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...assetKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...assetKeys.all, 'summaries', status] as const,
  stats: () => [...assetKeys.all, 'stats'] as const,
};

export function useAssetList(filter: AssetFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: assetKeys.list(filter, page, size, sort),
    queryFn: () => searchAssets(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAsset(id: string | undefined) {
  return useQuery({
    queryKey: assetKeys.detail(id ?? ''),
    queryFn: () => getAsset(id as string),
    enabled: Boolean(id),
  });
}

export function useAssetSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: assetKeys.summaries(status),
    queryFn: () => listAssetSummaries(status),
    staleTime: 60_000,
  });
}

export function useAssetStatusStats() {
  return useQuery({
    queryKey: assetKeys.stats(),
    queryFn: getAssetStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAsset() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AssetInput) => createAsset(input),
    onSuccess: () => client.invalidateQueries({ queryKey: assetKeys.all }),
  });
}

export function useUpdateAsset(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AssetUpdateInput) => updateAsset(id, input),
    onSuccess: (updated) => {
      client.setQueryData(assetKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: assetKeys.lists() });
    },
  });
}

export function useChangeAssetStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAssetStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: assetKeys.all }),
  });
}

export function useDeleteAsset() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAsset(id),
    onSuccess: () => client.invalidateQueries({ queryKey: assetKeys.all }),
  });
}

export function useBulkCreateAssets() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AssetInput[]) => bulkCreateAssets(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: assetKeys.all }),
  });
}

export function useExportAssets() {
  return useMutation({
    mutationFn: (filter: AssetFilter) => exportAssetsCsv(filter),
  });
}
