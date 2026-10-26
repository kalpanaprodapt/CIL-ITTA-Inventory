import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAssetTags,
  changeAssetTagStatus,
  createAssetTag,
  deleteAssetTag,
  exportAssetTagsCsv,
  getAssetTag,
  getAssetTagStatusStats,
  listAssetTagSummaries,
  searchAssetTags,
  updateAssetTag,
  listAssetTagByAssetId,
} from './api';
import type { AssetTagFilter, AssetTagInput, AssetTagUpdateInput } from './types';

export const assetTagKeys = {
  all: ['assetTag'] as const,
  lists: () => [...assetTagKeys.all, 'list'] as const,
  list: (filter: AssetTagFilter, page: number, size: number, sort?: string) =>
    [...assetTagKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...assetTagKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...assetTagKeys.all, 'summaries', status] as const,
  stats: () => [...assetTagKeys.all, 'stats'] as const,
};

export function useAssetTagList(filter: AssetTagFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: assetTagKeys.list(filter, page, size, sort),
    queryFn: () => searchAssetTags(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAssetTag(id: string | undefined) {
  return useQuery({
    queryKey: assetTagKeys.detail(id ?? ''),
    queryFn: () => getAssetTag(id as string),
    enabled: Boolean(id),
  });
}

export function useAssetTagSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: assetTagKeys.summaries(status),
    queryFn: () => listAssetTagSummaries(status),
    staleTime: 60_000,
  });
}

export function useAssetTagStatusStats() {
  return useQuery({
    queryKey: assetTagKeys.stats(),
    queryFn: getAssetTagStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAssetTag() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AssetTagInput) => createAssetTag(input),
    onSuccess: () => client.invalidateQueries({ queryKey: assetTagKeys.all }),
  });
}

export function useUpdateAssetTag(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AssetTagUpdateInput) => updateAssetTag(id, input),
    onSuccess: (updated) => {
      client.setQueryData(assetTagKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: assetTagKeys.lists() });
    },
  });
}

export function useChangeAssetTagStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAssetTagStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: assetTagKeys.all }),
  });
}

export function useDeleteAssetTag() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAssetTag(id),
    onSuccess: () => client.invalidateQueries({ queryKey: assetTagKeys.all }),
  });
}

export function useBulkCreateAssetTags() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AssetTagInput[]) => bulkCreateAssetTags(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: assetTagKeys.all }),
  });
}

export function useExportAssetTags() {
  return useMutation({
    mutationFn: (filter: AssetTagFilter) => exportAssetTagsCsv(filter),
  });
}

export function useAssetTagsByAssetId(assetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...assetTagKeys.all, 'by-asset_id', assetId, page, size],
    queryFn: () => listAssetTagByAssetId(assetId as string, page, size),
    enabled: Boolean(assetId),
  });
}
