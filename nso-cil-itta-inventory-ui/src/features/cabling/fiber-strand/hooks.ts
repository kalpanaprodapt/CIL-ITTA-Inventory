import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateFiberStrands,
  changeFiberStrandStatus,
  createFiberStrand,
  deleteFiberStrand,
  exportFiberStrandsCsv,
  getFiberStrand,
  getFiberStrandStatusStats,
  listFiberStrandSummaries,
  searchFiberStrands,
  updateFiberStrand,
  listFiberStrandByCableId,
} from './api';
import type { FiberStrandFilter, FiberStrandInput, FiberStrandUpdateInput } from './types';

export const fiberStrandKeys = {
  all: ['fiberStrand'] as const,
  lists: () => [...fiberStrandKeys.all, 'list'] as const,
  list: (filter: FiberStrandFilter, page: number, size: number, sort?: string) =>
    [...fiberStrandKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...fiberStrandKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...fiberStrandKeys.all, 'summaries', status] as const,
  stats: () => [...fiberStrandKeys.all, 'stats'] as const,
};

export function useFiberStrandList(filter: FiberStrandFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: fiberStrandKeys.list(filter, page, size, sort),
    queryFn: () => searchFiberStrands(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useFiberStrand(id: string | undefined) {
  return useQuery({
    queryKey: fiberStrandKeys.detail(id ?? ''),
    queryFn: () => getFiberStrand(id as string),
    enabled: Boolean(id),
  });
}

export function useFiberStrandSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: fiberStrandKeys.summaries(status),
    queryFn: () => listFiberStrandSummaries(status),
    staleTime: 60_000,
  });
}

export function useFiberStrandStatusStats() {
  return useQuery({
    queryKey: fiberStrandKeys.stats(),
    queryFn: getFiberStrandStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateFiberStrand() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FiberStrandInput) => createFiberStrand(input),
    onSuccess: () => client.invalidateQueries({ queryKey: fiberStrandKeys.all }),
  });
}

export function useUpdateFiberStrand(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FiberStrandUpdateInput) => updateFiberStrand(id, input),
    onSuccess: (updated) => {
      client.setQueryData(fiberStrandKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: fiberStrandKeys.lists() });
    },
  });
}

export function useChangeFiberStrandStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeFiberStrandStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: fiberStrandKeys.all }),
  });
}

export function useDeleteFiberStrand() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteFiberStrand(id),
    onSuccess: () => client.invalidateQueries({ queryKey: fiberStrandKeys.all }),
  });
}

export function useBulkCreateFiberStrands() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: FiberStrandInput[]) => bulkCreateFiberStrands(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: fiberStrandKeys.all }),
  });
}

export function useExportFiberStrands() {
  return useMutation({
    mutationFn: (filter: FiberStrandFilter) => exportFiberStrandsCsv(filter),
  });
}

export function useFiberStrandsByCableId(cableId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...fiberStrandKeys.all, 'by-cable_id', cableId, page, size],
    queryFn: () => listFiberStrandByCableId(cableId as string, page, size),
    enabled: Boolean(cableId),
  });
}
