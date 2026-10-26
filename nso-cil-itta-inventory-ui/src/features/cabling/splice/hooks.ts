import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSplices,
  changeSpliceStatus,
  createSplice,
  deleteSplice,
  exportSplicesCsv,
  getSplice,
  getSpliceStatusStats,
  listSpliceSummaries,
  searchSplices,
  updateSplice,
  listSpliceByAStrandId,
  listSpliceByZStrandId,
} from './api';
import type { SpliceFilter, SpliceInput, SpliceUpdateInput } from './types';

export const spliceKeys = {
  all: ['splice'] as const,
  lists: () => [...spliceKeys.all, 'list'] as const,
  list: (filter: SpliceFilter, page: number, size: number, sort?: string) =>
    [...spliceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...spliceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...spliceKeys.all, 'summaries', status] as const,
  stats: () => [...spliceKeys.all, 'stats'] as const,
};

export function useSpliceList(filter: SpliceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: spliceKeys.list(filter, page, size, sort),
    queryFn: () => searchSplices(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSplice(id: string | undefined) {
  return useQuery({
    queryKey: spliceKeys.detail(id ?? ''),
    queryFn: () => getSplice(id as string),
    enabled: Boolean(id),
  });
}

export function useSpliceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: spliceKeys.summaries(status),
    queryFn: () => listSpliceSummaries(status),
    staleTime: 60_000,
  });
}

export function useSpliceStatusStats() {
  return useQuery({
    queryKey: spliceKeys.stats(),
    queryFn: getSpliceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSplice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SpliceInput) => createSplice(input),
    onSuccess: () => client.invalidateQueries({ queryKey: spliceKeys.all }),
  });
}

export function useUpdateSplice(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SpliceUpdateInput) => updateSplice(id, input),
    onSuccess: (updated) => {
      client.setQueryData(spliceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: spliceKeys.lists() });
    },
  });
}

export function useChangeSpliceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSpliceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: spliceKeys.all }),
  });
}

export function useDeleteSplice() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSplice(id),
    onSuccess: () => client.invalidateQueries({ queryKey: spliceKeys.all }),
  });
}

export function useBulkCreateSplices() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SpliceInput[]) => bulkCreateSplices(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: spliceKeys.all }),
  });
}

export function useExportSplices() {
  return useMutation({
    mutationFn: (filter: SpliceFilter) => exportSplicesCsv(filter),
  });
}

export function useSplicesByAStrandId(aStrandId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...spliceKeys.all, 'by-a_strand_id', aStrandId, page, size],
    queryFn: () => listSpliceByAStrandId(aStrandId as string, page, size),
    enabled: Boolean(aStrandId),
  });
}

export function useSplicesByZStrandId(zStrandId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...spliceKeys.all, 'by-z_strand_id', zStrandId, page, size],
    queryFn: () => listSpliceByZStrandId(zStrandId as string, page, size),
    enabled: Boolean(zStrandId),
  });
}
