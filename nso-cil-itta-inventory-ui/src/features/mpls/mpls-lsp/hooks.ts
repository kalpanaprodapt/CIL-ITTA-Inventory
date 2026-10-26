import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateMplsLsps,
  changeMplsLspStatus,
  createMplsLsp,
  deleteMplsLsp,
  exportMplsLspsCsv,
  getMplsLsp,
  getMplsLspStatusStats,
  listMplsLspSummaries,
  searchMplsLsps,
  updateMplsLsp,
  listMplsLspByHeadEndId,
  listMplsLspByTailEndId,
} from './api';
import type { MplsLspFilter, MplsLspInput, MplsLspUpdateInput } from './types';

export const mplsLspKeys = {
  all: ['mplsLsp'] as const,
  lists: () => [...mplsLspKeys.all, 'list'] as const,
  list: (filter: MplsLspFilter, page: number, size: number, sort?: string) =>
    [...mplsLspKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...mplsLspKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...mplsLspKeys.all, 'summaries', status] as const,
  stats: () => [...mplsLspKeys.all, 'stats'] as const,
};

export function useMplsLspList(filter: MplsLspFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: mplsLspKeys.list(filter, page, size, sort),
    queryFn: () => searchMplsLsps(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useMplsLsp(id: string | undefined) {
  return useQuery({
    queryKey: mplsLspKeys.detail(id ?? ''),
    queryFn: () => getMplsLsp(id as string),
    enabled: Boolean(id),
  });
}

export function useMplsLspSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: mplsLspKeys.summaries(status),
    queryFn: () => listMplsLspSummaries(status),
    staleTime: 60_000,
  });
}

export function useMplsLspStatusStats() {
  return useQuery({
    queryKey: mplsLspKeys.stats(),
    queryFn: getMplsLspStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateMplsLsp() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: MplsLspInput) => createMplsLsp(input),
    onSuccess: () => client.invalidateQueries({ queryKey: mplsLspKeys.all }),
  });
}

export function useUpdateMplsLsp(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: MplsLspUpdateInput) => updateMplsLsp(id, input),
    onSuccess: (updated) => {
      client.setQueryData(mplsLspKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: mplsLspKeys.lists() });
    },
  });
}

export function useChangeMplsLspStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeMplsLspStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: mplsLspKeys.all }),
  });
}

export function useDeleteMplsLsp() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteMplsLsp(id),
    onSuccess: () => client.invalidateQueries({ queryKey: mplsLspKeys.all }),
  });
}

export function useBulkCreateMplsLsps() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: MplsLspInput[]) => bulkCreateMplsLsps(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: mplsLspKeys.all }),
  });
}

export function useExportMplsLsps() {
  return useMutation({
    mutationFn: (filter: MplsLspFilter) => exportMplsLspsCsv(filter),
  });
}

export function useMplsLspsByHeadEndId(headEndId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...mplsLspKeys.all, 'by-head_end_id', headEndId, page, size],
    queryFn: () => listMplsLspByHeadEndId(headEndId as string, page, size),
    enabled: Boolean(headEndId),
  });
}

export function useMplsLspsByTailEndId(tailEndId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...mplsLspKeys.all, 'by-tail_end_id', tailEndId, page, size],
    queryFn: () => listMplsLspByTailEndId(tailEndId as string, page, size),
    enabled: Boolean(tailEndId),
  });
}
