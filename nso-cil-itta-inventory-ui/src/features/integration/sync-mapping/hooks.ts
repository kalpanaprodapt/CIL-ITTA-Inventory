import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSyncMappings,
  changeSyncMappingStatus,
  createSyncMapping,
  deleteSyncMapping,
  exportSyncMappingsCsv,
  getSyncMapping,
  getSyncMappingStatusStats,
  listSyncMappingSummaries,
  searchSyncMappings,
  updateSyncMapping,
  listSyncMappingByJobId,
} from './api';
import type { SyncMappingFilter, SyncMappingInput, SyncMappingUpdateInput } from './types';

export const syncMappingKeys = {
  all: ['syncMapping'] as const,
  lists: () => [...syncMappingKeys.all, 'list'] as const,
  list: (filter: SyncMappingFilter, page: number, size: number, sort?: string) =>
    [...syncMappingKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...syncMappingKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...syncMappingKeys.all, 'summaries', status] as const,
  stats: () => [...syncMappingKeys.all, 'stats'] as const,
};

export function useSyncMappingList(filter: SyncMappingFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: syncMappingKeys.list(filter, page, size, sort),
    queryFn: () => searchSyncMappings(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSyncMapping(id: string | undefined) {
  return useQuery({
    queryKey: syncMappingKeys.detail(id ?? ''),
    queryFn: () => getSyncMapping(id as string),
    enabled: Boolean(id),
  });
}

export function useSyncMappingSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: syncMappingKeys.summaries(status),
    queryFn: () => listSyncMappingSummaries(status),
    staleTime: 60_000,
  });
}

export function useSyncMappingStatusStats() {
  return useQuery({
    queryKey: syncMappingKeys.stats(),
    queryFn: getSyncMappingStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSyncMapping() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SyncMappingInput) => createSyncMapping(input),
    onSuccess: () => client.invalidateQueries({ queryKey: syncMappingKeys.all }),
  });
}

export function useUpdateSyncMapping(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SyncMappingUpdateInput) => updateSyncMapping(id, input),
    onSuccess: (updated) => {
      client.setQueryData(syncMappingKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: syncMappingKeys.lists() });
    },
  });
}

export function useChangeSyncMappingStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSyncMappingStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: syncMappingKeys.all }),
  });
}

export function useDeleteSyncMapping() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSyncMapping(id),
    onSuccess: () => client.invalidateQueries({ queryKey: syncMappingKeys.all }),
  });
}

export function useBulkCreateSyncMappings() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SyncMappingInput[]) => bulkCreateSyncMappings(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: syncMappingKeys.all }),
  });
}

export function useExportSyncMappings() {
  return useMutation({
    mutationFn: (filter: SyncMappingFilter) => exportSyncMappingsCsv(filter),
  });
}

export function useSyncMappingsByJobId(jobId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...syncMappingKeys.all, 'by-job_id', jobId, page, size],
    queryFn: () => listSyncMappingByJobId(jobId as string, page, size),
    enabled: Boolean(jobId),
  });
}
