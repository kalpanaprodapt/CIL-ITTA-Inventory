import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePseudoWires,
  changePseudoWireStatus,
  createPseudoWire,
  deletePseudoWire,
  exportPseudoWiresCsv,
  getPseudoWire,
  getPseudoWireStatusStats,
  listPseudoWireSummaries,
  searchPseudoWires,
  updatePseudoWire,
  listPseudoWireByAEndId,
  listPseudoWireByZEndId,
} from './api';
import type { PseudoWireFilter, PseudoWireInput, PseudoWireUpdateInput } from './types';

export const pseudoWireKeys = {
  all: ['pseudoWire'] as const,
  lists: () => [...pseudoWireKeys.all, 'list'] as const,
  list: (filter: PseudoWireFilter, page: number, size: number, sort?: string) =>
    [...pseudoWireKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...pseudoWireKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...pseudoWireKeys.all, 'summaries', status] as const,
  stats: () => [...pseudoWireKeys.all, 'stats'] as const,
};

export function usePseudoWireList(filter: PseudoWireFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: pseudoWireKeys.list(filter, page, size, sort),
    queryFn: () => searchPseudoWires(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePseudoWire(id: string | undefined) {
  return useQuery({
    queryKey: pseudoWireKeys.detail(id ?? ''),
    queryFn: () => getPseudoWire(id as string),
    enabled: Boolean(id),
  });
}

export function usePseudoWireSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: pseudoWireKeys.summaries(status),
    queryFn: () => listPseudoWireSummaries(status),
    staleTime: 60_000,
  });
}

export function usePseudoWireStatusStats() {
  return useQuery({
    queryKey: pseudoWireKeys.stats(),
    queryFn: getPseudoWireStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePseudoWire() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PseudoWireInput) => createPseudoWire(input),
    onSuccess: () => client.invalidateQueries({ queryKey: pseudoWireKeys.all }),
  });
}

export function useUpdatePseudoWire(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PseudoWireUpdateInput) => updatePseudoWire(id, input),
    onSuccess: (updated) => {
      client.setQueryData(pseudoWireKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: pseudoWireKeys.lists() });
    },
  });
}

export function useChangePseudoWireStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePseudoWireStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: pseudoWireKeys.all }),
  });
}

export function useDeletePseudoWire() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePseudoWire(id),
    onSuccess: () => client.invalidateQueries({ queryKey: pseudoWireKeys.all }),
  });
}

export function useBulkCreatePseudoWires() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PseudoWireInput[]) => bulkCreatePseudoWires(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: pseudoWireKeys.all }),
  });
}

export function useExportPseudoWires() {
  return useMutation({
    mutationFn: (filter: PseudoWireFilter) => exportPseudoWiresCsv(filter),
  });
}

export function usePseudoWiresByAEndId(aEndId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...pseudoWireKeys.all, 'by-a_end_id', aEndId, page, size],
    queryFn: () => listPseudoWireByAEndId(aEndId as string, page, size),
    enabled: Boolean(aEndId),
  });
}

export function usePseudoWiresByZEndId(zEndId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...pseudoWireKeys.all, 'by-z_end_id', zEndId, page, size],
    queryFn: () => listPseudoWireByZEndId(zEndId as string, page, size),
    enabled: Boolean(zEndId),
  });
}
