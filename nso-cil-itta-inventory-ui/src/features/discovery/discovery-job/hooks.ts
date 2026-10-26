import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateDiscoveryJobs,
  changeDiscoveryJobStatus,
  createDiscoveryJob,
  deleteDiscoveryJob,
  exportDiscoveryJobsCsv,
  getDiscoveryJob,
  getDiscoveryJobStatusStats,
  listDiscoveryJobSummaries,
  searchDiscoveryJobs,
  updateDiscoveryJob,
  listDiscoveryJobByProfileId,
} from './api';
import type { DiscoveryJobFilter, DiscoveryJobInput, DiscoveryJobUpdateInput } from './types';

export const discoveryJobKeys = {
  all: ['discoveryJob'] as const,
  lists: () => [...discoveryJobKeys.all, 'list'] as const,
  list: (filter: DiscoveryJobFilter, page: number, size: number, sort?: string) =>
    [...discoveryJobKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...discoveryJobKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...discoveryJobKeys.all, 'summaries', status] as const,
  stats: () => [...discoveryJobKeys.all, 'stats'] as const,
};

export function useDiscoveryJobList(filter: DiscoveryJobFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: discoveryJobKeys.list(filter, page, size, sort),
    queryFn: () => searchDiscoveryJobs(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useDiscoveryJob(id: string | undefined) {
  return useQuery({
    queryKey: discoveryJobKeys.detail(id ?? ''),
    queryFn: () => getDiscoveryJob(id as string),
    enabled: Boolean(id),
  });
}

export function useDiscoveryJobSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: discoveryJobKeys.summaries(status),
    queryFn: () => listDiscoveryJobSummaries(status),
    staleTime: 60_000,
  });
}

export function useDiscoveryJobStatusStats() {
  return useQuery({
    queryKey: discoveryJobKeys.stats(),
    queryFn: getDiscoveryJobStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateDiscoveryJob() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveryJobInput) => createDiscoveryJob(input),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryJobKeys.all }),
  });
}

export function useUpdateDiscoveryJob(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveryJobUpdateInput) => updateDiscoveryJob(id, input),
    onSuccess: (updated) => {
      client.setQueryData(discoveryJobKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: discoveryJobKeys.lists() });
    },
  });
}

export function useChangeDiscoveryJobStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeDiscoveryJobStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryJobKeys.all }),
  });
}

export function useDeleteDiscoveryJob() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteDiscoveryJob(id),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryJobKeys.all }),
  });
}

export function useBulkCreateDiscoveryJobs() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: DiscoveryJobInput[]) => bulkCreateDiscoveryJobs(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryJobKeys.all }),
  });
}

export function useExportDiscoveryJobs() {
  return useMutation({
    mutationFn: (filter: DiscoveryJobFilter) => exportDiscoveryJobsCsv(filter),
  });
}

export function useDiscoveryJobsByProfileId(profileId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...discoveryJobKeys.all, 'by-profile_id', profileId, page, size],
    queryFn: () => listDiscoveryJobByProfileId(profileId as string, page, size),
    enabled: Boolean(profileId),
  });
}
