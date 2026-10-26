import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSyncJobs,
  changeSyncJobStatus,
  createSyncJob,
  deleteSyncJob,
  exportSyncJobsCsv,
  getSyncJob,
  getSyncJobStatusStats,
  listSyncJobSummaries,
  searchSyncJobs,
  updateSyncJob,
  listSyncJobByEndpointId,
} from './api';
import type { SyncJobFilter, SyncJobInput, SyncJobUpdateInput } from './types';

export const syncJobKeys = {
  all: ['syncJob'] as const,
  lists: () => [...syncJobKeys.all, 'list'] as const,
  list: (filter: SyncJobFilter, page: number, size: number, sort?: string) =>
    [...syncJobKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...syncJobKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...syncJobKeys.all, 'summaries', status] as const,
  stats: () => [...syncJobKeys.all, 'stats'] as const,
};

export function useSyncJobList(filter: SyncJobFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: syncJobKeys.list(filter, page, size, sort),
    queryFn: () => searchSyncJobs(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSyncJob(id: string | undefined) {
  return useQuery({
    queryKey: syncJobKeys.detail(id ?? ''),
    queryFn: () => getSyncJob(id as string),
    enabled: Boolean(id),
  });
}

export function useSyncJobSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: syncJobKeys.summaries(status),
    queryFn: () => listSyncJobSummaries(status),
    staleTime: 60_000,
  });
}

export function useSyncJobStatusStats() {
  return useQuery({
    queryKey: syncJobKeys.stats(),
    queryFn: getSyncJobStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSyncJob() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SyncJobInput) => createSyncJob(input),
    onSuccess: () => client.invalidateQueries({ queryKey: syncJobKeys.all }),
  });
}

export function useUpdateSyncJob(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SyncJobUpdateInput) => updateSyncJob(id, input),
    onSuccess: (updated) => {
      client.setQueryData(syncJobKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: syncJobKeys.lists() });
    },
  });
}

export function useChangeSyncJobStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSyncJobStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: syncJobKeys.all }),
  });
}

export function useDeleteSyncJob() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSyncJob(id),
    onSuccess: () => client.invalidateQueries({ queryKey: syncJobKeys.all }),
  });
}

export function useBulkCreateSyncJobs() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SyncJobInput[]) => bulkCreateSyncJobs(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: syncJobKeys.all }),
  });
}

export function useExportSyncJobs() {
  return useMutation({
    mutationFn: (filter: SyncJobFilter) => exportSyncJobsCsv(filter),
  });
}

export function useSyncJobsByEndpointId(endpointId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...syncJobKeys.all, 'by-endpoint_id', endpointId, page, size],
    queryFn: () => listSyncJobByEndpointId(endpointId as string, page, size),
    enabled: Boolean(endpointId),
  });
}
