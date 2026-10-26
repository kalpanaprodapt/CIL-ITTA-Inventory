import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateUtilizationSamples,
  changeUtilizationSampleStatus,
  createUtilizationSample,
  deleteUtilizationSample,
  exportUtilizationSamplesCsv,
  getUtilizationSample,
  getUtilizationSampleStatusStats,
  listUtilizationSampleSummaries,
  searchUtilizationSamples,
  updateUtilizationSample,

} from './api';
import type { UtilizationSampleFilter, UtilizationSampleInput, UtilizationSampleUpdateInput } from './types';

export const utilizationSampleKeys = {
  all: ['utilizationSample'] as const,
  lists: () => [...utilizationSampleKeys.all, 'list'] as const,
  list: (filter: UtilizationSampleFilter, page: number, size: number, sort?: string) =>
    [...utilizationSampleKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...utilizationSampleKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...utilizationSampleKeys.all, 'summaries', status] as const,
  stats: () => [...utilizationSampleKeys.all, 'stats'] as const,
};

export function useUtilizationSampleList(filter: UtilizationSampleFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: utilizationSampleKeys.list(filter, page, size, sort),
    queryFn: () => searchUtilizationSamples(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useUtilizationSample(id: string | undefined) {
  return useQuery({
    queryKey: utilizationSampleKeys.detail(id ?? ''),
    queryFn: () => getUtilizationSample(id as string),
    enabled: Boolean(id),
  });
}

export function useUtilizationSampleSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: utilizationSampleKeys.summaries(status),
    queryFn: () => listUtilizationSampleSummaries(status),
    staleTime: 60_000,
  });
}

export function useUtilizationSampleStatusStats() {
  return useQuery({
    queryKey: utilizationSampleKeys.stats(),
    queryFn: getUtilizationSampleStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateUtilizationSample() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: UtilizationSampleInput) => createUtilizationSample(input),
    onSuccess: () => client.invalidateQueries({ queryKey: utilizationSampleKeys.all }),
  });
}

export function useUpdateUtilizationSample(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: UtilizationSampleUpdateInput) => updateUtilizationSample(id, input),
    onSuccess: (updated) => {
      client.setQueryData(utilizationSampleKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: utilizationSampleKeys.lists() });
    },
  });
}

export function useChangeUtilizationSampleStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeUtilizationSampleStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: utilizationSampleKeys.all }),
  });
}

export function useDeleteUtilizationSample() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteUtilizationSample(id),
    onSuccess: () => client.invalidateQueries({ queryKey: utilizationSampleKeys.all }),
  });
}

export function useBulkCreateUtilizationSamples() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: UtilizationSampleInput[]) => bulkCreateUtilizationSamples(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: utilizationSampleKeys.all }),
  });
}

export function useExportUtilizationSamples() {
  return useMutation({
    mutationFn: (filter: UtilizationSampleFilter) => exportUtilizationSamplesCsv(filter),
  });
}
