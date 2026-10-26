import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateDiscoveredAssets,
  changeDiscoveredAssetStatus,
  createDiscoveredAsset,
  deleteDiscoveredAsset,
  exportDiscoveredAssetsCsv,
  getDiscoveredAsset,
  getDiscoveredAssetStatusStats,
  listDiscoveredAssetSummaries,
  searchDiscoveredAssets,
  updateDiscoveredAsset,
  listDiscoveredAssetByJobId,
  listDiscoveredAssetByMatchedAssetId,
} from './api';
import type { DiscoveredAssetFilter, DiscoveredAssetInput, DiscoveredAssetUpdateInput } from './types';

export const discoveredAssetKeys = {
  all: ['discoveredAsset'] as const,
  lists: () => [...discoveredAssetKeys.all, 'list'] as const,
  list: (filter: DiscoveredAssetFilter, page: number, size: number, sort?: string) =>
    [...discoveredAssetKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...discoveredAssetKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...discoveredAssetKeys.all, 'summaries', status] as const,
  stats: () => [...discoveredAssetKeys.all, 'stats'] as const,
};

export function useDiscoveredAssetList(filter: DiscoveredAssetFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: discoveredAssetKeys.list(filter, page, size, sort),
    queryFn: () => searchDiscoveredAssets(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useDiscoveredAsset(id: string | undefined) {
  return useQuery({
    queryKey: discoveredAssetKeys.detail(id ?? ''),
    queryFn: () => getDiscoveredAsset(id as string),
    enabled: Boolean(id),
  });
}

export function useDiscoveredAssetSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: discoveredAssetKeys.summaries(status),
    queryFn: () => listDiscoveredAssetSummaries(status),
    staleTime: 60_000,
  });
}

export function useDiscoveredAssetStatusStats() {
  return useQuery({
    queryKey: discoveredAssetKeys.stats(),
    queryFn: getDiscoveredAssetStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateDiscoveredAsset() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveredAssetInput) => createDiscoveredAsset(input),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveredAssetKeys.all }),
  });
}

export function useUpdateDiscoveredAsset(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveredAssetUpdateInput) => updateDiscoveredAsset(id, input),
    onSuccess: (updated) => {
      client.setQueryData(discoveredAssetKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: discoveredAssetKeys.lists() });
    },
  });
}

export function useChangeDiscoveredAssetStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeDiscoveredAssetStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveredAssetKeys.all }),
  });
}

export function useDeleteDiscoveredAsset() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteDiscoveredAsset(id),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveredAssetKeys.all }),
  });
}

export function useBulkCreateDiscoveredAssets() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: DiscoveredAssetInput[]) => bulkCreateDiscoveredAssets(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveredAssetKeys.all }),
  });
}

export function useExportDiscoveredAssets() {
  return useMutation({
    mutationFn: (filter: DiscoveredAssetFilter) => exportDiscoveredAssetsCsv(filter),
  });
}

export function useDiscoveredAssetsByJobId(jobId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...discoveredAssetKeys.all, 'by-job_id', jobId, page, size],
    queryFn: () => listDiscoveredAssetByJobId(jobId as string, page, size),
    enabled: Boolean(jobId),
  });
}

export function useDiscoveredAssetsByMatchedAssetId(matchedAssetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...discoveredAssetKeys.all, 'by-matched_asset_id', matchedAssetId, page, size],
    queryFn: () => listDiscoveredAssetByMatchedAssetId(matchedAssetId as string, page, size),
    enabled: Boolean(matchedAssetId),
  });
}
